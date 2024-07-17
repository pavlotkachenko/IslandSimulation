package org.island.settings;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public final class OrganismParameters {
    private Map<String, AnimalParams> limits;
    private Map<String, String> icons;
    private Map<String, Map<String, Integer>> foodMap;

    @Getter
    @Setter
    public static class AnimalParams {
        private double maxWeight;
        private int maxPopulation;
        private int speed;
    }
}