package org.island.model;

import lombok.ToString;
import org.island.entity.OrganismDTO;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ToString
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

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public List<Location> getDirections() {
        return this.directions;
    }

    public Map<String, Set<OrganismDTO>> getResidents() {
        return this.residents;
    }

    public Lock getLock() {
        return this.lock;
    }

    public void setDirections(List<Location> directions) {
        this.directions = directions;
    }

    public void setResidents(Map<String, Set<OrganismDTO>> residents) {
        this.residents = residents;
    }

    public void addResident(OrganismDTO resident) {
        Set<OrganismDTO> organismSet = residents.computeIfAbsent(resident.getType(), k -> new HashSet<>());
        organismSet.add(resident);
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Location)) return false;
        final Location other = (Location) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getRow() != other.getRow()) return false;
        if (this.getColumn() != other.getColumn()) return false;
        final Object this$directions = this.getDirections();
        final Object other$directions = other.getDirections();
        if (this$directions == null ? other$directions != null : !this$directions.equals(other$directions))
            return false;
        final Object this$residents = this.getResidents();
        final Object other$residents = other.getResidents();
        if (this$residents == null ? other$residents != null : !this$residents.equals(other$residents)) return false;
        final Object this$lock = this.getLock();
        final Object other$lock = other.getLock();
        if (this$lock == null ? other$lock != null : !this$lock.equals(other$lock)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Location;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getRow();
        result = result * PRIME + this.getColumn();
        final Object $directions = this.getDirections();
        result = result * PRIME + ($directions == null ? 43 : $directions.hashCode());
        final Object $residents = this.getResidents();
        result = result * PRIME + ($residents == null ? 43 : $residents.hashCode());
        final Object $lock = this.getLock();
        result = result * PRIME + ($lock == null ? 43 : $lock.hashCode());
        return result;
    }
}
