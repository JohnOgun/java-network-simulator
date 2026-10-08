package lib;

import java.util.ArrayList;

public class Switch {
    private RoutingTable table;
    //Composition relationship is shown as the Routing Table is part of the whole (Switch)
    // This implementation is used to give the Switch functionality within this simulation of a network

    public Switch() {
        //Construtor for the Switch class which is used to initialise the attributes of the class itself
        this.table = new RoutingTable();
        // creating a new instance of the routing table class which will call
        // the constructor of the class itself and assign it to this object instance of the class
    }

    public void addEntry(String destinationIP, String macAddress, String subnetMask, String gateway, EthernetPort entryInterface) {
        // function to add an entry to the arp table
        // it collects the key information for arp table
        // the addEntry function needs to have the following parameters or else the entry will not be added
        table.addEntry(destinationIP, macAddress, subnetMask, gateway, entryInterface);
    }

    public void showEntries() {
        // This method calls the Routing tables function to show the entries
        // that it contains this is a example of abstraction and encapsulation
        this.table.showEntries();
    }

    public RoutingTableEntry lookUpRoutingTableEntry(String destinationIp, String macAddress) {
        // This function is used to look up a routing table entry
        RoutingTableEntry entry = this.table.lookUpEntry(destinationIp, macAddress);
        // the searched entry is assigned to the Routing table variable entry
        System.out.println("\nSwitch: device " + entry.getDestinationIP() + " : " + entry.getMacAddress() + " found.");
        // Print to the console the ip and mac address of the entry
        return entry;//return the entry that has been found
    }

    public ArrayList<RoutingTableEntry> getDeviceEntries(String deviceIpAddress) {
        // this function has a returns an arraylist of type for Routing table entries

        return this.table.lookUpDeviceEntries(deviceIpAddress);
        //This line will return a entry based on its ip address of the device
    }
    public RoutingTable getRoutingTable() {
        return this.table;
    }
}
