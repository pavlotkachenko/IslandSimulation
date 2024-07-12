package org.island;

import org.island.entity.Simulation;
import org.island.location.Island;
import org.island.repo.MapCreator;
import org.island.repo.factory.EntityFactory;
import org.island.repo.factory.Factory;
import org.island.services.IslandSimulationService;
import org.island.settings.Config;
import org.island.view.ConsoleView;
import org.island.view.View;


public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and Welcome to the Island Simulation!");

        Config config = Config.getConfig();
        Factory entityFactory = new EntityFactory(); //TODO: to be applied nested Strategy
        MapCreator mapCreator = new MapCreator(entityFactory); //TODO: Replace by Service, extend this Service by DTO
        Island island = mapCreator.createIsland(config); //TODO: need to be safety removed when logic moved to Service above
        View view = new ConsoleView(island, entityFactory); //TODO: Leave interface for console view
        Simulation game = new Simulation(island, entityFactory, view); //TODO: Move to IslandSimulationService, OrganismSimulation
        IslandSimulationService gameWorker = new IslandSimulationService(game); //TODO:
        gameWorker.start();

    }
}