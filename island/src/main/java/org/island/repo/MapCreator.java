package org.island.repo;

import org.island.entity.Organism;
import org.island.location.Island;
import org.island.location.Location;
import org.island.repo.factory.Factory;
import org.island.repo.maps.Residents;
import org.island.settings.Config;
import org.island.util.Randomizer;

import java.util.*;

public class MapCreator {

    private final Factory entityFactory;
    private int rows;
    private int columns;

    public MapCreator(Factory factory) {
        this.entityFactory = factory;
    }

    public Island createIsland(Config config) {
        this.rows = config.getRows();
        this.columns = config.getColumns();

        Island island = new Island(rows, columns);

        createEmptyGrid(island);
        populateIsland(island);
        findLocationNeighbors(island);

        return island;
    }

    private void createEmptyGrid(Island island) {
        Location[][] grid = island.getIslandGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                grid[row][column] = new Location(row, column);
            }
        }
    }

    private void populateIsland(Island island) {
        Location[][] grid = island.getIslandGrid();
        Map<String, Organism> prototypes = entityFactory.getPrototypes();

        for (Location[] value : grid) {
            for (Location location : value) {
                Residents residents = location.getResidents();

                for (Organism organism : prototypes.values()) {
                    String type = organism.getType();
                    int maxCount = organism.getMaxPopulation();
                    int count = Randomizer.random(maxCount / 2, maxCount);
                    Set<Organism> organismSet = new HashSet<>();

                    for (int i = 0; i < count; i++) {
                        organismSet.add(entityFactory.create(type));
                    }
                    residents.put(type, organismSet);
                }
            }
        }
    }

    private void findLocationNeighbors(Island island) {
        Location[][] grid = island.getIslandGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Location location = grid[row][col];
                List<Location> neighbours = findNeighbors(row, col, grid);
                location.setDirection(neighbours);
            }
        }
    }

    private List<Location> findNeighbors(int row, int col, Location[][] grid) {
        List<Location> result = new ArrayList<>();
        if (row > 0) result.add(grid[row - 1][col]);
        if (col > 0) result.add(grid[row][col - 1]);
        if (row < rows - 1) result.add(grid[row + 1][col]);
        if (col < columns - 1) result.add(grid[row][col + 1]);

        return result;
    }
}
