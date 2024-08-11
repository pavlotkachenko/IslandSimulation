package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.exceptions.OrganismNotFound;
import org.island.model.Location;
import org.island.model.Ration;
import org.island.model.Residents;
import org.island.util.Randomizer;

import java.util.Comparator;
import java.util.Set;

public class FeedAnimalService implements FeedingService{


    @Override
    public void eat(OrganismDTO organism, Location location) {
        location.getLock().lock();

        try {
            Ration ration = (Ration) organism.getRation();
            double currentWeight = organism.getCurrentWeight();
            double deltaWeight = organism.getMaxWeight() - currentWeight;
            Residents residents = (Residents) location.getResidents();
            OrganismDTO prey = getTarget(ration, residents);
            String preyType = prey.getType();

            if (prey.isAlive()) {
                int chanceToKill = ration.get(preyType);
                int predatorTry = Randomizer.random(0, 100);

                if (predatorTry <= chanceToKill) {
                    prey.setAlive(false);
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

           organism.setCurrentWeight(predatorFinalWeight);
        } finally {
            location.getLock().unlock();
        }
    }

    private OrganismDTO getTarget(Ration ration, Residents residents) {
        Set<OrganismDTO> preys = residents
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
