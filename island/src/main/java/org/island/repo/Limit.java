package org.island.repo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Limit {
    private double maxWeight;
    private int maxPopulation;
    private int speed;
}
