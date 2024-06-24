package org.island.services;

import org.island.entity.Organism;
import org.island.location.Location;

public class KillTaskService extends TaskService {

    public KillTaskService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
        if (organism.isAlive()) {
            organism.getDead();
        }
    }
}
