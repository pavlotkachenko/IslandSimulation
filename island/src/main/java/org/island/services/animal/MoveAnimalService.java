package org.island.services.animal;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.location.Location;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoveAnimalService {

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination) {
        return destination
                .getDirections()
                .stream()
                .filter(location -> !visitedLocations.contains(location))
                .collect(Collectors.toList());
    }

    private Location findDestinationLocation(int countOfSteps, Location location, Animal animal) {
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

    public void move(Animal animal, Location currentLocation) {
        int speed = animal.getSpeed();
        int countOfSteps = Randomizer.random(speed);

        Location destination = findDestinationLocation(countOfSteps, currentLocation, animal);

        if (moveTo(animal, destination)) {
            remove(animal, currentLocation);
        }
    }

    private boolean moveTo(Animal animal, Location destination) {
        destination.getLock().lock();

        try {
            String type = animal.getType();
            Residents residents = destination.getResidents();
            Set<Organism> sameKindOrganisms = residents.get(type);
            int countOfSameKindOrganisms = sameKindOrganisms.size();
            int maxCount = animal.getMaxPopulation();
            if (countOfSameKindOrganisms < maxCount) {
                return sameKindOrganisms.add(animal);
            } else return false;
        } finally {
            destination.getLock().unlock();
        }
    }

    private void remove(Animal animal, Location location) {
        location.getLock().lock();

        try {
            Residents residents = location.getResidents();
            String type = animal.getType();
            Set<Organism> sameKindOrganisms = residents.get(type);
            if (organismStillAvailable(animal, location)) {
                sameKindOrganisms.remove(animal);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private boolean organismStillAvailable(Animal animal, Location location) {
        Residents residents = location.getResidents();
        String type = animal.getType();
        Set<Organism> organismStillAvailable = residents.get(type);
        return organismStillAvailable.contains(animal);
    }


}
