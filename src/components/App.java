package components;

import javax.swing.*;

public class App extends JFrame {
    // This class inherits this is known by the use of extends in the above line
    // JFRAME class meaning it can use the methods of a frame
    private HomeView home;

    public App() {

        // setting up the home page to add to application
        this.home = new HomeView();

        // Window Configuration
        this.setSize(500, 500);
        this.setTitle("Java ping simulation of a network with OSPF");
        this.setVisible(true);
        // If false we won't be able to see the display of the home page with routers
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //How the frame is close needs to be done
        // by using the X in top right or the app will still run
        this.setResizable(false);
        //Does not adjust size

        this.add(home);
        // add it to this instance of the app running currently
    }
}
