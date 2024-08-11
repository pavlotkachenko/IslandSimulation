package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;

public interface FeedingService {
    void eat(OrganismDTO organismDTO, Location location);
}
