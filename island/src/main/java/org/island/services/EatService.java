package org.island.services;

import org.island.entity.Organism;
import org.island.location.Location;


//TODO Переписать
public class EatService extends TaskService {

    public EatService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
    }
}
