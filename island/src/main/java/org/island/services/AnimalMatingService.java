package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

import java.util.Map;
import java.util.Set;

public class AnimalMatingService implements MatingService {

    @Override
    public void findMate(OrganismDTO organism, Location location) {

        location.getLock().lock();
        try{
            Map<String, Set<OrganismDTO>> residents = location.getResidents();
            String type = organism.getType();
            Set<OrganismDTO> sameOrganisms = residents.get(type);
            int maxLimit = organism.getMaxPopulation();

            if(sameOrganisms.contains(this) && sameOrganisms.size() >= 2 && sameOrganisms.size() < maxLimit){
                OrganismDTO organismNew = OrganismDTO.builder().build();
                sameOrganisms.add(organismNew);
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
