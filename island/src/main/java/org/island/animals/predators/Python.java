package org.island.animals.predators;

import org.island.abilities.Eatable;
import org.island.abilities.Movable;
import org.island.abilities.Reproducible;
import org.island.animals.Predator;
import org.island.repo.Limit;
import org.island.repo.OrganismInfo;

public class Python extends Predator {

    public Python(OrganismInfo info, Limit limit) {
        super(info, limit);
    }
}
