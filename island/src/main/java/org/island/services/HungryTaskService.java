package org.island.services;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.location.Location;
import org.island.settings.Config;
import org.island.util.Fullness;

public class HungryTaskService extends TaskService {

    private static final double WELL_FED = 0.75; //TODO need different logic
    private static final double ALL_RIGHT = WELL_FED - 0.2;
    public static final double HUNGRY = ALL_RIGHT - 0.4;

    public HungryTaskService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
        double currentWeight = organism.getCurrentWeight();
        double maxWeight = organism.getMaxWeight();
        Config config = Config.getConfig();
        double weightNextDay = currentWeight - maxWeight * config.getWeightDecreaseFactor();
        double weightRatio = weightNextDay / maxWeight;

        if (organism instanceof Animal) {
            Animal animal = (Animal) organism;
            Fullness fullness = animal.getFullness();

            if (weightRatio >= WELL_FED && fullness != Fullness.WELL_FED) {
                animal.setFullness(Fullness.WELL_FED);
            } else if (weightRatio >= ALL_RIGHT && weightRatio < WELL_FED && fullness != Fullness.ALL_RIGHT) {
                animal.setFullness(Fullness.ALL_RIGHT);
            } else if (weightRatio >= HUNGRY && weightRatio < ALL_RIGHT && fullness != Fullness.HUNGRY) {
                animal.setFullness(Fullness.HUNGRY);
            } else if (weightRatio < HUNGRY && fullness != Fullness.WILL_BE_FINE) {
                animal.setFullness(Fullness.WILL_BE_FINE);
            }
        }

        double deathThreshold = config.getDeathThreshold();
        if (weightRatio < deathThreshold) {
            organism.killOrganism(location);
        } else {
            organism.setCurrentWeight(weightNextDay);
        }
    }
}
