package org.island.services;

import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.model.Location;
import org.island.settings.Config;

import java.util.*;
import java.util.stream.Collectors;

public class ViewMapService {

    private static final int HEIGHT_DIAGRAM = 10;
    private static final int POPULATION_HIGH = 90;
    private static final int POPULATION_AVERAGE = 50;

    private final OrganismFactory factory;

    public ViewMapService(OrganismFactory factory) {
        this.factory = factory;
    }

    public void showMap(Island island) {
        StringBuilder out = new StringBuilder("\n");
        Location[][] grid = island.getGRID();
        List<OrganismDTO> organisms = createOrganismPrototypes();
        Map<EOrganisms, Integer> currentPopulation = countOrganisms(organisms, grid);
        drawPopulationDiagram(out, organisms, currentPopulation);
        drawIcons(out, organisms);
        System.out.println(out);
    }

    private List<OrganismDTO> createOrganismPrototypes() {
        return Arrays.stream(EOrganisms.values())
                .map(OrganismFactory::createOrganism)
                .collect(Collectors.toList());
    }

    private void drawPopulationDiagram(StringBuilder out, List<OrganismDTO> organisms, Map<EOrganisms, Integer> currentPopulation) {
        for (int row = 0; row < HEIGHT_DIAGRAM; row++) {
            int percent = 100 - row * 10;
            out.append(String.format("%-3d %%|", percent));

            for (OrganismDTO organism : organisms) {
                String residentString = getPopulationFill(row, organism, currentPopulation);
                out.append(residentString);
            }


            out.append(Color.RESET).append("\n");
        }
    }

    private void drawIcons(StringBuilder out, List<OrganismDTO> organisms) {
        out.append(" ".repeat(5));
        organisms.forEach(organism -> out.append(organism.getIcon()).append(" "));
        out.append("\n");
    }

    private Map<EOrganisms, Integer> countOrganisms(List<OrganismDTO> organisms, Location[][] grid) {
        Map<EOrganisms, Integer> result = new EnumMap<>(EOrganisms.class);
        for (Location[] row : grid) {
            for (Location location : row) {
                location.getResidents().values().stream()
                        .flatMap(Set::stream)
                        .map(OrganismDTO::getOrganismType)
                        .forEach(type -> result.merge(type, 1, Integer::sum));
            }
        }
        return result;
    }

    private String getPopulationFill(int row, OrganismDTO organism, Map<EOrganisms, Integer> currentPopulation) {
        Config config = Config.initialize();
        int totalCells = config.getIslandSimulationConfig().getIslandSize().getRows() *
                config.getIslandSimulationConfig().getIslandSize().getColumns();
        int maxCount = organism.getMaxPopulation() * totalCells;
        int currentCount = currentPopulation.getOrDefault(organism.getOrganismType(), 0);
        double ratioPercent = 100.0 * currentCount / maxCount;
        double currentPercent = 100.0 - row * 10;
        System.out.println(ratioPercent + " " + currentPercent);
        return ratioPercent >= currentPercent
                ? chooseColorBasedOnPopulation(ratioPercent) + "   "
                : Color.RESET + "   ";
    }

    private String chooseColorBasedOnPopulation(double ratioPercent) {
        if (ratioPercent >= POPULATION_HIGH) {
            return Color.FILL_GREEN;
        } else if (ratioPercent >= POPULATION_AVERAGE) {
            return Color.FILL_YELLOW;
        } else {
            return Color.FILL_RED;
        }
    }

    private static class Color {
        public static final String RESET = "\u001B[0m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
        public static final String FILL_RED = "\u001B[41m";
    }
}
