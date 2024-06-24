package org.island.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.island.abilities.Eatable;
import org.island.abilities.Movable;
import org.island.entity.Organism;
import org.island.exceptions.OrganismNotFound;
import org.island.location.Location;
import org.island.repo.Limit;
import org.island.repo.OrganismInfo;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Animal extends Organism implements Eatable, Movable {

    public Animal(OrganismInfo info, Limit limit) {
        super(info, limit);
    }


    public void move(Location currentLocation) {
        int speed = getSpeed();
        int countOfSteps = Randomizer.random(speed);

        Location destination = findDestinationLocation(countOfSteps, currentLocation);

        if (moveTo(destination)) {
            remove(currentLocation);
        }
    }


    public void eat(Location location) {
        location.getLock().lock();

        try {
            Ration ration = getRation();
            double currentWeight = getCurrentWeight();
            double deltaWeight = getMaxWeight() - currentWeight;
            Residents residents = location.getResidents();
            Organism prey = getTarget(ration, residents);
            String preyType = prey.getType();

            if (prey.isAlive()) {
                int chanceToKill = ration.get(preyType);
                int predatorTry = Randomizer.random(0, 100);

                if (predatorTry <= chanceToKill) {
                    prey.getDead();
                } else return;
            }

            double currentTargetWeight = prey.getCurrentWeight();
            double predatorFinalWeight;

            if (deltaWeight > currentTargetWeight) {
                predatorFinalWeight = currentWeight + currentTargetWeight;
                prey.setCurrentWeight(0);
            } else {
                predatorFinalWeight = currentWeight + deltaWeight;
                prey.setCurrentWeight(currentTargetWeight - deltaWeight);
            }

            setCurrentWeight(predatorFinalWeight);
        } finally {
            location.getLock().unlock();
        }
    }

    public boolean findSomeFood(Ration ration, Residents residents) {
        return residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .map(Map.Entry::getKey)
                .anyMatch(ration::containsKey);
    }

    private Organism getTarget(Ration ration, Residents residents) {
        Set<Organism> preys = residents
                .entrySet()
                .stream()
                .filter(resident -> resident.getValue().size() > 0)
                .filter(resident -> ration.containsKey(resident.getKey()))
                .max(Comparator.comparingInt(resident -> ration.get(resident.getKey())))
                .orElseThrow(() -> new OrganismNotFound("target / prey not found"))
                .getValue();

        return preys
                .stream()
                .skip(Randomizer.random(0, preys.size()))
                .iterator()
                .next();

    }

    private List<Location> getAvailableDirections(Set<Location> visitedLocations, Location destination){
        return destination
                .getDirections()
                .stream()
                .filter(location -> !visitedLocations.contains(location))
                .collect(Collectors.toList());
    }

    private Location findDestinationLocation(int countOfSteps, Location location){
        Set<Location> visitedLocations = new HashSet<>();
        Location destination = location;

        while(countOfSteps > 0){
            visitedLocations.add(destination);
            List<Location> directions = getAvailableDirections(visitedLocations, destination);
            int countOfDirections = directions.size();

            if (countOfDirections > 0){
                int selectedDirection = Randomizer.random(0, countOfDirections);
                destination = directions.get(selectedDirection);
            }
            countOfSteps--;
        }
        return destination;
    }

    private boolean moveTo(Location destination){
        destination.getLock().lock();

        try{
            String type = getType();
            Residents residents = destination.getResidents();
            Set<Organism> sameKindOrganisms = residents.get(type);
            int countOfSameKindOrganisms = sameKindOrganisms.size();
            int maxCount = getMaxPopulation();
            if (countOfSameKindOrganisms < maxCount){
                return sameKindOrganisms.add(this);
            } else return false;
        } finally {
            destination.getLock().unlock();
        }
    }

    private void remove(Location location){
        location.getLock().lock();

        try{
            Residents residents = location.getResidents();
            String type = getType();
            Set<Organism> sameKindOrganisms = residents.get(type);
            if (organismStillAvailable(location)){
                sameKindOrganisms.remove(this);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private boolean organismStillAvailable(Location location){
        Residents residents = location.getResidents();
        String type = getType();
        Set<Organism> organismStillAvailable = residents.get(type);
        return organismStillAvailable.contains(this);
    }



}
