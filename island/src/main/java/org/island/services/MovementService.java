package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;

public interface MovementService {
    void move(OrganismDTO organism, Island island);
}
