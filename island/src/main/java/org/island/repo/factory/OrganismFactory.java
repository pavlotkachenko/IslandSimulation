package org.island.repo.factory;

import org.island.entity.Organism;
import org.island.repo.Limit;
import org.island.repo.maps.Ration;
import org.island.settings.Config;
import org.island.util.Fullness;

import java.io.ObjectInputFilter;

public abstract class OrganismFactory {
    protected int groupId;
    protected String type;
    protected Config config = Config.getConfig();
    protected boolean isAlive = true;
    protected double currentWeight;
    protected Fullness fullness = Fullness.ALL_RIGHT;
    protected Ration ration;
    protected Limit limit;
    protected String icon;

    public abstract Organism create(String type);
}
