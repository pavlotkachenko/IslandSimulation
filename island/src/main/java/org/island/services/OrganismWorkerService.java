package org.island.services;

import org.island.animals.Animal;
import org.island.entity.Organism;
import org.island.exceptions.OrganismOperationFail;
import org.island.location.Island;
import org.island.location.Location;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;

import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class OrganismWorkerService implements Runnable {
    private final Organism organism;
    private final Island island;
    private final Queue<TaskService> tasks = new ConcurrentLinkedQueue<>();
    private final Queue<TaskService> hungryTasks = new ConcurrentLinkedQueue<>();
    private final CountDownLatch latch;

    public OrganismWorkerService(Organism organism, Island island, CountDownLatch latch) {
        this.organism = organism;
        this.island = island;
        this.latch = latch;
    }

    @Override
    public void run() {
        Location[][] grid = island.getIslandGrid();
        for (Location[] row : grid) {
            for (Location location : row) {
                try {
                    processOneLocation(location);
                } catch (Exception e) {
                    throw new OrganismOperationFail("Problem operation with creature - " + this.organism, e);
                }
            }
        }
    }

    private void processOneLocation(Location location) {
        String type = organism.getType();
        Residents residents = location.getResidents();
        Set<Organism> organisms = residents.get(type);

        if (Objects.nonNull(organisms)) {
            location.getLock().lock();
            try {
                for (Organism organism : organisms) {
                    TaskService task = chooseAction(organism, location, residents);
                    tasks.add(task);
                    TaskService hungry = new HungryTaskService(organism, location);
                    hungryTasks.add(hungry);
                }
            } finally {
                location.getLock().unlock();
            }
            tasks.forEach(TaskService::run);
            tasks.clear();
            latch.countDown();

            hungryTasks.forEach(TaskService::run);
            hungryTasks.clear();
            latch.countDown();
        }
    }

    private TaskService chooseAction(Organism organism, Location location, Residents residents) {
        TaskService task = null;

        if (organism instanceof Animal) {
            Animal animal = (Animal) organism;
            task = switch (organism.getFullness()) {
                case WELL_FED -> new ReproduceTaskService(organism, location);
                case ALL_RIGHT -> new MoveTaskService(organism, location);
                case HUNGRY -> {
                    Ration myRation = organism.getRation();
                    boolean haveFoodHere = animal.findSomeFood(myRation, residents);
                    yield haveFoodHere ? new EatTaskService(organism, location) : new MoveTaskService(organism, location);
                }
                case WILL_BE_FINE -> new KillTaskService(organism, location);
            };
        } else {
            task = new ReproduceTaskService(organism, location);
        }
        return task;
    }

}
