package org.island;

import org.island.entity.Simulation;
import org.island.location.Island;
import org.island.repo.MapCreator;
import org.island.repo.factory.EntityFactory;
import org.island.repo.factory.Factory;
import org.island.services.SimulationWorkerService;
import org.island.settings.Config;
import org.island.view.ConsoleView;
import org.island.view.View;


public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and Welcome to the Island Simulation!");

        Config config = Config.getConfig();
        Factory entityFactory = new EntityFactory();
        MapCreator mapCreator = new MapCreator(entityFactory);
        Island island = mapCreator.createIsland(config);
        View view = new ConsoleView(island, entityFactory);
        Simulation game = new Simulation(island, entityFactory, view);
        SimulationWorkerService gameWorker = new SimulationWorkerService(game);
        gameWorker.start();

    }
}