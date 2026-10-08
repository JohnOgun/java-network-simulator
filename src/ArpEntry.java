package lib;

public class ArpEntry {
    private String ipAddress;// Private string variable ip address which is encapsulated
    private String macAddress;// Private string variable mac address which is encapsulated

    public ArpEntry(String ipAddress, String macAddress) {
        // Constructor for the arp table class
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    public String getIpAddress() { // Returns a string value for the ip address of the entry
        return ipAddress;
    }

    public String getMacAddress() { // Returns a string value for the mac address of the entry
        return macAddress;
    }
}
