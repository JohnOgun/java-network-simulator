package components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Component;

import javax.swing.*;

import lib.PC;

public class PCView extends JPanel {
    private PC pc;
    private HomeView parentPanel;

    public PCView(PC pc, HomeView parentPanel) {
        // PC View constructor
        this.pc = pc;// assign PC object passed to this instance of the PcView pc object to
        this.parentPanel = parentPanel;
        //Assign HomeView object to the parentpanel attribute of the class

        // Add padding around the screen and let the content fill the panel.
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Containers
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(pc.getHostname());
        heading.setFont(new Font(heading.getFont().getName(), Font.BOLD, 22));

        ImageIcon backIcon = new ImageIcon("src/images/back.png");
        JButton backBtn = new JButton("Back to network", new ImageIcon(
                backIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        // action listener for the class to which will implement the action listener for the back button
        // e is an instance of the of the ActionEvent
        // the -> indicates the function that should be used when the button is pressed
        backBtn.addActionListener(e -> {
            boolean isRouter1 = this.pc.getDefaultGateway().getHostname().equals("Johns Network");
            // boolean check to see which router the PC object is connected to so
            // that the back button will navigate to that specific router

            if (isRouter1) {// if true go to the router1 view if false go to the router2 view
                this.parentPanel.getController().showFrame("router1");
            } else {
                this.parentPanel.getController().showFrame("router2");
            }
        });
        //container.add(backBtn);// add the button to the container variable

        container.add(heading);
        container.add(Box.createRigidArea(new Dimension(5, 10)));
        // The container is used to add an createRigidArea to the itself this area
        // is used to make space between the components similar to the createVerticalGlue
        // this is done by using the dimensions of (width=5,height=10)
        container.add(new JLabel("IP Address: " + pc.getIpAddress()));
        // add a new label to the container that uses the PC object getIpAddress function
        // which is a display of code abstraction in OOP concepts due the class without knowing how the getIpAddress method is used to
        container.add(Box.createRigidArea(new Dimension(5, 10)));
        container.add(new JLabel("MAC Address: " + pc.getMacAddress()));//
        container.add(Box.createRigidArea(new Dimension(5, 20)));

        JButton localPingBtn = new JButton("Local Ping");// New button made with the text Local Ping
        localPingBtn.addActionListener(e -> this.runLocalPing());
        // adding of a action listener is run this PCView instance of the local ping function form the PC class

        JButton remotePingBtn = new JButton("Remote Ping");
        remotePingBtn.addActionListener(e -> this.runRemotePing());

        JButton displayArpBtn = new JButton("View ARP table");
        displayArpBtn.addActionListener(e -> {
            // this action listener is used to display the a TableView of the PC Arp Table
            TableView tableView = new TableView(this.pc.getHostname() + ": " + "ARP Table", this.pc.getArpTable(), false);
            // Creating a new tableView using this PCViews instance of PC object
        });

        // Two rows keep the buttons readable in the existing 500px window.
        JPanel buttonsContainer = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        for (JButton button : new JButton[]{backBtn, localPingBtn, remotePingBtn, displayArpBtn}) {
            button.setMargin(new Insets(10, 8, 10, 8));
        }
        localPingBtn.setToolTipText("Ping a PC on the same network");
        remotePingBtn.setToolTipText("Ping a PC on the other network");

        //Add all the buttons to the button container
        buttonsContainer.add(backBtn);
        buttonsContainer.add(localPingBtn);
        buttonsContainer.add(remotePingBtn);
        buttonsContainer.add(displayArpBtn);


        container.add(Box.createVerticalStrut(12));
        container.add(buttonsContainer);// add the button container to the overall container/JPanel
        container.add(Box.createVerticalStrut(14));
        container.add(Box.createVerticalGlue());
        // Align the heading, details and buttons along the same left edge.
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                ((JComponent) component).setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        }
        this.add(container, BorderLayout.CENTER);// add the overall container to this instance of the PCView class

        this.setVisible(true);
        //Make this view visible in the window
    }

    private void runLocalPing() {
        String ipAddress = JOptionPane.showInputDialog(
                this, "Enter destination IP address:");

        // Cancel returns null, so stop before calling the ping method.
        if (ipAddress == null) {
            return;
        }

        // Allow spaces around the address when typing or pasting.
        ipAddress = ipAddress.trim();

        // Reject empty input, including input containing only spaces.
        if (ipAddress.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter an IP address.",
                    "Missing IP address", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pass the address to the PC's local ping simulation.
        pc.localPing(ipAddress);
    }

    private void runRemotePing() {
        String ipAddress = JOptionPane.showInputDialog(
                this, "Enter destination IP address:");

        // Cancel returns null, so stop before calling the ping method.
        if (ipAddress == null) {
            return;
        }

        // Allow spaces around the address when typing or pasting.
        ipAddress = ipAddress.trim();

        // Reject empty input, including input containing only spaces.
        if (ipAddress.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter an IP address.",
                    "Missing IP address", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pass the address to the PC's remote ping simulation.
        pc.remotePing(ipAddress);
    }
}
