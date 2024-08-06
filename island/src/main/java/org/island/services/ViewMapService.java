package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.model.Location;
import org.island.settings.Config;

import java.util.*;

public class ViewMapService {
    public static final int HEIGHT_DIAGRAM = 10;
    public static final int POPULATION_HIGH = 70; // int between 40 - 100
    public static final int POPULATION_AVERAGE = POPULATION_HIGH - 40;

    private final OrganismFactory factory;

    public ViewMapService(OrganismFactory factory) {
        this.factory = factory;
    }

    public void showMap(Island island) {
        StringBuilder out = new StringBuilder("\n");
        Map<String, OrganismDTO> prototypes = factory.getORGANISMS();
        List<OrganismDTO> organisms = new ArrayList<>(prototypes.values());
        Location[][] grid = island.getGrid();
        int organismsCount = organisms.size();
        Map<String, Integer> currentPopulation = countOrganisms(organisms, grid);
        drawDiagram(out, organisms, organismsCount, currentPopulation);
        out.append(" ".repeat(5)).append("|");
        drawIcons(out, organisms);
        System.out.println(out);
    }

    private void drawDiagram(StringBuilder out, List<OrganismDTO> organisms, int organismsCount, Map<String, Integer> currentPopulation) {
        for (int row = 0; row < HEIGHT_DIAGRAM; row++) {
            int percent = 100;
            out.append(row == 0
                    ? String.format("%-3s %%|", percent)
                    : String.format(" %-2s %%|", percent - row * 10)
            );

            for (int col = 0; col < organismsCount; col++) {
                String residentString = fill(row, col, currentPopulation, organisms);
                int LocationWidth = 5;
                out.append(String.format("%-" + LocationWidth + "s", residentString));
            }

            out.append("\n");
        }
    }

    private void drawIcons(StringBuilder out, List<OrganismDTO> organisms) {
        List<Integer> skipList = List.of(3, 7, 10);  // icons with different width, so will be offset in this position

        for (int i = 0; i < organisms.size(); i++) {
            String icon = organisms.get(i).getIcon();
            out.append(icon);
            if (!skipList.contains(i)) {
                out.append(" ");
            }
        }
    }

    private Map<String, Integer> countOrganisms(List<OrganismDTO> organisms, Location[][] grid) {
        Map<String, Integer> result = new HashMap<>();
        for (Location[] row : grid) {
            for (Location location : row) {
                HashMap<String, Set<OrganismDTO>> residents = location.getResidents();
                if (Objects.nonNull(residents)) {
                    organisms.forEach(organism -> {
                        String type = organism.getType();
                        result.put(type, result.getOrDefault(type, 0) + residents.get(type).size());
                    });
                }
            }
        }
        return result;
    }

    private String fill(int row, int col, Map<String, Integer> currentPopulation, List<OrganismDTO> organisms) {
        OrganismDTO organism = organisms.get(col);
        String type = organism.getType();
        Config config = Config.initialize();
        int mapRow = config.getIslandSimulationConfig().getIslandSize().getRows();
        int mapCol = config.getIslandSimulationConfig().getIslandSize().getColumns();
        int maxCount = organism.getMaxPopulation() * mapRow * mapCol;
        int currentCount = currentPopulation.get(type);
        double ratioPercent = 100.0 * currentCount / maxCount;
        double currentPercent = 100.0 - row * 10;

        String filler = choseFiller(ratioPercent, currentPercent);
        return "." + filler + Color.RESET + ".";
    }

    private String choseFiller(double ratioPercent, double currentPercent) {
        String filler = ".";
        String color = Color.RESET;

        if (currentPercent <= ratioPercent) {
            if (ratioPercent >= POPULATION_HIGH) {
                color = Color.FILL_GREEN;
            } else if (ratioPercent >= POPULATION_AVERAGE) {
                color = Color.FILL_YELLOW;
            } else {
                color = Color.FILL_RED;
            }
            filler = " ";
        }

        return color + filler;
    }

    private static class Color {
        public static final String RESET = "\u001B[0m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
        public static final String FILL_RED = "\u001B[41m";
    }
}
