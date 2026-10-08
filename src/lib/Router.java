package lib;

import java.util.ArrayList;

// We are going to simulate a modern router, one that has a switch integrated with it
public class Router {
    // This is going to be a pre-configured router for now, there for class members
    // will be filled
    private String macAddress;
    private String ipAddress;
    private ArrayList<EthernetPort> ports;
    private String hostname;
    private ArrayList<PC> connectedDevices;
    private ArrayList<Router> connectedRouters;
    private Switch switchDevice;

    public Router(String hostname, String macAddress, String ipAdress) {
        this.hostname = hostname;
        this.macAddress = macAddress;
        this.ipAddress = ipAdress;

        // All routers will have 6 ports
        this.ports = new ArrayList<EthernetPort>();
        this.connectedDevices = new ArrayList<PC>();
        this.connectedRouters = new ArrayList<Router>();

        // Initialise switch
        this.switchDevice = new Switch();

        // Initialise ports
        for (int i = 0; i < 6; i++) {
            this.addPort(new EthernetPort(i));
        }
    }
    public RoutingTable getRoutingTable() {
        return this.switchDevice.getRoutingTable();
    }
    public String getHostname() {
        return this.hostname;
    }

    public void connectDevice(PC pc) {
        // connect the pc through a port
        EthernetPort port = this.assignDevicePort(pc);

        // Show the arp table before updating
        pc.displayArpTable();
        this.onNewDevice(pc);
        this.connectedDevices.add(pc);

        // Set this router instance as this pc's default gateway
        pc.setDefaultGateway(this);
        this.switchDevice.addEntry(pc.getIpAddress(), pc.getMacAddress(), "255.0.0.0", this.ipAddress, port);

        System.out.println(pc.getHostname() + " has connected to " + this.hostname + ".");

    }

    /**
     * Update arp entries for the new pc and all other devices on the network
     *
     * @param pc the new device
     */
    private void onNewDevice(PC pc) {
        if (this.connectedDevices.size() == 0) {
            return;
        }

        for (PC device : connectedDevices) {
            device.addToArpTable(pc.getIpAddress(), pc.getMacAddress());
            pc.addToArpTable(device.getIpAddress(), device.getMacAddress());
        }

        // Show the arp table after updating
        pc.displayArpTable();
    }

    // Method override for router
    public void connectDevice(Router router) {
        // connect the router through a port
        EthernetPort port = this.assignDevicePort(router);

        this.connectedRouters.add(router);

        this.switchDevice.addEntry(router.getIpAddress(), router.getMacAddress(), "255.0.0.0", this.ipAddress, port);
        System.out.println(router.getHostname() + " has connected to " + this.hostname + ".");

        if (!router.isRouterConnected(this)) {
            router.connectDevice(this);
        }
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void addPort(EthernetPort port) {
        this.ports.add(port);
        System.out.println("Port: " + port.getName() + " initialised on " + "router " + this.hostname);
    }

    private EthernetPort assignDevicePort(PC pc) {
        int portIndex = -1;
        // find a free port
        for (int i = 0; i < this.ports.size(); i++) {
            if (!ports.get(i).isConnected()) {
                portIndex = i;
                break;
            }
        }

        if (portIndex == -1) {
            System.out.println("Error: There are no more free ports");
            return null;
        }
        this.ports.get(portIndex).connectDevice(pc);
        System.out.println("Device: " + pc.getHostname() + " connected to PORT " + portIndex);
        return this.ports.get(portIndex);
    }

    // Method override for router
    private EthernetPort assignDevicePort(Router router) {
        int portIndex = -1;
        // find a free port
        for (int i = 0; i < this.ports.size(); i++) {
            if (!ports.get(i).isConnected()) {
                portIndex = i;
                break;
            }
        }

        if (portIndex == -1) {
            System.out.println("Error: There are no more free ports");
            return null;
        }
        this.ports.get(portIndex).connectDevice(router);
        System.out.println("Device: " + router.getHostname() + " connected to PORT " + portIndex);
        return this.ports.get(portIndex);
    }

    public void displayRoutingTable() {
        this.switchDevice.showEntries();
    }

    public PC routePacket(ArpEntry entry) {
        PC foundPC = null;
        // Simulate PC look up from switch table
        System.out.println("Performing device look up for " + entry.getIpAddress() + " : " + entry.getMacAddress());
        RoutingTableEntry routingTableEntry = this.switchDevice.lookUpRoutingTableEntry(entry.getIpAddress(),
                entry.getMacAddress());
        foundPC = this.lookUpDevice(routingTableEntry);
        return foundPC;
    }

    private PC lookUpDevice(RoutingTableEntry entry) {
        PC device = null;
        boolean isFound;

        for (PC pc : this.connectedDevices) {
            isFound = (pc.getIpAddress().equals(entry.getDestinationIP())
                    && pc.getMacAddress().equals(entry.getMacAddress()))
                    && pc.getDefaultGateway().getIpAddress().equals(entry.getGateway());
            if (isFound) {
                device = pc;
                System.out.println("Router: device with hostname " + pc.getHostname() + " found.");
            }
        }

        return device;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public ArrayList<Router> getConnectedRouters() {
        return this.connectedRouters;
    }

    /*
     * This method implements a basic application of the ospf algorithim
     * It finds the entry in the routing table that has the least cost.
     * Cost is determined by an entrys metric
     */
    public ArpEntry ospfSearch(String ipAddress) {
        // Get all routing table entries
        ArrayList<RoutingTableEntry> routingTableEntries = this.switchDevice.getDeviceEntries(ipAddress);

        // There is no route to choose if the destination isn't in the table.
        if (routingTableEntries.isEmpty()) {
            return null;
        }

        RoutingTableEntry entry = routingTableEntries.get(0);

        // Find the entry with the least cost
        for (RoutingTableEntry rTableEntry : routingTableEntries) {
            if (rTableEntry.getMetric() < entry.getMetric()) {
                entry = rTableEntry;
            }
        }
        return new ArpEntry(entry.getDestinationIP(), entry.getMacAddress());
    }

    /**
     * Check if the router is connected to another router
     * @param router - the other router
     */
    private Boolean isRouterConnected(Router router) {
        return this.connectedRouters.contains(router);
    }
}
