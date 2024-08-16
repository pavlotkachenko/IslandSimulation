package org.island.settings;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class IslandSimulationConfig {
    private IslandSize islandSize;
    private SimulationParameters simulation;

    @Getter
    @Setter
    public static class IslandSize {
        private int rows;
        private int columns;
    }

    @Getter
    @Setter
    public static class SimulationParameters {
        private int period;
        private double startWeightFactor;
        private double weightDecreaseFactor;
        private double deathThreshold;
    }
}