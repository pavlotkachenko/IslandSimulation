package org.island.simulation;

import lombok.RequiredArgsConstructor;
import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.factory.OrganismFactory;
import org.island.model.Island;
import org.island.model.Location;
import org.island.services.DeathService;
import org.island.services.FeedingService;
import org.island.services.MatingService;
import org.island.services.MovementService;
import org.island.util.Randomizer;
import org.island.view.View;

import java.util.*;

@RequiredArgsConstructor
public class Simulation implements SimulationEngine {
    private final View view;
    private final Island island;
    private final int period;
    private final MovementService movementService;
    private final MatingService matingService;
    private final FeedingService feedingService;
    private final DeathService deathService;
    private final OrganismFactory organismFactory;

    public void populateIsland(Island island) {
        Location[][] grid = island.getGRID();

        for (Location[] locations : grid) {
            for (Location location : locations) {
                Map<String, Set<OrganismDTO>> residents = location.getResidents();

                for (EOrganisms organismType : EOrganisms.values()) {
                    String type = organismType.getType();
                    int maxCount = organismFactory.createOrganism(organismType).getMaxPopulation();
                    int count = Randomizer.random(maxCount / 2, maxCount);
                    Set<OrganismDTO> organismSet = new HashSet<>();

                    for (int i = 0; i < count; i++) {
                        // Создание нового экземпляра для каждого организма
                        organismSet.add(OrganismFactory.createOrganism(organismType));
                    }
                    residents.put(type, organismSet);
                }
            }
        }
    }

    // TODO: Дописать сервисы
    public void runCycle() {
        Location[][] grid = island.getGRID();

        for (int cycle = 0; cycle < period; cycle++) {
            view.showMessage("Starting cycle " + (cycle + 1));

            processLocations(grid);
            updateView();

            view.showMessage("Cycle " + (cycle + 1) + " completed");
        }

        view.showMessage("Simulation completed");
    }

    private void processLocations(Location[][] grid) {
        for (Location[] locations : grid) {
            for (Location location : locations) {
                processLocation(location);
            }
        }
    }

    private void processLocation(Location location) {

        moveOrganisms(location);
//    feedOrganisms(location);
//    handleDeaths(location);
//    mateOrganisms(location);
    }

    private void moveOrganisms(Location location) {
        Map<String, Set<OrganismDTO>> residents = location.getResidents();
        List<String> types = new ArrayList<>(residents.keySet());

        types.forEach(type -> {
            Set<OrganismDTO> organisms = residents.get(type);
            if (organisms != null) {
                organisms.forEach(organism -> movementService.move(organism, location));
            }
        });
    }

// private void feedOrganisms(Location location) {
//     feedingService.eat(location);
// }

// private void mateOrganisms(Location location) {
//     matingService.findMate(location);
// }

// private void handleDeaths(Location location) {
//     deathService.die(location);
// }

    private void updateView() {
        view.showStatistics();
        view.showMap();
    }


    public void run() {
        view.showMessage("Initializing island population...");
        populateIsland(island);

        view.showMessage("Starting simulation...");
         runCycle();
    }
}