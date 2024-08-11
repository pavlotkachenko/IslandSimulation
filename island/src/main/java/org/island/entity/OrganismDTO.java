package org.island.entity;


import lombok.*;

import java.util.HashMap;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganismDTO {
    private String type;
    private int groupId;
    private int organismId;
    private boolean isAlive;
    private double currentWeight;
    private String icon;
    private HashMap<String, Integer> ration;
    private double maxWeight;
    private int maxPopulation;
    private int speed;

    public EOrganisms getOrganismType() {
        try {
            return EOrganisms.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EOrganisms.HERB;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganismDTO that)) return false;
        return groupId == that.groupId && organismId == that.organismId && isAlive == that.isAlive && Double.compare(currentWeight, that.currentWeight) == 0 && Double.compare(maxWeight, that.maxWeight) == 0 && maxPopulation == that.maxPopulation && speed == that.speed && Objects.equals(type, that.type) && Objects.equals(icon, that.icon) && Objects.equals(ration, that.ration);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrganismDTO;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, groupId, organismId, isAlive, currentWeight, icon, ration, maxWeight, maxPopulation, speed);
    }
}
