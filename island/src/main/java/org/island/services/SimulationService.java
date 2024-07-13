package org.island.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.island.entity.Organism;
import org.island.location.Island;
import org.island.repo.OrganismFactory;
import org.island.entity.Group;
import org.island.view.View;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class SimulationService extends Thread {
    private final View view;
    private final Island island;
    private final int period;
    private final PopulateIslandService populateIslandService;

    @Override
    @SneakyThrows
    public void run() {

        populateIslandService.populateIsland(island);
        view.showMap();
        view.showStatistics();
        ScheduledExecutorService mainPool = Executors.newScheduledThreadPool(1);
        Map<String, Organism> prototypes = createPrototypes();
        CountDownLatch latch = new CountDownLatch(prototypes.size());

        List<OrganismWorkerService> workers = prototypes
                .values()
                .stream()
                .map(organism -> new OrganismWorkerService(organism, island, latch))
                .toList();

        mainPool.scheduleWithFixedDelay(() -> {
            ExecutorService servicePool = Executors.newFixedThreadPool(4);
            workers.forEach(servicePool::submit);
            servicePool.shutdown();


            try {
                if (servicePool.awaitTermination(3, TimeUnit.SECONDS)) {
                    view.showMap();
                    view.showStatistics();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }, period, period, TimeUnit.MILLISECONDS);
    }

    private Map<String, Organism> createPrototypes() {
        Map<String, Organism> result = new LinkedHashMap<>();
        for (Group group : Group.values()) {
            Organism organism = OrganismFactory.createOrganism(group);
            result.put(group.getType(), organism);
        }
        return Collections.unmodifiableMap(result);
    }
}
