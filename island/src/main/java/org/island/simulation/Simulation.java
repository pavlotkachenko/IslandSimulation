package org.island.simulation;

import lombok.RequiredArgsConstructor;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class Simulation implements SimulationEngine{
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
        Map<String, OrganismDTO> prototypes = organismFactory.getORGANISMS();

        for (Location[] locations : grid) {
            for (Location location : locations) {
                Map<String, Set<OrganismDTO>> residents = location.getResidents();

                for (OrganismDTO organism : prototypes.values()) {
                    String type = organism.getType();
                    int maxCount = organism.getMaxPopulation();
                    int count = Randomizer.random(maxCount / 2, maxCount);
                    Set<OrganismDTO> organismSet = new HashSet<>();

                    for (int i = 0; i < count; i++) {
                        organismSet.add(organismFactory.getORGANISMS().get(type));
                    }
                    residents.put(type, organismSet);
                }
            }
        }
    }
    //TODO Сервисы дописать
    public void runCycle() {
        Location[][] grid = island.getGRID();

        for (int cycle = 0; cycle < period; cycle++) {
            view.showMessage("Starting cycle " + (cycle + 1));

            // Process each location on the island
            for (Location[] locations : grid) {
                for (Location location : locations) {
                    Map<String, Set<OrganismDTO>> residents = location.getResidents();

/*                    // Move organisms
                    movementService.move(location);

*//*                    // Feed organisms
                    feedingService.feed(location);

                    // Mate organisms
                    matingService.mate(location);

                    // Handle deaths
                    deathService.handleDeaths(location);*/
                }
            }

            // Update view after each cycle
            view.showStatistics();
            view.showMap();

            view.showMessage("Cycle " + (cycle + 1) + " completed");
        }

        view.showMessage("Simulation completed");
    }

    public void run() {
        view.showMessage("Initializing island population...");
        populateIsland(island);

        view.showMessage("Starting simulation...");
//        runCycle();
    }
}