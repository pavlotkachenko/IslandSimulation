package org.island;

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
        Config config = Config.initialize();

        //Init island
        Island island = new Island(config);

        //Init view
        View view = new ConsoleView(island);

        // Initialize simulation
        Simulation gameWorker = getSimulation(config, island, view);

        //Populate island
        gameWorker.populateIsland(island);

        //StartSimulation
        gameWorker.runCycle();
    }


    private static Simulation getSimulation(Config config, Island island, View view) {
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
}
