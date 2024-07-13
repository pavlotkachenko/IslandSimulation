package org.island.settings;

import java.util.Map;

import static java.util.Map.entry;

//TODO Засунуть в ямл
public final class Default {

    public static final double START_WEIGHT_FACTOR = 0.75; //maxWeight for newly created/born organism
    public static final double WEIGHT_DECREASE_FACTOR = 0.02; // weight decreasing every iteration if organism not eat
    public static final double DEATH_THRESHOLD = 0.1; // ratio of organism current weight to it's max weight, when reached organism kills / die

    private Default() {
    }

}
