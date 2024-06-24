package org.island.plants;


import org.island.entity.Organism;
import org.island.repo.Limit;
import org.island.repo.OrganismInfo;

public abstract class Plant extends Organism {

    public Plant(OrganismInfo info, Limit limit){
        super(info, limit);
    }
}
