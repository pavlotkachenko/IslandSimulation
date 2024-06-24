package org.island.repo.factory.animal_factories.grasseaters;

import org.island.animals.grasseaters.Boar;
import org.island.animals.grasseaters.Buffalo;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.repo.OrganismInfo;
import org.island.repo.factory.OrganismFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class BuffaloFactory extends OrganismFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);

    public BuffaloFactory(){
        groupId = Group.BUFFALO.getGroupId();
        type = Group.BUFFALO.getType();
        limit = config.getLimit(type);
        currentWeight = limit.getMaxWeight() * config.getStartWeightFactor();
        ration = config.getRation(type);
        icon = config.getIcon(type);
    }

    @Override
    public Organism create(String type){

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

        return new Buffalo(organismInfo, limit);
    }
}
