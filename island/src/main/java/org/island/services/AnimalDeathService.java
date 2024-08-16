package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Location;

import java.util.UUID;

public class AnimalDeathService implements DeathService {



    @Override
    public void die(OrganismDTO organism, Location location) {
        location.getLock().lock();

        try {
            if (organism.isAlive()) {
                organism.setAlive(false);
            }

            String type = organism.getType();
            location.getResidents().get(type).remove(organism);
        } finally {
            location.getLock().unlock();
        }
    }


    public void remove(UUID idOrganism, Location location) {
        location.getLock().lock();

        try {
            location.getResidents().values().forEach(organisms ->
                    organisms.removeIf(organism -> organism.getUuid().equals(idOrganism))
            );
        } finally {
            location.getLock().unlock();
        }
    }
}
