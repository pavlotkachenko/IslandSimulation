package org.island.simulation;

import org.island.model.Island;

public interface SimulationEngine {
    void populateIsland(Island island);
    void runCycle();
    void run();
}