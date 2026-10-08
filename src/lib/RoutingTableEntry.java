package lib;

public class RoutingTableEntry {
    private String destinationIP;
    private String macAddress;
    private String subnetMask;
    // This is the next router or device on the path
    private String gateway;
    // This is the local interface through which the packet will be sent to
    private EthernetPort entryInterface;
    // Represents the cost of a route
    private int metric;

    public RoutingTableEntry(String destinationIP, String macAddress, String subnetMask, String gateway, EthernetPort entryInterface, int metric) {
        //Constructor for the RoutingTableEntry which will initialise the attributes of the class
        // This will be for each instance of the class that is created
        this.destinationIP = destinationIP;
        this.macAddress = macAddress;
        this.subnetMask = subnetMask;
        this.gateway = gateway;
        this.entryInterface = entryInterface;
        this.metric = metric;
    }

    public String showEntry() {
        // String return type that will be used to show the entries in the RoutingTable
        return this.destinationIP + "\t" + this.macAddress + "\t" + this.subnetMask + "\t" + this.gateway + "\t"
                + this.entryInterface.getName()
                + "\t" + this.metric;
    }


    public int getMetric() {
        // Getter method used to get the metric/cost of the entry
        return this.metric;
    }

    public String getDestinationIP() {
        // returns the destination ip of the instance calling this function
        return this.destinationIP;
    }

    public String getMacAddress() {
        // get the mac address of the objects
        // instance calling thi method return the mac address as a string
        return this.macAddress;
    }

    public String getGateway() {
        // get the gateway of the objects
        // instance calling thi method return the gateway as a string
        return this.gateway;
    }

    public String getSubnetMask() {
        return this.subnetMask;
    }

    public String getInterface() {
        return this.entryInterface.getName();
    }
}
