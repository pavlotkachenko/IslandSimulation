package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

public interface MatingService {
    void findMate(OrganismDTO organismDTO, Island island);

    void findMate(OrganismDTO organism, Location location);
}
