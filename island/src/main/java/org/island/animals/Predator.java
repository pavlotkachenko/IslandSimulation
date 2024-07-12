package org.island.animals;

import org.island.repo.Limit;
import org.island.entity.OrganismInfo;

public abstract class Predator extends Animal {

    public Predator(OrganismInfo info, Limit limit){
        super(info, limit);
    }
}

