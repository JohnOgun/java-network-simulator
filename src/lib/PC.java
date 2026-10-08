package lib;

import java.util.ArrayList;

public class PC {
    // private variables as they have to be ecapsulated/abrastracted each for their
    // own reasons
    // Abstraction: done with private attributes as they are made private but are
    // used in the methods
    // Also that the uses of the attrubites is hidden

    private String ipAddress;// ip address is a string attribute of the pc class
    private String macAddress;// ip address is a string attribute of the pc class
    private String hostname;// ip address is a string attribute of the pc class
    private ArpTable arpTable;// Composition since PC has an ARP table
    private Router defaultGateway; // The gateway to the router that will handle all network traffic

    public PC(String ipAddress, String macAddress, String hostname) { // Constructor for the PC class
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.hostname = hostname;
        this.arpTable = new ArpTable();
    }

    public String getIpAddress() { // Returns a string value for the ip address of the PC
        return ipAddress;
    }

    public String getMacAddress() {// Returns a string value for the mac address of the PC
        return macAddress;
    }

    public String getHostname() { // Returns a string value for the Hostname of the PC
        return hostname;
    }

    public Router getDefaultGateway() {
        return this.defaultGateway;
    }

    public void setDefaultGateway(Router router) {
        this.defaultGateway = router;
    }

    public void showInformation(int pcNumber) { // Print function to show the information curretly on the PC it takes
        // the pc number as a parameter
        String information = "PC " + pcNumber + " Information:\n" +
                "\tIP Address: " + getIpAddress() + "\n" +
                "\tMAC Address: " + getMacAddress() + "\n" +
                "\tHostname: " + getHostname() + "\n";
        System.out.println(information);
    }

    private void simulateRequest(PC recPC) { // print function to show a message acting as a reuqest being made
        System.out.println("Simulating a request to " + recPC.getIpAddress() + "... \n");
    }

    public static String[] splitIpAddress(String ipAddress) {
        return ipAddress.split("\\."); // Split function used to reduce the ip address into bytes instead of the
        // full address
    }

    public void localPing(String pcIpAddress) {
        String[] ipParts1 = PC.splitIpAddress(this.getIpAddress());// store the seprated parts in an array for a ipPart1
        String[] ipParts2 = PC.splitIpAddress(pcIpAddress); // store the seprated parts in an array for a ipPart2

        double sumRTT = 0;
        double minRTT = Double.MAX_VALUE;
        double maxRTT = Double.MIN_VALUE;
        int countRTT = 0;

        // Check if PCs are on the same network
        boolean isLocal = checkParts(ipParts1, ipParts2);
        if (isLocal) {
            System.out.println("Checking if PC1 and PC2 are on the same network......");
            System.out.println("PC1 and PC2 are on the same network.\n");

            System.out.println("\nPerforming arp entry look up");
            // Look up the arp entry for the receipient pc
            ArpEntry entry = this.arpTable.lookUpArpEntry(pcIpAddress);

            // Check if we found the arp entry
            if (entry == null) {
                System.out.println("There is no existing arp entry for " + ipAddress);
            } else {
                System.out.println("Arp entry found for " + ipAddress);
            }

            // Check if MAC addresses are valid
            if (this.checkMacAddress(this.getMacAddress()) && checkMacAddress(entry.getMacAddress())) {

                PC receipientPc = this.defaultGateway.routePacket(entry);

                // Simulate a request from this PC to the receipientPc
                simulateRequest(receipientPc);
                Frame frame = new Frame();

                // ping with a request and reply packet
                for (int i = 0; i < 4; i++) {
                    this.sendRequest(receipientPc, frame, i);

                    // Calculate and display RTT
                    double rtt = frame.getReplyPacket().getRTT(); // Get the reply packet RTT time
                    if (rtt >= 0) {
                        sumRTT += rtt; // sum stores all the RTT values
                        countRTT++;// counting the amount of packet that are made with RTT timers

                        if (rtt < minRTT) {// if the rtt is less than the min rtt value the rtt value will become the
                            // min rtt
                            minRTT = rtt;
                        }
                        if (rtt > maxRTT) {
                            maxRTT = rtt;// if the rtt is bigger than max rtt then the value of RTT is put into maxRTT
                        }
                        System.out.println("Round Trip Time: " + rtt + " ms\n");// printing out the RTT calculation time
                    } else {
                        System.out.println("Round Trip Time: RTT reply not received \n");
                    }
                }

                if (countRTT > 0) { // if the count is greater than 0
                    double averageRTT = sumRTT / countRTT;// double variable made to store the average rtt time
                    System.out.println("Average RTT: " + averageRTT + " ms");// used to print the average rtt time to //
                    // the console
                    System.out.println("Min RTT: " + minRTT + " ms");// used to print the min rtt time to the console
                    System.out.println("Max RTT: " + maxRTT + " ms");// used to print the max rtt time to the console
                } else {
                    System.out.println("No RTT values to calculate stats.");
                }
            } else {
                // Display if the MAC address is not valid
                System.out.println("Invalid MAC address. Ping cannot take place.");
            }
        } else {
            // Display if the PC's are too far from one another meaninig that it is a remote
            // ping needed is not valid
            System.out.println("PC1 and PC2 are on different networks. Local Ping cannot take place.");
        }
    }

