package org.island.services;

import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.model.Location;
import org.island.settings.Config;

import java.util.*;

public class ViewMapService {
    //TODO Вынести в отдельный файл конфигурации
    public static final int HEIGHT_DIAGRAM = 10;
    public static final int POPULATION_HIGH = 70; // int between 40 - 100
    public static final int POPULATION_AVERAGE = POPULATION_HIGH - 40;

    private final OrganismFactory factory;

    public ViewMapService(OrganismFactory factory) {
        this.factory = factory;
    }
    //TODO Карта отрисовывается, но некорректно
    public void showMap(Island island) {
        StringBuilder out = new StringBuilder("\n");
        Map<String, OrganismDTO> prototypes = factory.getORGANISMS();
        List<OrganismDTO> organisms = new ArrayList<>(prototypes.values());
        Location[][] grid = island.getGRID();
        int organismsCount = organisms.size();
        Map<EOrganisms, Integer> currentPopulation = countOrganisms(organisms, grid);
        drawDiagram(out, organisms, organismsCount, currentPopulation);
        out.append("\n").append(" ".repeat(5)).append("|");
        drawIcons(out, organisms);
        System.out.println(out);
    }

    private void drawDiagram(StringBuilder out, List<OrganismDTO> organisms, int organismsCount, Map<EOrganisms, Integer> currentPopulation) {
        for (int row = 0; row < HEIGHT_DIAGRAM; row++) {
            int percent = 100 - row * 10;
            out.append(String.format("%-3d %%|", percent));

            for (int col = 0; col < organismsCount; col++) {
                String residentString = fill(row, col, currentPopulation, organisms);
                int locationWidth = 4; // Adjust width based on icon size
                out.append(String.format("%-" + locationWidth + "s", residentString));
            }

            out.append("\n");
        }
    }

    private void drawIcons(StringBuilder out, List<OrganismDTO> organisms) {
        int iconWidth = 1; // Width for spacing icons
        for (int i = 0; i < organisms.size(); i++) {
            String icon = organisms.get(i).getIcon();
            out.append(icon);
            if (i < organisms.size() - 1) {
                out.append("  ".repeat(iconWidth));
            }
        }
    }

    private Map<EOrganisms, Integer> countOrganisms(List<OrganismDTO> organisms, Location[][] grid) {
        Map<EOrganisms, Integer> result = new EnumMap<>(EOrganisms.class);
        for (Location[] row : grid) {
            for (Location location : row) {
                Map<String, Set<OrganismDTO>> residents = location.getResidents();
                if (Objects.nonNull(residents)) {
                    for (OrganismDTO organism : organisms) {
                        EOrganisms type = organism.getOrganismType();
                        Set<OrganismDTO> organismsSet = residents.get(type.getType());
                        if (organismsSet != null) {
                            int count = result.getOrDefault(type, 0) + organismsSet.size();
                            result.put(type, count);
                        }
                    }
                }
            }
        }
        return result;
    }

    private String fill(int row, int col, Map<EOrganisms, Integer> currentPopulation, List<OrganismDTO> organisms) {
        OrganismDTO organism = organisms.get(col);
        EOrganisms type = organism.getOrganismType();
        Config config = Config.initialize();
        int mapRow = config.getIslandSimulationConfig().getIslandSize().getRows();
        int mapCol = config.getIslandSimulationConfig().getIslandSize().getColumns();
        int maxCount = organism.getMaxPopulation() * mapRow * mapCol;
        int currentCount = currentPopulation.getOrDefault(type, 0);
        double ratioPercent = 100.0 * currentCount / maxCount;
        double currentPercent = 100.0 - row * 10;

        String filler = choseFiller(ratioPercent, currentPercent);
        return filler;
    }

    private String choseFiller(double ratioPercent, double currentPercent) {
        String color;

        if (currentPercent <= ratioPercent) {
            if (ratioPercent >= POPULATION_HIGH) {
                color = Color.FILL_GREEN;
            } else if (ratioPercent >= POPULATION_AVERAGE) {
                color = Color.FILL_YELLOW;
            } else {

                color = Color.FILL_RED;
            }
            return color + "  "; // Два пробела для заполнения
        }

        return Color.RESET + ".."; // Два точки для пустого места
    }

    private static class Color {
        public static final String RESET = "\u001B[0m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
        public static final String FILL_RED = "\u001B[41m";
    }
}
