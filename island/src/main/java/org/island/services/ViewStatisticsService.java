package org.island.services;

import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ViewStatisticsService {

    public void showStatistics(Island island, int day) {
        Map<String, Integer> statsPredators = new HashMap<>();
        Map<String, Integer> statsGrassEaters = new HashMap<>();
        Map<String, Integer> statsPlants = new HashMap<>();

        Location[][] grid = island.getGRID();
        for (Location[] row : grid) {
            for (Location location : row) {
                Map<String, Set<OrganismDTO>> residents = location.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                OrganismDTO organism = set.stream().findAny().get();
                                EOrganisms organismType = organism.getOrganismType();
                                String name = organismType.getType();
                                String icon = Objects.nonNull(organism.getIcon()) ? organism.getIcon() : "";
                                String info = icon + name;

                                if (organismType.isAnimal()) {
                                    if (organismType.getGroupId() <= 4) {
                                        statsPredators.put(info, statsPredators.getOrDefault(info, 0) + set.size());
                                    } else {
                                        statsGrassEaters.put(info, statsGrassEaters.getOrDefault(info, 0) + set.size());
                                    }
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
