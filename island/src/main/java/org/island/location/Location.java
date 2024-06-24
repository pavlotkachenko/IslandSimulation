package org.island.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.island.animals.Animal;
import org.island.plants.Plant;
import org.island.repo.maps.Residents;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
06/15/2024:
Location Class: Represents a single cell on the island grid,
encapsulating vegetation (same as plant) and animal information.

@Data: Lombok annotation that automatically generates getters, setters, equals(), hashCode(),
and toString() methods for all fields in the class.

@AllArgsConstructor: Lombok annotation that automatically generates constructor with all
 arguments based on all fields in the class.
 */

@Data
@AllArgsConstructor
public class Location {
    private final int row;
    private final int column;

    @Getter
    private List<Location> directions;

    @Setter
    private final Residents residents;

    @Getter
    private final Lock lock;

    public Location(int row, int column){
        this.row = row;
        this.column = column;
        this.residents = new Residents();
        this.lock = new ReentrantLock(true);
    }

    public void setDirection(List<Location> directions){
        this.directions = directions;
    }

    @Override
    public String toString(){
        return "Location" + "[" + row + "]" + "[" + column + "]";
    }

    private Plant plant;
    private Animal animal;
}
