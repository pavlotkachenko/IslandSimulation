package org.island.util;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    private Randomizer(){}

    public static int random(int maxValue){
        return ThreadLocalRandom.current().nextInt(maxValue + 1);
    }

    public static int random(int minValue, int maxValue){
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

}
