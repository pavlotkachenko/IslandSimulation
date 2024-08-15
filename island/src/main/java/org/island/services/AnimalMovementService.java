package org.island.services;

import org.island.entity.OrganismDTO;
import org.island.model.Location;
import org.island.util.Randomizer;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class AnimalMovementService implements MovementService {
    Logger log = Logger.getLogger(this.getClass().getName());

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination) {
        return destination.getDirections().stream()
                .filter(location -> !visitedLocations.contains(location))
                .toList();
    }

    private Location findDestinationLocation(int countOfSteps, Location location) {
        Set<Location> visitedLocations = new HashSet<>();
        Location destination = location;

        while (countOfSteps > 0) {
            visitedLocations.add(destination);
            List<Location> directions = getAvailableDirections(visitedLocations, destination);
            int countOfDirections = directions.size();

            if (countOfDirections > 0) {
                int selectedDirection = Randomizer.random(0, countOfDirections);
                if (selectedDirection < directions.size()) {
                    destination = directions.get(selectedDirection);
                    countOfSteps--;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
            countOfSteps--;
        }


        return destination;
    }

    public void move(OrganismDTO animal, Location currentLocation) {
        int speed = animal.getSpeed();
        int countOfSteps = Randomizer.random(speed);
        Location destination = findDestinationLocation(countOfSteps, currentLocation);

        if (moveTo(animal, destination)) {
            remove(animal, currentLocation);
        }
    }

    private boolean moveTo(OrganismDTO animal, Location destination) {
        Lock lock = destination.getLock();
        lock.lock();
        try {
            String type = animal.getType();
            Map<String, Set<OrganismDTO>> residents = destination.getResidents();
            Set<OrganismDTO> sameKindOrganisms = residents.computeIfAbsent(type, k -> new HashSet<>());

            if (sameKindOrganisms.size() < animal.getMaxPopulation()) {
                sameKindOrganisms.add(animal);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void remove(OrganismDTO animal, Location location) {
        Lock lock = location.getLock();
        lock.lock();
        try {
            Map<String, Set<OrganismDTO>> residents = location.getResidents();
            String type = animal.getType();
            Set<OrganismDTO> sameKindOrganisms = residents.get(type);

            if (sameKindOrganisms != null && sameKindOrganisms.contains(animal)) {
                sameKindOrganisms.remove(animal);
            }
        } finally {
            lock.unlock();
        }
    }
}