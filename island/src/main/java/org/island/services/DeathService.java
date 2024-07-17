package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Island;

public interface DeathService {
    void die(OrganismDTO organism, Island location);
}
