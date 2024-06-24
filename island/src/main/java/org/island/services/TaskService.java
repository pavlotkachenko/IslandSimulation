package org.island.services;

import lombok.RequiredArgsConstructor;
import org.island.entity.Organism;
import org.island.location.Location;

@RequiredArgsConstructor
public abstract class TaskService implements Runnable {
    protected final Organism organism;
    protected final Location location;

    public abstract void run();
}
