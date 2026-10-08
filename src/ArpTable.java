package lib;

import java.util.ArrayList;

public class ArpTable {
    private ArrayList<ArpEntry> entries;// Private variable arraylist variable which is for the entries in the arp table

    public ArpTable() {
        //Constructor for the ArpTable class to initialise the array of entries using the
        // new keyword which will call the default constructor for array list class Arptable object
        this.entries = new ArrayList<ArpEntry>();

    }

    public void addEntry(String ipAddress, String macAddress) {
        // Limit an arp table to 10 entries
        if (this.entries.size() == 10) {
            System.out.println("Max limit of entries is 10.");
            return;
        }
        this.entries.add(new ArpEntry(ipAddress, macAddress));
    }

    public void displayArpTable() {
        System.out.println("IP Address \t MAC Address");
        System.out.println("---------------------------------");
        // Loop through entries and print out each entries ip & mac address
        for (ArpEntry entry : this.entries) {
            System.out.println(entry.getIpAddress() + "\t" + entry.getMacAddress());
        }
        System.out.println("---------------------------------");
    }

    public ArpEntry lookUpArpEntry(String ipAddress) {
        // FUNCTION THAT WILL BE USED TO SEARCH FOR AN ENTRY ON THE ARP Table
        ArpEntry foundEntry = null;
        // creating a arp table entry variable that is set to null to indicate that no entry has been found yet
        for (ArpEntry arpEntry : this.entries) {
            // for each loop which is used in this function to loop
            // through each of these entries stored in the object instance calling this function
            if (arpEntry.getIpAddress().equals(ipAddress)) {
                //  if conditional statement that is sued to check if the ip address is in the Arp table
                foundEntry = arpEntry;
                //assign the current entry to the found entry variable
            }
        }
        return foundEntry;// return the null found entry variable if the entry is not within the Arp Table
    }

    public int size() {
        return this.entries.size();
    }

    public ArpEntry getEntry(int index) {
        return this.entries.get(index);
    }
}
