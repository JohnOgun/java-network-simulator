package lib;

import java.awt.CardLayout; //CardLayout which is used to manage the different views of the simulation is imported from the java.Awt library
import java.awt.event.ActionEvent; // import the action listener from the awt package
import java.awt.event.ActionListener; // Action listener like mobile systems
import javax.swing.JButton;// Importing the Java Button object for GUI from the java.swing library

import components.HomeView; // import the home screen view from the components package of the class

/**
 * Controller for all transitions from one view to another view
 */
public class AppController implements ActionListener {
    JButton homeRouterBtn1; // Java Button variable for the  first router button
    JButton homeRouterBtn2; // Java Button variable for the second router button
    CardLayout frames; // CardLayout variable like Fragment manager in mobile controls which frag you will see
    HomeView rootContainer; // HomeView variable to hold the starting/root container

    // Constructor for AppController class
    public AppController(CardLayout frames, HomeView rootContainer, Seed seed) {
        this.frames = frames; // Assign the CardLayout (Fragment manager) object to the frames variable
        this.rootContainer = rootContainer; // Assign the passed HomeView object to the local rootContainer variable

        // Set all components to use this action listener
        this.homeRouterBtn1 = rootContainer.getRouter1Btn(); // Get the JButton for router 1 from the HomeView object
        this.homeRouterBtn1.addActionListener(this); // Add this controller as an action listener to the router 1 button
        this.homeRouterBtn2 = rootContainer.getRouter2Btn(); // Get the JButton for router 2 from the HomeView object
        this.homeRouterBtn2.addActionListener(this); // Add this controller as an action listener to the router 2 button
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ActionPerformed method required by
        // ActionListener implementation
        // Uses the Action Event type variable e for any button

        Object component = e.getSource();
        // using the get source of the action performed
        // compeonet will be the source of the object that needed a action listener

        // Check if the source is the router 1 button
        if (component.equals(this.homeRouterBtn1)) {
            this.frames.show(this.rootContainer, "router1"); // Show the "router1" frame in the root container
        }
        // Check if the source is the router 2 button
        else if (component.equals(this.homeRouterBtn2)) {
            this.frames.show(this.rootContainer, "router2"); // Show the "router2" frame in the root container
        }
    }

    // Method to show a specific frame in the root container
    public void showFrame(String frameName) {
        this.frames.show(this.rootContainer, frameName);
        // Show the frame by using the constraint name for example home or
        // router1 which will show the router view or homeView in the root container
    }
}
