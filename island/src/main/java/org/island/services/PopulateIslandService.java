package org.island.services;

import lombok.RequiredArgsConstructor;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.location.Island;
import org.island.location.Location;
import org.island.repo.OrganismFactory;
import org.island.repo.maps.Residents;
import org.island.util.Randomizer;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class PopulateIslandService {
    public void populateIsland(Island island) {
        Location[][] grid = island.getGRID();
        for (Location[] locations : grid) {
            for (Location location : locations) {
                Group randomGroup = Group.values()[Randomizer.random(Group.values().length - 1)];
                Organism organism = OrganismFactory.createOrganism(randomGroup);
                Residents residents = location.getResidents();
                Set<Organism> organisms = residents.getOrDefault(randomGroup.getType(), new HashSet<>());
                organisms.add(organism);
                residents.put(randomGroup.getType(), organisms);
            }
        }
    }

}