    public void remotePing(String pcIpAddress) {
        double sumRTT = 0;
        double minRTT = Double.MAX_VALUE;
        double maxRTT = Double.MIN_VALUE;
        int countRTT = 0;

        System.out.println("\nPerforming arp entry look up");

        // Search for the router receipient pc is connected to
        Router receipientRouter = this.lookUpRemoteRouter(pcIpAddress);

        // Look up the arp entry for the receipient pc
        ArpEntry entry = this.arpTable.lookUpArpEntry(pcIpAddress);

        // Check if we found the arp entry
        if (entry == null) {
            System.out.println("No arp entry found for: " + pcIpAddress);
            System.out.println("Performing ospf search");
            entry = receipientRouter.ospfSearch(pcIpAddress);
            this.addToArpTable(entry.getIpAddress(), entry.getMacAddress());
            System.out.println("Arp entry found for " + pcIpAddress);
        } else {
            System.out.println("Arp entry found for " + pcIpAddress);
        }

        // Check if MAC addresses are valid
        if (this.checkMacAddress(this.getMacAddress()) && checkMacAddress(entry.getMacAddress())) {

            PC receipientPc = receipientRouter.routePacket(entry);

            // Simulate a request from this PC to the receipientPc
            simulateRequest(receipientPc);
            Frame frame = new Frame();

            // ping with a request and reply packet
            for (int i = 0; i < 4; i++) {
                this.sendRequest(receipientPc, frame, i);

                // Calculate and display RTT
                double rtt = frame.getReplyPacket().getRTT(); // Get the reply packet RTT time
                if (rtt >= 0) {
                    sumRTT += rtt; // sum stores all the RTT values
                    countRTT++;// counting the amount of packet that are made with RTT timers

                    if (rtt < minRTT) {// if the rtt is less than the min rtt value the rtt value will become the
                        // min rtt
                        minRTT = rtt;
                    }
                    if (rtt > maxRTT) {
                        maxRTT = rtt;// if the rtt is bigger than max rtt then the value of RTT is put into maxRTT
                    }
                    System.out.println("Round Trip Time: " + rtt + " ms\n");// printing out the RTT calculation time
                } else {
                    System.out.println("Round Trip Time: RTT reply not received \n");
                }
            }

            if (countRTT > 0) { // if the count is greater than 0
                double averageRTT = sumRTT / countRTT;// double variable made to store the average rtt time
                System.out.println("Average RTT: " + averageRTT + " ms");// used to print the average rtt time to //
                // the console
                System.out.println("Min RTT: " + minRTT + " ms");// used to print the min rtt time to the console
                System.out.println("Max RTT: " + maxRTT + " ms");// used to print the max rtt time to the console
            } else {
                System.out.println("No RTT values to calculate stats.");
            }
        } else {
            // Display if the MAC address is not valid
            System.out.println("Invalid MAC address. Ping cannot take place.");
        }
    }

