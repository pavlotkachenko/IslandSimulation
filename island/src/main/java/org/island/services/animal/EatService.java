package org.island.services.animal;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.exceptions.OrganismNotFound;
import org.island.location.Location;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class EatService {
    public void eat(Animal animal, Location location) {
        location.getLock().lock();

        try {
            double currentWeight = animal.getCurrentWeight();
            double deltaWeight = animal.getMaxWeight() - currentWeight;
            
            Ration ration = animal.getRation();
            Residents residents = location.getResidents();
            Organism prey = getTarget(ration, residents);
            String preyType = prey.getType();

            if (prey.isAlive()) {
                int chanceToKill = ration.get(preyType);
                int predatorTry = Randomizer.random(0, 100);

                if (predatorTry <= chanceToKill) {
                    prey.getDead();
                } else return;
            }

            double currentTargetWeight = prey.getCurrentWeight();
            double predatorFinalWeight;

            if (deltaWeight > currentTargetWeight) {
                predatorFinalWeight = currentWeight + currentTargetWeight;
                prey.setCurrentWeight(0);
            } else {
                predatorFinalWeight = currentWeight + deltaWeight;
                prey.setCurrentWeight(currentTargetWeight - deltaWeight);
            }

            animal.setCurrentWeight(predatorFinalWeight);
        } finally {
            location.getLock().unlock();
        }
    }

    public boolean findSomeFood(Ration ration, Residents residents) {
        return residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .map(Map.Entry::getKey)
                .anyMatch(ration::containsKey);
    }

    private Organism getTarget(Ration ration, Residents residents) {
        Set<Organism> preys = residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .filter(resident -> ration.containsKey(resident.getKey()))
                .max(Comparator.comparingInt(resident -> ration.get(resident.getKey())))
                .orElseThrow(() -> new OrganismNotFound("target / prey not found"))
                .getValue();

        return preys
                .stream()
                .skip(Randomizer.random(0, preys.size()))
                .iterator()
                .next();
    }
}
