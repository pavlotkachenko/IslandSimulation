package org.island.view;

import org.island.location.Island;
import org.island.repo.factory.Factory;
import org.island.services.view.ViewMapService;
import org.island.services.view.ViewStatisticsService;

public class ConsoleView implements View {

    private final Island island;
    private final Factory factory;
    private final ViewStatisticsService viewStatisticsService;
    private final ViewMapService viewMapService;
    private int day = 0;

    public ConsoleView(Island island, Factory factory) {
        this.island = island;
        this.factory = factory;
        this.viewStatisticsService = new ViewStatisticsService();
        this.viewMapService = new ViewMapService(factory);
    }

    @Override
    public void showStatistics() {
      viewStatisticsService.showStatistics(island, day++);
    }

    @Override
    public void showMap() {
        viewMapService.showMap(island);
    }
}
