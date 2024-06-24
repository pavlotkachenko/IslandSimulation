package org.island.animals;

import org.island.abilities.Eatable;
import org.island.repo.Limit;
import org.island.repo.OrganismInfo;

public abstract class GrassEater extends Animal {
   public GrassEater(OrganismInfo info, Limit limit){
       super(info, limit);
   }
}
