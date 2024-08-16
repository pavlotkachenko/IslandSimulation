package org.island.initializer;

import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.services.*;
import org.island.simulation.Simulation;
import org.island.settings.Config;
import org.island.view.ConsoleView;
import org.island.view.View;

public class Initializer {

    public Simulation initialize() {
        // Load configuration
        Config config = loadConfig();

        // Init island and factory
        OrganismFactory factory = createFactory();

        // Init map creator service
        MapCreatorService mapCreatorService = new MapCreatorService(factory);

        // Init island and populate
        Island island = mapCreatorService.createIsland(config);

        // Init view
        View view = createView(island, factory);
        view.showStatistics();
        view.showMap();

        // Initialize simulation
        return createSimulation(config, island, view);
    }

    private Config loadConfig() {
        return Config.initialize();
    }

    private OrganismFactory createFactory() {
        return new OrganismFactory();
    }

    private View createView(Island island, OrganismFactory factory) {
        return new ConsoleView(island, factory);
    }

    private Simulation createSimulation(Config config, Island island, View view) {
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
                deathService,
                createFactory());
    }
}