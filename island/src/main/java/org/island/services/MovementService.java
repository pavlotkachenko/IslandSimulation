package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

public interface MovementService {
    void move(OrganismDTO organism, Location location);
}
