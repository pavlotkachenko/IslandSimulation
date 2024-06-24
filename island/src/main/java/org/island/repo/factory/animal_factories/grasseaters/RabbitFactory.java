package org.island.repo.factory.animal_factories.grasseaters;

import org.island.animals.grasseaters.Rabbit;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.repo.OrganismInfo;
import org.island.repo.factory.OrganismFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class RabbitFactory extends OrganismFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);

    public RabbitFactory() {
        groupId = Group.RABBIT.getGroupId();
        type = Group.RABBIT.getType();
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

        return new Rabbit(organismInfo, limit);
    }
}
