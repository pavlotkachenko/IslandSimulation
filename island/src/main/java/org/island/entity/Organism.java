package org.island.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.island.abilities.Reproducible;
import org.island.repo.Limit;
import org.island.repo.maps.Ration;
import org.island.util.Fullness;

@AllArgsConstructor
@EqualsAndHashCode
public abstract class Organism implements Reproducible {
    private final OrganismInfo info;
    private final Limit limit;

    public String getType() {
        return info.getType();
    }

    public int getGroupId() {
        return info.getGroupId();
    }

    public boolean isAlive() {
        return info.isAlive();
    }

    public double getCurrentWeight() {
        return info.getCurrentWeight();
    }

    public void setCurrentWeight(double currentWeight) {
        info.setCurrentWeight(currentWeight);
    }

    public String getIcon() {
        return info.getIcon();
    }

    public Fullness getFullness() {
        return info.getFullness();
    }

    public void setFullness(Fullness fullness) {
        info.setFullness(fullness);
    }

    public Ration getRation() {
        return info.getRation();
    }

    public double getMaxWeight() {
        return limit.getMaxWeight();
    }

    public int getMaxPopulation() {
        return limit.getMaxPopulation();
    }

    public int getSpeed() {
        return limit.getSpeed();
    }

    public void getDead() {
        info.setAlive(false);
    }
}
