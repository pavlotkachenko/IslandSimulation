package org.island.repo.maps;

import org.island.entity.Organism;

import java.util.HashMap;
import java.util.Set;

/*
Map<K,V> residents, contain:
    K - creature String type;
    V - set of these organisms.
*/

public class Residents extends HashMap<String, Set<Organism>> {
}
