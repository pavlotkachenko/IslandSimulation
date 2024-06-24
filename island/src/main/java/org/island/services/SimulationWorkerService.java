package org.island.services;

import org.island.entity.Organism;
import org.island.entity.Simulation;
import org.island.settings.Config;
import org.island.view.View;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SimulationWorkerService extends Thread {
    private final Simulation simulation;
    private final int period;

    public SimulationWorkerService(Simulation simulation) {
        this.simulation = simulation;
        this.period = Config.getConfig().getPeriod();
    }

    @Override
    public void run() {
        View view = simulation.getView();
        view.showMap();
        view.showStatistics();
        ScheduledExecutorService mainPool = Executors.newScheduledThreadPool(1);
        Map<String, Organism> prototypes = simulation.getEntityFactory().getPrototypes();
        CountDownLatch latch = new CountDownLatch(prototypes.size());

        List<OrganismWorkerService> workers = prototypes
                .values()
                .stream()
                .map(organism -> new OrganismWorkerService(organism, simulation.getIsland(), latch))
                .collect(Collectors.toList());

        mainPool.scheduleWithFixedDelay(() -> {
            ExecutorService servicPool = Executors.newFixedThreadPool(4);
            workers.forEach(servicPool::submit);
            servicPool.shutdown();
            await(view, servicPool);

        }, period, period, TimeUnit.MILLISECONDS);
    }

    private void await(View view, ExecutorService servicePool) {
        try {
            if (servicePool.awaitTermination(3, TimeUnit.SECONDS)) {
                view.showMap();
                view.showStatistics();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
