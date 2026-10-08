package lib;

/**
 * The purpose of this class is to provide starter data for the application.
 */
public class Seed {
    private Router r1;
    private Router r2;
    private PC pc1;
    private PC pc2;
    private PC pc3;
    private PC pc4;

    public Seed() {
        // Initialise all objects
        this.r1 = new Router("Johns Network", "00:1A:2B:3C:4D:5E", "192.11.9.1");
        pc1 = new PC("192.11.9.2", "00-B0-D0-63-C2-26", "Seans PC");
        pc2 = new PC("192.11.9.3", "90-09-DF-EA-D9-59", "Niamh's PC");
        r1.connectDevice(pc1);
        r1.connectDevice(pc2);

        r2 = new Router("Peters Network", "0A:2B:8B:3C:4D:5E", "186.23.13.1");
        pc3 = new PC("186.23.13.2", "08:00:27:93:A5:FD", "Marys PC");
        pc4 = new PC("186.23.13.3", "A0:B1:C2:D3:E4:F5", "Pauls PC");
        r2.connectDevice(pc3);
        r2.connectDevice(pc4);

        // Connect routers
        this.r1.connectDevice(r2);

    }

    // Getters
    public Router getRouter1() {
        return this.r1;
    }

    public Router getRouter2() {
        return this.r2;
    }

    public PC getPc1() {
        return this.pc1;
    }

    public PC getPc2() {
        return this.pc2;
    }

    public PC getPc3() {
        return this.pc3;
    }

    public PC getPc4() {
        return this.pc4;
    }
}

