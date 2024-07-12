package org.island.services.view;

import org.island.animals.GrassEater;
import org.island.animals.Predator;
import org.island.entity.Organism;
import org.island.location.Island;
import org.island.location.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewStatisticsService {

        public void showStatistics(Island island, int day) {
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

            printStatistics(statsPredators, statsGrassEaters, statsPlants, day);
        }

        private void printStatistics(Map<String, Integer> statsPredators, Map<String, Integer> statsGrassEaters, Map<String, Integer> statsPlants, int day) {
            StringBuilder out = new StringBuilder("\n");
            out.append(String.format("--------Island DAY %d--------%n", day));
            out.append("Predators:\n");
            out.append(mapToString(statsPredators));
            out.append("GrassEaters:\n");
            out.append(mapToString(statsGrassEaters));
            out.append("Plants:\n");
            out.append(mapToString(statsPlants));
            out.append("----------------------------");

            System.out.println(out);
        }

        private String mapToString(Map<String, Integer> map) {
            StringBuilder out = new StringBuilder();
            map.forEach((key, value) -> out.append(String.format("%19s - %d%n", key, value)));
            return out.toString();
        }
    }

