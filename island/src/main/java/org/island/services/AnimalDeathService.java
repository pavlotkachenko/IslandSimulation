package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

public class AnimalDeathService implements DeathService {

    public AnimalDeathService(OrganismDTO organism, Location location) {
        super();
    }


    @Override
    public void die(OrganismDTO organism, Location location) {
        location.getLock().lock();

        try{

            if (organism.isAlive()){ //TODO: Review this block, maybe it's not needed
                organism.setAlive(false);
            }

            String type = organism.getType();
            location.getResidents().get(type).remove(this);
        } finally {
            location.getLock().unlock();
        }

    }
}
