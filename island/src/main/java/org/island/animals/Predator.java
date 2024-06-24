package org.island.animals;

import org.island.repo.Limit;
import org.island.repo.OrganismInfo;

public abstract class Predator extends Animal {

    public Predator(OrganismInfo info, Limit limit){
        super(info, limit);
    }

}

