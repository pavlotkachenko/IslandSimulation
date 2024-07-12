package org.island.animals;

import org.island.abilities.Eatable;
import org.island.abilities.Movable;
import org.island.entity.Organism;
import org.island.location.Location;
import org.island.repo.Limit;
import org.island.entity.OrganismInfo;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;
import org.island.services.animal.EatService;
import org.island.services.animal.MoveAnimalService;


public abstract class Animal extends Organism implements Eatable, Movable {

    private final MoveAnimalService moveAnimalService = new MoveAnimalService();
    private final EatService eatService = new EatService();


    public Animal(OrganismInfo info, Limit limit) {
        super(info, limit);
    }

    public void move(Location currentLocation) {
        moveAnimalService.move(this, currentLocation);
    }


    public void eat(Location location) {
        eatService.eat(this, location);
    }

    public boolean findSomeFood(Ration ration, Residents residents) {
        return eatService.findSomeFood(ration, residents);
    }
}
