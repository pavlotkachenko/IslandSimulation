package org.island.repo.factory.animal_factories.predators;

import org.island.animals.predators.Wolf;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.repo.OrganismInfo;
import org.island.repo.factory.OrganismFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class WolfFactory extends OrganismFactory {
    private final static AtomicInteger ID = new AtomicInteger(0);

    public WolfFactory() {
        groupId = Group.WOLF.getGroupId();
        type = Group.WOLF.getType();
        limit = config.getLimit(type);
        currentWeight = limit.getMaxWeight() * config.getStartWeightFactor();
        ration = config.getRation(type);
        icon = config.getIcon(type);
    }

    @Override
    public Organism create(String type) {
        int organismId = ID.getAndIncrement();

        OrganismInfo organismInfo = OrganismInfo.builder()
                .type(type)
                .groupId(groupId)
                .organismId(organismId)
                .isAlive(isAlive)
                .currentWeight(currentWeight)
                .icon(icon)
                .fullness(fullness)
                .ration(ration)
                .build();

        return new Wolf(organismInfo, limit);

    }
}
