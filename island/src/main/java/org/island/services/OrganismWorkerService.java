package org.island.services;

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


//TODO Переписать
public class OrganismWorkerService implements Runnable {
    private final Organism organism;
    private final Island island;
    private final Queue<org.island.services.TaskService> tasks = new ConcurrentLinkedQueue<>();
    private final Queue<org.island.services.TaskService> hungryTasks = new ConcurrentLinkedQueue<>();
    private final CountDownLatch latch;

    public OrganismWorkerService(Organism organism, Island island, CountDownLatch latch) {
        this.organism = organism;
        this.island = island;
        this.latch = latch;
    }

    @Override
    public void run() {
        Location[][] grid = island.getGRID();
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
                    org.island.services.TaskService task = chooseAction(organism, location, residents);
                    tasks.add(task);
                    org.island.services.TaskService hungry = new HungryService(organism, location);
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
                case WELL_FED -> new ReproductionService(organism, location);
                case ALL_RIGHT -> new org.island.services.MoveTaskService(organism, location);
                case HUNGRY -> {
                    Ration myRation = organism.getRation();
                    boolean haveFoodHere = animal.findSomeFood(myRation, residents);
                    yield haveFoodHere ? new EatService(organism, location) : new org.island.services.MoveTaskService(organism, location);
                }
                case DEATH -> new KillService(organism, location);
            };
        } else {
            task = new ReproductionService(organism, location);
        }
        return task;
    }

}
