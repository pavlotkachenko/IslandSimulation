package org.island.view;

import org.island.animals.GrassEater;
import org.island.animals.Predator;
import org.island.entity.Organism;
import org.island.location.Island;
import org.island.location.Location;
import org.island.repo.factory.Factory;
import org.island.repo.maps.Residents;
import org.island.settings.Config;

import java.util.*;

public class ConsoleView implements View {

    public static final int HEIGHT_DIAGRAM = 10;
    public static final int POPULATION_HIGH = 70; // int between 40 - 100
    public static final int POPULATION_AVERAGE = POPULATION_HIGH - 40;

    private final Island island;
    private final Factory factory;
    private int day = 0;

    public ConsoleView(Island island, Factory factory) {
        this.island = island;
        this.factory = factory;
    }

    @Override
    public void showStatistics() {
        Map<String, Integer> statsPredators = new HashMap<>();
        Map<String, Integer> statsGrassEaters = new HashMap<>();
        Map<String, Integer> statsPlants = new HashMap<>();

        Location[][] grid = island.getGrid();

        for (Location[] row : grid) {
            for (Location location : row) {
                var residents = location.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                Organism organism = set.stream().findAny().get();
                                String name = organism.getClass().getSimpleName();
                                String icon = organism.getIcon();
                                String info = icon + name;

                                if (organism instanceof Predator) {
                                    statsPredators.put(info, statsPredators.getOrDefault(info, 0) + set.size());
                                } else if (organism instanceof GrassEater) {
                                    statsGrassEaters.put(info, statsGrassEaters.getOrDefault(info, 0) + set.size());
                                } else {
                                    statsPlants.put(info, statsPlants.getOrDefault(info, 0) + set.size());
                                }
                            });
                }
            }
        }

        printStatistics(statsPredators, statsGrassEaters, statsPlants);
    }

    @Override
    public void showMap() {
        StringBuilder out = new StringBuilder("\n");
        Map<String, Organism> prototypes = factory.getPrototypes();
        List<Organism> organisms = new ArrayList<>(prototypes.values());
        Location[][] grid = island.getGrid();
        int organismsCount = organisms.size();
        Map<String, Integer> currentPopulation = countOrganisms(organisms, grid);
        drawDiagram(out, organisms, organismsCount, currentPopulation);
        out.append(" ".repeat(5)).append("|");
        drawIcons(out, organisms);
        System.out.println(out);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void printStatistics(Map<String, Integer> statsPredators, Map<String, Integer> statsGrassEaters, Map<String, Integer> statsPlants) {
        StringBuilder out = new StringBuilder("\n");
        out.append(String.format("--------Island DAY %d--------%n", day++));
        out.append("Predators:\n");
        out.append(MapToString(statsPredators));
        out.append("GrassEaters:\n");
        out.append(MapToString(statsGrassEaters));
        out.append("Plants:\n");
        out.append(MapToString(statsPlants));
        out.append("----------------------------");

        System.out.println(out);
    }

    private String MapToString(Map<String, Integer> map) {
        StringBuilder out = new StringBuilder();
        map.forEach((key, value) -> out.append(String.format("%19s - %d%n", key, value)));
        return out.toString();
    }

    private void drawDiagram(StringBuilder out, List<Organism> organisms, int organismsCount, Map<String, Integer> currentPopulation) {
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

    private void drawIcons(StringBuilder out, List<Organism> organisms) {
        List<Integer> skipList = List.of(3, 7, 10);  // icons with different width, so will be offset in this position

        for (int i = 0; i < organisms.size(); i++) {
            String icon = organisms.get(i).getIcon();
            out.append(icon);
            if (!skipList.contains(i)) {
                out.append(" ");
            }
        }
    }

    private Map<String, Integer> countOrganisms(List<Organism> organisms, Location[][] grid) {
        Map<String, Integer> result = new HashMap<>();
        for (Location[] row : grid) {
            for (Location location : row) {
                Residents residents = location.getResidents();
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

    private String fill(int row, int col, Map<String, Integer> currentPopulation, List<Organism> organisms) {
        Organism organism = organisms.get(col);
        String type = organism.getType();
        Config config = Config.getConfig();
        int mapRow = config.getRows();
        int mapCol = config.getColumns();
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

    private static class Color{
        public static final String RESET = "\u001B[0m";
        public static final String FILL_GREEN = "\u001B[42m";
        public static final String FILL_YELLOW = "\u001B[43m";
        public static final String FILL_RED = "\u001B[41m";
    }


}
