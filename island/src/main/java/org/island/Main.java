package org.island;

import org.island.initializer.Initializer;
import org.island.simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and Welcome to the Island Simulation!");

        Initializer initializer = new Initializer();
        Simulation simulation = initializer.initialize();

        simulation.run();
    }
}