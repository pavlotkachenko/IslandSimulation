package org.island.services;


import org.island.entity.OrganismDTO;
import org.island.model.Island;
import org.island.model.Location;
import org.island.util.Randomizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO Переписать
public class AnimalMovementService implements MovementService {
    @Override
    public void move(OrganismDTO organism, Island island) {
        if (organism instanceof OrganismDTO) {
            OrganismDTO animal = (OrganismDTO) organism;
            int speed = animal.getSpeed();

            if (speed > 0) {
                move(animal, );
            }
        }
    }

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination) {
        return destination
                .getDirections()
                .stream()
                .filter(location -> !visitedLocations.contains(location))
                .collect(Collectors.toList());
    }

    private Location findDestinationLocation(int countOfSteps, Location location, OrganismDTO animal) {
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

    public void move(OrganismDTO animal, Location currentLocation) {
        int speed = animal.getSpeed();
        int countOfSteps = Randomizer.random(speed);

        Location destination = findDestinationLocation(countOfSteps, currentLocation, animal);

        if (moveTo(animal, destination)) {
            remove(animal, currentLocation);
        }
    }

    private boolean moveTo(OrganismDTO animal, Location destination) {
        destination.getLock().lock();

        try {
            String type = animal.getType();
            HashMap<String, Set<OrganismDTO>> residents = destination.getResidents();
            Set<OrganismDTO> sameKindOrganisms = residents.get(type);
            int countOfSameKindOrganisms = sameKindOrganisms.size();
            int maxCount = animal.getMaxPopulation();
            if (countOfSameKindOrganisms < maxCount) {
                return sameKindOrganisms.add(animal);
            } else return false;
        } finally {
            destination.getLock().unlock();
        }
    }

    private void remove(OrganismDTO animal, Location location) {
        location.getLock().lock();

        try {
            HashMap<String, Set<OrganismDTO>>  residents = location.getResidents();
            String type = animal.getType();
            Set<OrganismDTO> sameKindOrganisms = residents.get(type);
            if (organismStillAvailable(animal, location)) {
                sameKindOrganisms.remove(animal);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private boolean organismStillAvailable(OrganismDTO animal, Location location) {
        HashMap<String, Set<OrganismDTO>>  residents = location.getResidents();
        String type = animal.getType();
        Set<OrganismDTO> organismStillAvailable = residents.get(type);
        return organismStillAvailable.contains(animal);
    }


}
