package org.island.repo.factory.animal_factories.predators;

import org.island.animals.predators.Eagle;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.repo.OrganismInfo;
import org.island.repo.factory.OrganismFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class EagleFactory extends OrganismFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);

    public EagleFactory(){
        groupId = Group.EAGLE.getGroupId();
        type = Group.EAGLE.getType();
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

        return new Eagle(organismInfo, limit);
    }
}
