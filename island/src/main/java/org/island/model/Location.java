package org.island.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.island.entity.OrganismDTO;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
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
        this.directions = new ArrayList<>();
    }

    public void addDirection(Location direction) {
        this.directions.add(direction);
    }

    public void addResident(OrganismDTO resident) {
        lock.lock();
        try {
            Set<OrganismDTO> organismSet = residents.computeIfAbsent(resident.getType(), k -> new HashSet<>());
            organismSet.add(resident);
        } finally {
            lock.unlock();
        }
    }

    public void removeResident(OrganismDTO resident) {
        lock.lock();
        try {
            Set<OrganismDTO> organismSet = residents.get(resident.getType());
            if (organismSet != null) {
                organismSet.remove(resident);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull(String type, int maxPopulation) {
        lock.lock();
        try {
            Set<OrganismDTO> organismSet = residents.get(type);
            return organismSet != null && organismSet.size() >= maxPopulation;
        } finally {
            lock.unlock();
        }
    }

    public int getResidentCount(String type) {
        lock.lock();
        try {
            Set<OrganismDTO> organismSet = residents.get(type);
            return organismSet != null ? organismSet.size() : 0;
        } finally {
            lock.unlock();
        }
    }

    public Set<OrganismDTO> getResidentsOfType(String type) {
        lock.lock();
        try {
            return new HashSet<>(residents.getOrDefault(type, Collections.emptySet()));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "row=" + row +
                ", column=" + column +
                ", directions=" + summarizeDirections() +
                ", residents=" + summarizeResidents() +
                '}';
    }

    private String summarizeDirections() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Location direction : directions) {
            sb.append("Location{row=").append(direction.getRow()).append(", column=").append(direction.getColumn()).append("}, ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private String summarizeResidents() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Set<OrganismDTO>> entry : residents.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue().size()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return row == location.row && column == location.column && Objects.equals(directions, location.directions) && Objects.equals(residents, location.residents) && Objects.equals(lock, location.lock);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + directions.size();
        result = 31 * result + residents.hashCode();
        return result;
    }
}