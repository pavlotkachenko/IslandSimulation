package org.island;

import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.services.*;
import org.island.simulation.Simulation;
import org.island.settings.Config;
import org.island.view.ConsoleView;
import org.island.view.View;

public class Main {
    public static void main(String[] args) {
        //Welcome message
        System.out.println("Hello and Welcome to the Island Simulation!");

        //Load configuration
        Config config = loadConfig();

        //Init island and factory
        Island island = createIsland(config);
        OrganismFactory factory = createFactory();

        //Init view
        View view = createView(island, factory);

        // Initialize simulation
        Simulation simulation = createSimulation(config, island, view);

        //Populate island and start simulation
        runSimulation(simulation, island);
    }

    private static Config loadConfig() {
        return Config.initialize();
    }

    private static Island createIsland(Config config) {
        return new Island(config);
    }

    private static OrganismFactory createFactory() {
        return new OrganismFactory();
    }

    private static View createView(Island island, OrganismFactory factory) {
        return new ConsoleView(island, factory);
    }

    private static Simulation createSimulation(Config config, Island island, View view) {
        MovementService movementService = new AnimalMovementService();
        MatingService matingService = new AnimalMatingService();
        FeedingService feedingService = new AnimalFeedingService();
        DeathService deathService = new AnimalDeathService();

        return new Simulation(
                view,
                island,
                config.getIslandSimulationConfig().getSimulation().getPeriod(),
                movementService,
                matingService,
                feedingService,
                deathService);
    }

    private static void runSimulation(Simulation simulation, Island island) {
        simulation.populateIsland(island);
        simulation.runCycle();
    }
}
