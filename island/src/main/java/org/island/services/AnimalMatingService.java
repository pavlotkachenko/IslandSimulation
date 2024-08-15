package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Location;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class AnimalMatingService implements MatingService {

    @Override
    public void findMate(OrganismDTO organism, Location location) {
        location.getLock().lock();
        try {
            Set<OrganismDTO> sameTypeOrganisms = getSameTypeOrganisms(organism, location);

            if (canReproduce(organism, sameTypeOrganisms)) {
                OrganismDTO newOrganism = createOffspring(organism);
                sameTypeOrganisms.add(newOrganism);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private Set<OrganismDTO> getSameTypeOrganisms(OrganismDTO organism, Location location) {
        return location.getResidents().getOrDefault(organism.getType(), Set.of());
    }

    private boolean canReproduce(OrganismDTO organism, Set<OrganismDTO> sameTypeOrganisms) {
        int currentPopulation = sameTypeOrganisms.size();
        return currentPopulation >= 2 && currentPopulation < organism.getMaxPopulation();
    }

    private OrganismDTO createOffspring(OrganismDTO parentOrganism) {
        return OrganismDTO.builder()
                .uuid(UUID.randomUUID())
                .type(parentOrganism.getType())
                .groupId(parentOrganism.getGroupId())
                .isAlive(true)
                .currentWeight(parentOrganism.getCurrentWeight() * 0.5)
                .icon(parentOrganism.getIcon())
                .ration(new HashMap<>(parentOrganism.getRation()))
                .maxWeight(parentOrganism.getMaxWeight())
                .maxPopulation(parentOrganism.getMaxPopulation())
                .speed(parentOrganism.getSpeed())
                .build();
    }

}
