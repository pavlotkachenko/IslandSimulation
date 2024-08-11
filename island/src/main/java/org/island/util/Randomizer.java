package org.island.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    private static final Random RANDOM = new Random();

    private Randomizer(){}

    public static int random(int maxValue){
        return ThreadLocalRandom.current().nextInt(maxValue + 1);
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

}
