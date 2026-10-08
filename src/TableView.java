package components;

import javax.swing.*;

import lib.ArpEntry;
import lib.ArpTable;
import lib.RoutingTable;
import lib.RoutingTableEntry;

public class TableView<T> extends JFrame {
    //This is the tableView class attribute
    //This class uses the generics <T> So that any object can be used in this class for improved reusability
    private JTable table;
    private String tableName;
    private T data; //Data is of type T which will be the object that is passedd to the class

    public TableView(String tableName, T tableData, Boolean isRoutingTable) {
        //Constructor for the TableView class
        this.data = tableData;//assign the passed parameter for the constructor to the data attribute of the class
        this.tableName = tableName; //assing the passed table name to this view instance of the tableName attribute

        // Set window title
        this.setTitle(this.tableName);

        // Table data
        String [][] data;
        //2d array used to store the data from the PC or ROuter objects
        //The first [] block will be used to store the rows of which the data is asscoatied with
        String[] columnNames;// This will be the
        if (isRoutingTable) {
            // Routing Table column labels for the routing table
            columnNames = new String[6]; //set the size of the array to be 6 and of type string
            columnNames[0] = "Destination IP";
            columnNames[1] = "MAC Address";
            columnNames[2] = "Subnet Mask";
            columnNames[3] = "Gateway";
            columnNames[4] = "Interface";
            columnNames[5] = "Metric";

            RoutingTable rTable = (RoutingTable) this.data; // cast the data to be in the form of the routing table

            // Initialise table data array
            int rows = rTable.size();//assign the size of the routing table to be the rows
            data = new String[rows][6];//The 2D array will use the rows vairable and 6

            RoutingTableEntry entry;
            // Add data to table
            for (int i = 0; i < rows; i++) {
                // for loop that will end once the total size of the routing table is checked meaning each entry
                entry = rTable.getEntry(i);
                //entry is assigned the value of each entry in the routing table

                //get all the data for the routing table using the routing table entries getter methods
                // this will be used to assign each rows data and it posstion
                data[i][0] = entry.getDestinationIP();
                data[i][1] = entry.getMacAddress();
                data[i][2] = entry.getSubnetMask();
                data[i][3] = entry.getGateway();
                data[i][4] = entry.getInterface();
                data[i][5] = String.valueOf(entry.getMetric());
                //converting the metric to be a string using the string in built valueOf method
            }


        } else {
            // ARP Table column labels
            columnNames = new String[2];
            columnNames[0] = "IP Address";
            columnNames[1] = "MAC Address";

            ArpTable aTable = (ArpTable) this.data;
            //casting this instance of the dat to be in the form of an Arp table

            // rows variable to store the total size of the arpTable
            int rows = aTable.size();
            data = new String[rows][2]; // make data a string 2D array

            ArpEntry entry;
            // Add data to table
            for (int i = 0; i < rows; i++) {
                entry = aTable.getEntry(i);
                data[i][0] = entry.getIpAddress();
                data[i][1] = entry.getMacAddress();
            }


        }

        // assign the data and the column names to a new instance of the JTable class
        this.table = new JTable(data, columnNames);

        // The JScrollPane is added to allow for scrolling for this view
        //Which comes from the swing package
        JScrollPane sp = new JScrollPane(this.table);
        this.add(sp);
        // add the Scrollpane to the this instance of the TableView

        this.setSize(700, 300);
        //Set the size of the window that will be displayed
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //using the DISPOSE_ON_CLOSE so that the main window is not closed
        this.setResizable(false);
        this.setVisible(true);
        // set the view to be visible
    }
}
