package lib;

public class Frame {

    private String data = "OAEINppn[po]";
    // private attribute for the Frame class
    private ICMPPacket request;
    // ICMP PACKET attribute variable called request
    private ICMPPacket reply;
    // ICMP PACKET attribute variable called reply

    public Frame() {
        //Constructor for the Frame class
        this.request = new ICMPPacket(8, 0);
        // initialises the request packet calling the constructor of the ICMP Packet class initialise this attribute
        this.reply = new ICMPPacket(0, 0);
        // initialises the reply packet calling the constructor of the ICMP Packet class initialise this attribute
    }

    public ICMPPacket getReqPacket() {
        // getter method to access the request packet
        return this.request;
    }

    public ICMPPacket getReplyPacket() {
        // GETTER METHOD USED TO ACCESS THE REPLY PACKET ATTRIBUTE OF THE CLASS
        return this.reply;
    }
}
