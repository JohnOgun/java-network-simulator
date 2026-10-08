package lib;

public class ICMPPacket {
    private int type;// Type of ICMP packet request/reply packet type
    private int sequenceNumber;// Sequence number used to display the replies and requests in the pc class
    private double sentTime; // double variable for sending device sent time used for RTT calc(ms)
    private double receivedTime; // double variable for receiveing device received time used for RTT calc(ms)
    private String sourceIP; // Stores the IP address of the packet's source
    private String destinationIP; // Stores the IP address of the packet's destination
    private double rtt = -1.0; // RTT in seconds, initialized to -1.0 to allow for the rtt to increase on the
    //  for the ping functions

    public ICMPPacket(int type, int sequenceNumber) {// constuctor
        this.type = type; // This line allows the type to be set using the variable that is passed to this
        // function
        this.sequenceNumber = sequenceNumber;
        this.sentTime = System.currentTimeMillis(); // Set sentTime to current time in milliseconds
    }

    public int getType() { // getter function to get the type of function back
        return type;
    }

    public void setType(int type) { // Setter function to set the type of icmp
        this.type = type;
        // This line allows the type to be set using the variable that is passed to this
        // function
        // This is then set to the current instance of the packet class ca;lling it
    }

    public void setSequenceNumber(int sequenceNumber) { // takes in the value for seq number and assign it to this
        // instance of the class
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() { // returns the sequence number value as an integer
        return sequenceNumber;
    }

    public double getSentTime() { // returns the value of the sentTime as a double
        return sentTime;
    }

    public double getReceivedTime() { // returns the value of the receivedTime as a double
        return receivedTime;
    }

    public void setReceivedTime(double receivedTime) {
        this.receivedTime = receivedTime; // uses the passed parameter to assign to this instance of the packet class
        // calling it
        updateRTT(); // Update RTT when received time is gotten
        // This function above is called to do the calculation of the rtt and then give
        // back the new rtt in seconds
    }

    private void updateRTT() {
        if (receivedTime > 0 && sentTime > 0) {// If the receivedTime and sent time are greater than 0
            rtt = (receivedTime - sentTime) / 1000.0; // Calculates RTT in seconds by divding the milliseconds values by
            // 1000
        }
    }

    public double getRTT() { // gets the rtt after the calculation
        return rtt; // Getter for RTT
    }

    public String getSourceIP() { // returns a string for the source ip that will be split later
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getDestinationIP() {// returns a string for the destination ip that will be split later
        return destinationIP;
    }

    public void setDestinationIP(String destinationIP) { // Setter for set the destination ip
        this.destinationIP = destinationIP;
    }

    public void Delay1sec() {// delay of one sec added so that the ping process is more realistic
        long startTime = System.currentTimeMillis(); // Start time using system time in milli
        long endTime = startTime + 1500; // Adding 1.5 seconds delay

        while (System.currentTimeMillis() < endTime) {
            // Loop for 1.5 seconds
        }

    }
}
