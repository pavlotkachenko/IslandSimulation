package org.island.services;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.location.Location;

public class MoveTaskService extends TaskService {

    public MoveTaskService(Organism organism, Location location) {
        super(organism, location);
    }

    @Override
    public void run() {
        if (organism instanceof Animal) {
            Animal animal = (Animal) organism;
            int speed = animal.getSpeed();

            if (speed > 0) {
                animal.move(location);
            }
        }
    }
}
