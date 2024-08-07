package org.island.model;

import lombok.Getter;
import org.island.settings.Config;


@Getter
public class Island {
    private final Location[][] grid;

    public Island(Config config) {
        int rows = config.getIslandSimulationConfig().getIslandSize().getRows();
        int columns = config.getIslandSimulationConfig().getIslandSize().getColumns();
        this.grid = new Location[rows][columns];
        initializeGrid(rows, columns);
    }

    private void initializeGrid(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Location(i, j);
            }
        }
    }
}