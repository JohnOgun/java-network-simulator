package components;

import java.awt.Dimension;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.*;

import lib.PC;
import lib.Router;

public class RouterView extends JPanel {
    private  Router router; // Router object for the  RouterView
    private ArrayList<JButton> pcDeviceBtns;  //arraylist to hold the Buttons that are made from the createDeviceContainer
    private  HomeView parentPanel;  // HomeView is he parent panel for this class

    public RouterView(Router router, HomeView parentPanel) {
        //RouterView constructor for the class instances

        this.router = router;  //assign this router that was passed to the constructor to this instance of the router for the view

        this.pcDeviceBtns = new ArrayList<JButton>();  // this instance of the attribute pcDeviceBtns in the class is assigned to a new arraylist
        this.parentPanel = parentPanel;  // set the parent panel to the class


        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));// Make the layout of the instance of the class to be horizontally

        JLabel heading = new JLabel("Connected devices:");  // Label for the container to show the connected devices below

        // Main container panel with a vertical box layout for its components
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Container for the two routers which will be in the horizontal
        JPanel routersContainer = new JPanel();
        routersContainer.setLayout(new BoxLayout(routersContainer, BoxLayout.X_AXIS));

        ImageIcon backIcon = new ImageIcon("src/images/back.png");
        JButton backBtn = new JButton("Back to networks", new ImageIcon(
                backIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        backBtn.setMargin(new Insets(10, 8, 10, 8)); // Back button with a icon

        backBtn.addActionListener(e -> this.parentPanel.getController().showFrame("home"));  // Action listener to go back to HomeView


        container.add(new JLabel("Router: " + this.router.getHostname()));  // Display the router's hostname
        container.add(Box.createRigidArea(new Dimension(5, 10)));  // spcae between the components
        container.add(new JLabel("IP Address: " + this.router.getIpAddress()));  // Display the router's IP address for this instance of the RouterView
        container.add(Box.createRigidArea(new Dimension(5, 10)));  // space between the components
        container.add(new JLabel("MAC Address: " + this.router.getMacAddress()));  // Display the router's MAC address for this instance of the RouterView
        container.add(Box.createRigidArea(new Dimension(5, 10)));  // Space between the components
        container.add(heading);  // Add the heading label to the container
        container.add(Box.createRigidArea(new Dimension(5, 10))); // Space between the components

        // Add PC containers depending on which network is the router is in
        JPanel routerContainer;
        if (this.router.getHostname().equals("Johns Network")) {
            //IF the router is has a hostname that is John's Network then pc1 and pc2 wil; be displayed in the container
            routersContainer.add(Box.createHorizontalGlue());
            routerContainer = this.createDeviceContainer(this.parentPanel.getSeed().getPc1());
            // using the function created to make create a container for PC1 using the getSeed
            // This is able to be called due to the view having a HomeView attribute in its class
            //and will use the seed class .getPc1 method to get the detail of PC1
            routersContainer.add(routerContainer); // using the add function from the JPanel to add the device to the container for the routers
            routersContainer.add(Box.createHorizontalGlue());//space between components
            routerContainer = this.createDeviceContainer(this.parentPanel.getSeed().getPc2());// change the variable routerContainer to hold a PC2 now
            routersContainer.add(routerContainer);
            routersContainer.add(Box.createHorizontalGlue());
        } else {
            // if the routers hostname is not John's networK then the means it is in the second router view and will make the other two PC object buttons
            // that will be added to the contianer
            routersContainer.add(Box.createHorizontalGlue());
            routerContainer = this.createDeviceContainer(this.parentPanel.getSeed().getPc3());
            routersContainer.add(routerContainer);
            routersContainer.add(Box.createHorizontalGlue());

            routerContainer = this.createDeviceContainer(this.parentPanel.getSeed().getPc4());
            routersContainer.add(routerContainer);
            routersContainer.add(Box.createHorizontalGlue());
        }

        JButton displayRouterTableBtn = new JButton("Display Router Table");  // Button to display the router's routing table
        displayRouterTableBtn.addActionListener(e -> {
            TableView tableView = new TableView(this.router.getHostname() + ": " + "Routing Table", this.router.getRoutingTable(), true);
        });  // Action listener to display the routing table in a new TableView

        container.add(backBtn);  // Add back button to the container
        container.add(routersContainer);  // Add the routers container to the main container
        container.add(Box.createRigidArea(new Dimension(5, 20)));  // SPACE FOR THE COMPONENT OBJECTS IN THE CONTAINER
        container.add(displayRouterTableBtn);  // Add the display button to the container

        // Centre the details and buttons above and below the devices.
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        this.add(container);  // Add the main container to this panel

        this.setVisible(true);  // Make this panel visible
    }

    private JPanel createDeviceContainer(PC device) {
        // Panel name for each Dev which is a PC here will make a string from the device HostName
        String frameName = "Device:" + device.getHostname() + device.hashCode();
        // hashCode() will return a unique int value for each of the objects

        // Panel for holding individual device information
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new JLabel(device.getHostname()));  // Add the device hostname label to the container

        JButton btn = new JButton(new ImageIcon("src/images/pc.png"));  // Button with PC icon
        btn.addActionListener(e -> this.parentPanel.getController().showFrame(frameName));  // Action listener to show device details

        this.parentPanel.add(new PCView(device, this.parentPanel), frameName);  // Add new pc view to home frame
        this.pcDeviceBtns.add(btn);  // Add button to the list
        container.add(btn);  // Add button to the device container

        // Centre the PC name above its button.
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        return container;  // Return the container
    }
}
