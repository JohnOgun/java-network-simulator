package lib;

public class EthernetPort<T> {
    private boolean connected = false;
    // Is a private variable connected set to false this will hold the state
    // of the Port the false state means that the port is free and that it is not connected
    private T connectedDevice;
    // Each port in a router will have a unique number to represent it
    private int number;

    public EthernetPort(int number) {
        //Constructor for the EthernetPort class which is used to initialise the attributes of the class
        // it is passed a number for to distinguish itself from another EthernetPort
        this.connectedDevice = null;
        // set the connected devices to null to indicate that no devices have been connected to the port yet

        this.number = number;// uses the passed number variable to set the object instance port number
    }

    public void connectDevice(T device) {
        // Make sure port is free
        if (this.connected == false) {
            //CHeck if the port is free
            this.connectedDevice = device;
            //Assign the device to the connectedDevice variable
            this.connected = true;
            // Change the state of the instance of the Port to True meaning that it is now connected to the device
        } else {
            System.out.println("This port is already in use.");
        }
    }

    public boolean isConnected() {
        // THe function is used to return the state of the port
        return this.connected;
    }

    public String getName() {
        // Sting return function that will return the name
        // number of the port with the string "eth" in front of it
        return "eth" + this.number;
    }
}