    private Router lookUpRemoteRouter(String ipAddress) {
        // Get all the routers connected to the pc's default gateway
        ArrayList<Router> routers = this.defaultGateway.getConnectedRouters();
        String[] ipParts1 = PC.splitIpAddress(ipAddress);
        String[] ipParts2;

        // Search for the router that the ipAddress is connected to
        for (Router router : routers) {
            ipParts2 = PC.splitIpAddress(router.getIpAddress());
            // Check if the ip addresses are on the same network
            if (this.checkParts(ipParts1, ipParts2)) {
                return router;
            }
        }

        return null;
    }

    private boolean checkParts(String[] ipParts1, String[] ipParts2) {

        if (ipParts1.length != 4 || ipParts2.length != 4) {
            return false; // Check if both IP addresses are split into four parts
        }

        for (int i = 0; i < 4; i++) {
            if (ipParts1[i].length() > 3 || ipParts2[i].length() > 3) {
                System.out.println("IP Address is longer than three digits"); // If not one to three digits or more than
                // three characters, return false
                return false;
            }
        }

        for (int i = 0; i < 4; i++) {
            int part1 = Integer.parseInt(ipParts1[i]); // Convert the string ipPart1 to and interger stored in the
            // variable part1
            int part2 = Integer.parseInt(ipParts2[i]); // Convert the string ipPart2 to and interger stored in the
            // variable part2

            if (part1 < 0 || part1 > 255 || part2 < 0 || part2 > 255) {
                System.out.println("IP Address value entered is incorrect a byte value was bigger than 255"); // If not
                // one to
                // three
                // digits
                // or more
                // than
                // three
                // characters,
                // return
                // false
                return false; // Checks if parts match and are within the range of 0-255
            }

            if (i < 3 && part1 != part2) {
                return false; // Parts must be equal for the same network

            }
        }
        return true; // If all conditions are met, IPs are on the same network

    }

    public boolean checkMacAddress(String macAddress) {
        String[] parts = macAddress.split("[:-]");
        if (parts.length != 6)
            return false;

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() != 2 || !parts[i].matches("[0-9A-F]{2}")) { // if lentgh of the parts is not two
                // reject or also if it doesnt match the
                // range given 0-9 and from A-F for two
                // digits
                // matches used here to set a range for digits and for lower/upper case letters
                // to match with the part of the mac address
                return false;
            }
        }
        return true;
    }

    public void addToArpTable(String ipAddress, String macAddress) {// Add an entry to the code using the parameters
        // ipAdress and macAddress
        this.arpTable.addEntry(ipAddress, macAddress);
    }

    public void displayArpTable() {
        System.out.println("\n=== ARP Table for " + getHostname() + " ===");
        this.arpTable.displayArpTable();
    }

    private void sendRequest(PC receipient, Frame frame, int sequence) {
        // set the soucre for the reuqest packet using this instance of the ip address
        frame.getReqPacket().setSourceIP(this.getIpAddress());
        // set the destination for the reuqest packet using the destintion ip address
        frame.getReqPacket().setDestinationIP(this.getIpAddress());
        // Setting sequence number for each request
        frame.getReqPacket().setSequenceNumber(sequence);
        frame.getReqPacket().Delay1sec();

        // Request message made to show where the request is coming from and where it is
        // going to
        System.out.println("Sending ICMP Echo Request (Seq: " + frame.getReqPacket().getSequenceNumber() + ") from "
                + this.getIpAddress() + " to " + receipient.getIpAddress());

        receipient.onRequest(this, frame, sequence);
    }

    private void onRequest(PC sender, Frame frame, int sequence) {
        // This reply packet is sent back to pc who started the ping request (sender)

        // Set receipient IP address
        frame.getReplyPacket().setSourceIP(this.getIpAddress());
        // Set sender IP address
        frame.getReplyPacket().setDestinationIP(sender.getIpAddress());
        // sett the sequence number for reply packet
        frame.getReplyPacket().setSequenceNumber(sequence);

        // Prints to the console the following message
        System.out.println("Received ICMP Echo Reply (Seq: " + frame.getReplyPacket().getSequenceNumber() + ") from "
                + this.getIpAddress() + " to " + sender.getIpAddress() + "\n");

        // sets the received time to the Reply packet after receiving the reply using
        // the laptops current time
        frame.getReplyPacket().setReceivedTime(System.currentTimeMillis());
    }

    public ArpTable getArpTable() {
        return this.arpTable;
    }

}
