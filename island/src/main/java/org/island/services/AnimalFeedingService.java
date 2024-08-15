package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Location;

import java.util.Map;
import java.util.Set;

public class AnimalFeedingService implements FeedingService {

    @Override
    public void eat(OrganismDTO organism, Location location) {
        location.getLock().lock();

        try {
            Map<String, Integer> ration = organism.getRation();

            for (Map.Entry<String, Integer> entry : ration.entrySet()) {
                String preyType = entry.getKey();
                int probability = entry.getValue();

                Set<OrganismDTO> preySet = location.getResidents().get(preyType);

                if (preySet != null && !preySet.isEmpty()) {
                    for (OrganismDTO prey : preySet) {
                        if (Math.random() * 100 < probability) {
                            organism.setCurrentWeight(Math.min(organism.getMaxWeight(),
                                    organism.getCurrentWeight() + prey.getCurrentWeight()));
                            prey.setAlive(false);
                            location.getResidents().get(preyType).remove(prey);
                            break;
                        }
                    }
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
