package org.island.services.animal;

import org.island.entity.Organism;
import org.island.location.Location;

public class OrganismKillService {
    public void killOrganism(Organism organism, Location location){
        location.getLock().lock();
        try{
            String type = organism.getType();
            location.getResidents().get(type).remove(this);
        } finally {
            location.getLock().unlock();
        }
    }
}
