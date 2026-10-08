# Java Network Simulator

I built this project during the final year of my Electronic Engineering degree in 2023/24. The aim was to demonstrate how PCs, routers and switches interact in a small network, using Java and a Swing interface.

I revisited it in 2026 to get it running again and replace the missing interface images. This repository contains that restored version.

## What it does

The simulation starts with two connected routers and four PCs. Through the interface, you can:

- View routers and their connected PCs.
- Check device IP and MAC addresses.
- View ARP and routing tables.
- Run simulated local and remote ping operations.

The interface displays device information, while the IntelliJ console shows the steps taken during the simulation.

## Running the project

You need a Java JDK and IntelliJ IDEA.

1. Clone or download this repository.
2. Open the project folder in IntelliJ.
3. Select an installed JDK under **File → Project Structure → Project SDK**.
4. Make sure `src` is marked as a Sources Root.
5. Open `src/Main.java` and run its `main` method.

Keep the run configuration’s working directory set to the project folder. The interface loads its icons from `src/images/`.

## Trying it out

Select **Johns Network**, then open **Seans PC**.

- For a local ping, enter `192.11.9.3`, the address of Niamh’s PC.
- For a remote ping, enter `186.23.13.2`, the address of Mary’s PC on the other router.

Watch the console output to follow the simulated communication.

## Simplifications

This is an educational simulation. It does not send real network packets.

The original coursework explored simplified OSPF concepts. The `ospfSearch` method selects the lowest-metric matching entry from the available routing table entries. It does not implement the full OSPF protocol or calculate shortest paths using Dijkstra’s algorithm.

## Next improvements

I plan to improve input validation, tidy the interface and add tests for routing and failure cases.
