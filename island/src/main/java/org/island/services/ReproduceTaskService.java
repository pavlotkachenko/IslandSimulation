package org.island.services;

import org.island.entity.Organism;
import org.island.location.Location;

public class ReproduceTaskService extends TaskService {

    public ReproduceTaskService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
        organism.reproduce(location);
    }
}
