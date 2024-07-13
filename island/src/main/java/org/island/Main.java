package org.island;

import org.island.location.Island;
import org.island.services.PopulateIslandService;
import org.island.services.SimulationService;
import org.island.settings.Config;
import org.island.view.ConsoleView;
import org.island.view.View;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and Welcome to the Island Simulation!");

        Config config = Config.getConfig();
        Island island = new Island(config);
        PopulateIslandService populateIslandService = new PopulateIslandService();
        View view = new ConsoleView(island);
        SimulationService gameWorker = new SimulationService(view, island, config.getPeriod(), populateIslandService);
        gameWorker.start();
    }
}
