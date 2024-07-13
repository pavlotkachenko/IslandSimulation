package org.island.location;

import lombok.*;
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
@RequiredArgsConstructor
@ToString
public class Location {
    private final int row;
    private final int column;
    private List<Location> directions;
    private Residents residents;
    private Lock lock;
}
