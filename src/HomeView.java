package components;

import javax.swing.*;

import lib.AppController;
import lib.Seed;

import java.awt.*;

public class HomeView extends JPanel {
    private JButton router1Btn;
    private JButton router2Btn;
    private CardLayout frames;
    private Seed seed;
    private AppController controller;

    public HomeView() {
        // Load all starter data
        this.seed = new Seed();

        // Layout for different panels
        this.frames = new CardLayout();

        this.setLayout(this.frames);

        // Initialise buttons
        this.router1Btn = new JButton();// calling the default constructor for the Jbutton to make an new button
        this.router1Btn.setIcon(new ImageIcon("src/images/router.png"));// assign an image to the button for the oruter
        this.router2Btn = new JButton();
        this.router2Btn.setIcon(new ImageIcon("src/images/router.png"));

        JLabel heading = new JLabel();// Setting of the label for the HomeView
        heading.setText("<html><body style=\"text-align: center\">Java Simulation of OSPf using<br>a network of pc's, routers & switches</body></html>");
        // the <html> is used the wrap the title in the form of HTML
        //<body style="text-align: center"> is used to store the text and also is used to center the text of the title
        //style="text-align: center" is from CSS
        //<br> is used to display the other part of the string as without it will be cut off as it means a new line
        // </body></html> is used to show the end of the heading text
        Font defFont = heading.getFont();
        // Used to get the default font for this simulation
        heading.setFont(new Font(defFont.getName(), Font.BOLD, 22));
        //set the font of the heading using the defFont variable , sent the font to bold and size to 22

        JPanel headingContainer = new JPanel();
        //JPanel object is used for the contain the heading
        headingContainer.add(Box.createHorizontalGlue());
        //Adding of a box glue to the container to centre the heading
        headingContainer.add(heading);//adding the heading to the container
        headingContainer.add(Box.createHorizontalGlue());// As two glues are used this will centre the heading component

        // Containers
        JPanel container = new JPanel();
        // calling the default Jpanel constructor to be
        // able to assign a new Jpanel to the container variable of type Jpanel
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // Set the layout of the container to be a new BoxLayout which is a part of the swing lib
        // BoxLayout uses a panel which is the new container and the BoxLayout.Y_AXIS)
        // makes sure the component is set in the vertical direction

        JPanel router1container = new JPanel();
        // this container will be used to hold the routers
        router1container.setLayout(new BoxLayout(router1container, BoxLayout.Y_AXIS));
        router1container.add(new JLabel("Router 1"));//adding a label to the router container
        router1container.add(this.router1Btn);
        // add to the container the router1 button for this instance of the home view

        JPanel router2container = new JPanel();
        router2container.setLayout(new BoxLayout(router2container, BoxLayout.Y_AXIS));
        router2container.add(new JLabel("Router 2"));
        router2container.add(this.router2Btn);

        JPanel routersContainer = new JPanel();
        // Overall container for the routers which will hold both router components
        routersContainer.setLayout(new BoxLayout(routersContainer, BoxLayout.X_AXIS));
        // The use of the BoxLayout.X_AXIS will be used to make the layout of this container
        // a horizontal so that both router are side by side

        routersContainer.add(Box.createHorizontalGlue());
        routersContainer.add(router1container);
        routersContainer.add(Box.createHorizontalGlue());
        routersContainer.add(router2container);
        routersContainer.add(Box.createHorizontalGlue());

        JPanel overallContainer = new JPanel();
        overallContainer.setLayout(new BoxLayout(overallContainer, BoxLayout.Y_AXIS));
        overallContainer.add(headingContainer);
        // invisible spacer
        overallContainer.add(Box.createRigidArea(new Dimension(5, 20)));
        overallContainer.add(routersContainer);

        // Create empty space between the component and every element on top or below
        // add the overall container to the window container for the View
        container.add(Box.createVerticalGlue());
        container.add(overallContainer);
        container.add(Box.createVerticalGlue());

        // Adding components to this instance of the HomeView using the add fucntionalilty of the JPanel object
        this.add(container, "home");
        // Give the HomeView component a constraint so that the view can be called later
        this.add(new RouterView(this.seed.getRouter1(), this), "router1");
        this.add(new RouterView(this.seed.getRouter2(), this), "router2");

        this.setVisible(true);
        // make the instance of the HomeView visible if set to false it is not visible
        this.frames.show(this, "home");
        //using the .show method for the CardLayout which
        // need the parent panel and the constraint name of the view that should be shown

        // Setup controller to control the cardlayout using the the homeView as the rootcontainer
        this.controller = new AppController(frames, this, this.seed);
    }

    public JButton getRouter1Btn() {
        return this.router1Btn;
    }

    public JButton getRouter2Btn() {
        return this.router2Btn;
    }

    public AppController getController() {
        return this.controller;
    }

    public Seed getSeed() {
        return this.seed;
    }

}
