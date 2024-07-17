package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;

public interface FeedingService {
    void eat(OrganismDTO organismDTO, Island location);
}
