package org.island.entity;

import lombok.Data;
import org.island.abilities.Reproducible;
import org.island.location.Location;
import org.island.repo.Limit;
import org.island.repo.OrganismInfo;
import org.island.repo.factory.EntityFactory;
import org.island.repo.maps.Ration;
import org.island.repo.maps.Residents;
import org.island.util.Fullness;

import java.util.Objects;
import java.util.Set;

@Data
public abstract class Organism implements Reproducible {
    private final OrganismInfo info;
    private final Limit limit;

    public Organism(OrganismInfo info, Limit limit){
        this.info = info;
        this.limit = limit;
    }

    public String getType(){
        return info.getType();
    }

    public int getGroupId(){
        return info.getGroupId();
    }

    public boolean isAlive(){
        return info.isAlive();
    }

    public double getCurrentWeight(){
        return info.getCurrentWeight();
    }

    public void setCurrentWeight(double currentWeight){
        info.setCurrentWeight(currentWeight);
    }

    public String getIcon(){
        return info.getIcon();
    }

    public Fullness getFullness(){
        return info.getFullness();
    }

    public void setFullness(Fullness fullness){
        info.setFullness(fullness);
    }

    public Ration getRation(){
        return info.getRation();
    }

    public double getMaxWeight(){
        return limit.getMaxWeight();
    }

    public int getMaxPopulation(){
        return limit.getMaxPopulation();
    }

    public int getSpeed(){
        return limit.getSpeed();
    }

    public void getDead(){
        info.setAlive(false);
    }

    @Override
    public void reproduce(Location location) {
        location.getLock().lock();
        try {
            Residents residents = location.getResidents();
            String type = getType();
            Set<Organism> sameOrganisms = residents.get(type);
            int maxLimit = getMaxPopulation();

            if(sameOrganisms.contains(this) && sameOrganisms.size() >= 2 && sameOrganisms.size() < maxLimit){
                Organism organism = EntityFactory.getFactory().create(type);
                sameOrganisms.add(organism);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void killOrganism(Location location){
        location.getLock().lock();
        try{
            String type = getType();
            location.getResidents().get(type).remove(this);
        } finally {
            location.getLock().unlock();
        }
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organism organism = (Organism) o;
        return Objects.equals(info, organism.info);
    }

    @Override
    public int hashCode(){
        return Objects.hash(info);
    }
}
