package org.island.services.animal;

import org.island.entity.Organism;
import org.island.exceptions.OrganismNotFound;
import org.island.location.Location;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.Comparator;
import java.util.Set;

@Deprecated
public class FeedingService {

    public Organism getTarget(Ration ration, Residents residents) {
        Set<Organism> preys = residents.entrySet().stream()
                .filter(resident -> !resident.getValue().isEmpty())
                .filter(resident -> ration.containsKey(resident.getKey()))
                .max(Comparator.comparingInt(resident -> ration.get(resident.getKey())))
                .orElseThrow(() -> new OrganismNotFound("Target / prey not found"))
                .getValue();

        return preys.stream()
                .skip(Randomizer.random(0, preys.size()))
                .findFirst()
                .orElseThrow(() -> new OrganismNotFound("No prey available after filtering"));
    }

    public boolean attemptToKill(Organism prey, Ration ration) {
        if (prey.isAlive()) {
            String preyType = prey.getType();
            int chanceToKill = ration.get(preyType);
            int predatorTry = Randomizer.random(0, 100);
            if (predatorTry <= chanceToKill) {
                prey.getDead();
                return true;
            }
        }
        return false;
    }

    public void updateWeights(Organism prey, double currentWeight, double deltaWeight, Organism predator) {
        double currentTargetWeight = prey.getCurrentWeight();
        double predatorFinalWeight;

        if (deltaWeight > currentTargetWeight) {
            predatorFinalWeight = currentWeight + currentTargetWeight;
            prey.setCurrentWeight(0);
        } else {
            predatorFinalWeight = currentWeight + deltaWeight;
            prey.setCurrentWeight(currentTargetWeight - deltaWeight);
        }

        predator.setCurrentWeight(predatorFinalWeight);
    }

    public void processEating(Location location, Organism predator) {
        location.getLock().lock();
        try {
            Ration ration = predator.getRation();
            double currentWeight = predator.getCurrentWeight();
            double deltaWeight = predator.getMaxWeight() - currentWeight;
            Residents residents = location.getResidents();
            Organism prey = getTarget(ration, residents);

            if (attemptToKill(prey, ration)) {
                updateWeights(prey, currentWeight, deltaWeight, predator);
            }
        } finally {
            location.getLock().unlock();
        }
    }
}

