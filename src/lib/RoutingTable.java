package lib;

import java.util.ArrayList;

public class RoutingTable {
    private ArrayList<RoutingTableEntry> entries;
    //private variable that stores RoutingTableEntry type of object in the arraylist the list is called entries

    public RoutingTable() {
        //This is the routing table constructor which is used to initialise the attributes of the class
        this.entries = new ArrayList<RoutingTableEntry>();
        //creating a new array list for the entries of the routing table
    }

    public void addEntry(String destinationIP, String macAddress, String subnetMask, String gateway, EthernetPort entryInterface) {
        // This fucntion is used to add entries within the Routing table object


        // We add duplicate entries with different costs to show ospf finds the one with smallest cost
        this.entries.add(new RoutingTableEntry(destinationIP, macAddress, subnetMask, gateway, entryInterface, 1));
        this.entries.add(new RoutingTableEntry(destinationIP, macAddress, subnetMask, gateway, entryInterface, 2));
        this.entries.add(new RoutingTableEntry(destinationIP, macAddress, subnetMask, gateway, entryInterface, 3));
        // These lines use the in built add function for the array list
        // the code shows that the new keyword is used to allow for the
        // required parameters to initialise the object of type RoutingTableEntry

    }

    public void showEntries() {
        // this fucntion is sued to display the entries store within the RoutingTable
        System.out.println("Routing table: ");
        // Prints string to the console window

        System.out.println("Destination IP" + "\t" + "MAC Address" + "\t        " + "Subnet Mask" + "\t" + "Gateway" + "\t" + "Interface" + "\t" + "Metric");
        // this is used as a form of formating for the routing table as it is used as header labels
        for (RoutingTableEntry entry : entries) {
            //for each loop that will loop through each entry stored in the routing table
            System.out.println("---------------------------------------------------------------------");//Divider of to separate the entries
            System.out.println(entry.showEntry());// prints to the console all the entries contained in the routing table
        }
        System.out.println("---------------------------------------------------------------------");//Divider of to separate the next entry
    }


    public RoutingTableEntry lookUpEntry(String destinationIp, String macAddress) {
        // within this method it is used to search and find the entry in the routing table
        //This fucntion uses it;s passed parameters to allow for this search to take place
        for (RoutingTableEntry routingTableEntry : this.entries) {
            //For each loop to iterate through the array list of the objects instance that called this method
            //routingTableEntry holds the value of the entry that is currently being used in the loop
            //This will change each time the loop happens as it will hold the next value of the routing table entry
            if (routingTableEntry.getDestinationIP().equals(destinationIp) && routingTableEntry.getMacAddress().equals(macAddress)) {
                //if statement condition to check if the destination ip and mac address is in the routing table
                System.out.println("\nRouting Table Entry found: ");
                System.out.println("---------------------------------------------------------------------");//Divider of to separate the next entry
                System.out.println(routingTableEntry.showEntry());
                System.out.println("---------------------------------------------------------------------");//Divider of to separate the next entry
                return routingTableEntry;//returns the entry of the routing table
            }
        }
        return null;
    }

    public ArrayList<RoutingTableEntry> lookUpDeviceEntries(String deviceIpAddress) {
        //Using an array list to be able to store the found entries in the routing table using
        // a new instance of the arraylist class which class the default constructor of the class
        ArrayList<RoutingTableEntry> foundEntries = new ArrayList<RoutingTableEntry>();

        for (RoutingTableEntry routingTableEntry : this.entries) {
            //For each loop that is sued to iterate through the entries of the routing table array list
            if (routingTableEntry.getDestinationIP().equals(deviceIpAddress)) {
                //Conditional if statement is used to check if the passed parameter is equal to the destination ip in the routing table
                // it will then be added to the list of found devices in the routing table
                foundEntries.add(routingTableEntry);
            }
        }
        return foundEntries;
        // if not entries are in the routing table it will return an empty arraylist
    }

    public int size() {
        return this.entries.size();
    }

    public RoutingTableEntry getEntry(int index) {
        return this.entries.get(index);
    }
}
