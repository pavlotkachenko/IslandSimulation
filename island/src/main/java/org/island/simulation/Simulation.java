package org.island.simulation;

import lombok.RequiredArgsConstructor;
import org.island.entity.EOrganisms;
import org.island.model.Island;
import org.island.services.DeathService;
import org.island.services.FeedingService;
import org.island.services.MatingService;
import org.island.services.MovementService;
import org.island.view.View;

@RequiredArgsConstructor
public class Simulation {
    private final View view;
    private final Island island;
    private final int period;
    private final MovementService movementService;
    private final MatingService matingService;
    private final FeedingService feedingService;
    private final DeathService deathService;

    public void populateIsland(Island island) {

    }
    public void runCycle(){

    }

}
