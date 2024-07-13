package org.island.location;

import lombok.Getter;
import org.island.settings.Config;
/*
06/15/2024:
Island Class: Manages the entire island grid using a 2-dimensional array of Location objects.

@Data: Lombok annotation that automatically generates getters, setters, equals(), hashCode(),
and toString() methods for all fields in the class.
 */

@Getter
public class Island {
    private final Location[][] GRID;

    public Island(Config config) {
        this.GRID = new Location[config.getRows()] [config.getColumns()];
    }

}
