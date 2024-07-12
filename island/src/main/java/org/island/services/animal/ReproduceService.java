package org.island.services.animal;

import lombok.AllArgsConstructor;
import org.island.abilities.Reproducible;
import org.island.entity.Organism;
import org.island.location.Location;
import org.island.repo.factory.EntityFactory;
import org.island.repo.maps.Residents;
import org.island.services.TaskService;

import java.util.Set;


public class ReproduceService implements TaskService {

    public void reproduce(Organism organism, Location location) {
        location.getLock().lock();
        try {
            Residents residents = location.getResidents();
            String type = organism.getType();
            Set<Organism> sameOrganisms = residents.get(type);
            int maxLimit = organism.getMaxPopulation();

            if(sameOrganisms.contains(this) && sameOrganisms.size() >= 2 && sameOrganisms.size() < maxLimit){
                 organism = EntityFactory.getFactory().create(type);
                sameOrganisms.add(organism);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    @Override
    public void run() {
        organism.reproduce(location);
    }
}
