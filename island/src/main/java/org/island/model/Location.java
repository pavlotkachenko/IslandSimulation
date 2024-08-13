package org.island.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.island.entity.OrganismDTO;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@ToString
@EqualsAndHashCode
public class Location {
    private final int row;
    private final int column;
    private List<Location> directions;
    private Map<String, Set<OrganismDTO>> residents;
    private final Lock lock = new ReentrantLock();

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
        this.residents = new HashMap<>();
    }


    public void addResident(OrganismDTO resident) {
        Set<OrganismDTO> organismSet = residents.computeIfAbsent(resident.getType(), k -> new HashSet<>());
        organismSet.add(resident);
    }

}
