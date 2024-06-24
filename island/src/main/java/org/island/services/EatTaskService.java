package org.island.services;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.location.Location;

public class EatTaskService extends TaskService {

    public EatTaskService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
        if (organism instanceof Animal) {
            Animal animal = (Animal) organism;
            animal.eat(location);
        }
    }
}
