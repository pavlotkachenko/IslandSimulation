package org.island.view;

import lombok.RequiredArgsConstructor;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.services.ViewMapService;
import org.island.services.ViewStatisticsService;

@RequiredArgsConstructor
public class ConsoleView implements View {
    private final Island island;
    private final OrganismFactory factory;
    private final ViewStatisticsService viewStatisticsService;
    private final ViewMapService viewMapService;
    private int day = 0;

    public ConsoleView(Island island, OrganismFactory factory) {
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

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}