package org.island.services.animal;

import org.island.entity.Organism;
import org.island.location.Location;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
public class MovementService {

    public Location findDestinationLocation(int countOfSteps, Location location) {
        Set<Location> visitedLocations = new HashSet<>();
        Location destination = location;

        while (countOfSteps > 0) {
            visitedLocations.add(destination);
            List<Location> directions = getAvailableDirections(visitedLocations, destination);

            if (!directions.isEmpty()) {
                int selectedDirection = Randomizer.random(0, directions.size());
                destination = directions.get(selectedDirection);
            }
            countOfSteps--;
        }
        return destination;
    }

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination) {
        return destination.getDirections().stream()
                .filter(location -> !visitedLocations.contains(location))
                .collect(Collectors.toList());
    }

    public boolean moveTo(Location destination, String type, int maxCount, Organism organism) {
        destination.getLock().lock();
        try {
            Residents residents = destination.getResidents();
            Set<Organism> sameKindOrganisms = residents.get(type);

            if (sameKindOrganisms.size() < maxCount) {
                return sameKindOrganisms.add(organism);
            } else {
                return false;
            }
        } finally {
            destination.getLock().unlock();
        }
    }

    public void remove(Location location, Organism organism, String type) {
        location.getLock().lock();
        try {
            Residents residents = location.getResidents();
            Set<Organism> sameKindOrganisms = residents.get(type);

            if (sameKindOrganisms.contains(organism)) {
                sameKindOrganisms.remove(organism);
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
