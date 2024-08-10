package org.island.services;


import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;
import org.island.util.Randomizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;


public class AnimalMovementService implements MovementService {

    @Override
    public void move(OrganismDTO organism, Island island) {
        if (organism != null) {
            int speed = organism.getSpeed();
            if (speed > 0) {
                Location currentLocation = findCurrentLocation(organism, island);
                if (currentLocation != null) {
                    move(organism, currentLocation);
                }
            }
        }
    }

    private Location findCurrentLocation(OrganismDTO organism, Island island) {
        for (Location[] row : island.getGRID()) {
            for (Location location : row) {
                if (location.getResidents().getOrDefault(organism.getType(), new HashSet<>()).contains(organism)) {
                    return location;
                }
            }
        }
        return null;
    }

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination) {
        return destination
                .getDirections()
                .stream()
                .filter(location -> !visitedLocations.contains(location))
                .collect(Collectors.toList());
    }

    private Location findDestinationLocation(int countOfSteps, Location location, OrganismDTO organism) {
        Set<Location> visitedLocations = new HashSet<>();
        Location destination = location;

        while (countOfSteps > 0) {
            visitedLocations.add(destination);
            List<Location> directions = getAvailableDirections(visitedLocations, destination);
            int countOfDirections = directions.size();

            if (countOfDirections > 0) {
                int selectedDirection = Randomizer.random(0, countOfDirections);
                destination = directions.get(selectedDirection);
            }
            countOfSteps--;
        }
        return destination;
    }

    private void move(OrganismDTO animal, Location currentLocation) {
        int speed = animal.getSpeed();
        int countOfSteps = Randomizer.random(speed);
        Location destination = findDestinationLocation(countOfSteps, currentLocation, animal);

        if (moveTo(animal, destination)) {
            remove(animal, currentLocation);
        }
    }

    private boolean moveTo(OrganismDTO animal, Location destination) {
        Lock lock = destination.getLock();
        lock.lock();
        try {
            String type = animal.getType();
            HashMap<String, Set<OrganismDTO>> residents = destination.getResidents();
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
            HashMap<String, Set<OrganismDTO>> residents = location.getResidents();
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
