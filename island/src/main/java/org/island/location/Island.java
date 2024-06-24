package org.island.location;

import lombok.Data;
/*
06/15/2024:
Island Class: Manages the entire island grid using a 2-dimensional array of Location objects.

@Data: Lombok annotation that automatically generates getters, setters, equals(), hashCode(),
and toString() methods for all fields in the class.
 */

@Data
public class Island {
    private Location[][] grid;

    public Island(int numRows, int numCols){
        this.grid = new Location[numRows] [numCols];
    }

    public Location[][] getIslandGrid(){
        return grid;
    }

}
