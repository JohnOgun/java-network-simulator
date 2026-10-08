# Java Network Simulator

I built this project during the final year of my Electronic Engineering degree in 2023/24. The aim was to demonstrate how PCs, routers and switches interact in a small network, using Java and a Swing interface.

I revisited it in 2026 to get it running again. Since then, I’ve improved input handling, fixed some missing-destination errors and tidied the home, PC and router screens.

## What it does

The simulation starts with two connected routers and four PCs. Through the interface, you can:

- View routers and their connected PCs.
- Check device IP and MAC addresses.
- View ARP and routing tables.
- Run simulated local and remote ping operations.

The interface displays device information, while the IntelliJ console shows the steps taken during the simulation.

Cancelled or blank input stops without attempting a ping. Invalid IPv4 addresses produce a message showing the entered address and an example of the expected format.

Remote Ping suggests using Local Ping when the destination is on the same network. Missing ARP entries, unknown remote networks and empty route searches produce a message instead of continuing with the ping.

## Running the project

I currently run the project using Java 21 and IntelliJ IDEA.

1. Clone or download this repository.
2. Open the project folder in IntelliJ.
3. Select an installed JDK under **File → Project Structure → Project SDK**.
4. Make sure `src` is marked as a Sources Root.
5. Open `src/Main.java` and run its `main` method.

Keep the run configuration’s working directory set to the project folder. The interface loads its icons from `src/images/`.

The source is organised into:

- `src/components/` — Swing screens and table views.
- `src/lib/` — network objects and simulation logic.
- `src/images/` — interface icons.
- `src/Main.java` — application entry point.

## Trying it out

Select **Johns Network**, then open **Seans PC**.

- For a local ping, enter `192.11.9.3`, the address of Niamh’s PC.
- For a remote ping, enter `186.23.13.2`, the address of Mary’s PC on the other router.

You can also try these checks from Sean’s PC:

- Enter `999.11.9.3` to see the invalid-address message.
- Use Remote Ping with `192.11.9.3` to see the suggestion to use Local Ping.
- Use Local Ping with `192.11.9.99` to check the missing ARP-entry handling.
- Use Remote Ping with `10.0.0.2` to check the unknown-network handling.
- Use Remote Ping with `186.23.13.99` to check the missing-route handling.

Watch the console output to follow the simulated communication.

## Recent changes

- Organised the source files into their matching Java packages.
- Added checks for cancelled, blank and invalid ping input.
- Added messages for missing ARP entries, remote networks and routes.
- Added network names to the home-screen buttons.
- Spaced the PC buttons into two rows.
- Centred the router details and PC names.
- Added labelled back buttons with smaller icons.

## Simplifications and known issues

This is an educational simulation. It does not send real network packets.

The original coursework explored simplified OSPF concepts. The `ospfSearch` method selects the lowest-metric matching entry from the available routing table entries. It does not implement the full OSPF protocol or calculate shortest paths using Dijkstra’s algorithm.

The network comparison currently assumes `/24` networks, although the routing table displays a different subnet mask. These need to be made consistent.

The RTT calculation returns seconds but displays them as milliseconds, and the elapsed time accumulates across replies. Ping operations can temporarily freeze the interface because the simulated delays run on the Swing event thread.

## Next improvements

I plan to improve the table screens and show ping results inside the application. I also need to correct the timing calculations, make subnet handling consistent and add tests for the network logic.