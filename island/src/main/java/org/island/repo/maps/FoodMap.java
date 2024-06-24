package org.island.repo.maps;

import java.util.HashMap;

/*
Map<K,V> foodMap,contain:
    K - organism String type;
    V - organism ration with chanceToEat > 0 (Map<String organism, Integer chanceToEat>).
*/

public class FoodMap extends HashMap<String, Ration> {
}
