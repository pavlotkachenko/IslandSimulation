package org.island.services;


import org.island.entity.OrganismDTO;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.model.Location;
import org.island.settings.Config;
import org.island.util.Randomizer;

import java.util.*;

public class MapCreatorService {

    private final OrganismFactory organismFactory;
    private int rows;
    private int columns;

    public MapCreatorService(OrganismFactory factory) {
        this.organismFactory = factory;
    }

//    public Island(Config config) {

//        this.GRID = new Location[rows][columns];
//        initializeGrid(rows, columns);
//    }

    public Island createIsland(Config config) {
        Island island = new Island();
        initializeGrid(island, config);
        populateIsland(island);
        findLocationNeighbors(island);
        return island;
    }

    private void initializeGrid(Island island, Config config) {
        rows = config.getIslandSimulationConfig().getIslandSize().getRows();
        columns = config.getIslandSimulationConfig().getIslandSize().getColumns();
        island.setGRID(new Location[rows][columns]);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                island.getGRID()[i][j] = new Location(i, j);
            }
        }
    }


    private void populateIsland(Island island) {
        Location[][] grid = island.getGRID();
        Map<String, OrganismDTO> prototypes = organismFactory.getORGANISMS();
        for (Location[] value : grid) {
            for (Location location : value) {
                HashMap<String, Set<OrganismDTO>> residents = location.getResidents();
                for (OrganismDTO organism : prototypes.values()) {
                    String type = organism.getType();
                    int maxCount = organism.getMaxPopulation();
                    int count = Randomizer.random(maxCount / 2, maxCount);
                    Set<OrganismDTO> organismSet = new HashSet<>();

                    for (int i = 0; i < count; i++) {
                        organismSet.add(organismFactory.getORGANISMS().get(type));
                    }

                    residents.put(type, organismSet);
                }
                location.setResidents(residents);
            }
        }
    }

    private void findLocationNeighbors(Island island) {
        Location[][] grid = island.getGRID();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Location location = grid[row][col];
                List<Location> neighbours = findNeighbors(row, col, grid);
                location.setDirections(neighbours);
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