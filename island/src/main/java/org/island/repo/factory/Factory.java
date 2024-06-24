package org.island.repo.factory;

import org.island.entity.Organism;

import java.util.Map;

public interface Factory {
    Organism create(String type);
    Map<String, Organism> getPrototypes();

}
