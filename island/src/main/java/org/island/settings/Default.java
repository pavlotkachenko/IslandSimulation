package org.island.settings;

import org.island.repo.Limit;

import java.util.Map;

import static java.util.Map.entry;

public final class Default {

    public static final int ROWS = 100;
    public static final int COLUMNS = 20;
    public static final int PERIOD = 500; //in milliseconds, period for one iteration
    public static final double START_WEIGHT_FACTOR = 0.75; //maxWeight for newly created/born organism
    public static final double WEIGHT_DECREASE_FACTOR = 0.02; // weight decreasing every iteration if organism not eat
    public static final double DEATH_THRESHOLD = 0.1; // ratio of organism current weight to it's max weight, when reached organism kills / die

    private Default() {
    }

    /*
    int[K1][K2] = V ,where:
        K1 - Creature groupId;
        K2 - Target groupId;
        V - chance to eat.
    */

    public static final int[][] RATION_TABLE = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    /*
    Map<K,V> LIMITS,where:
        K - organism String type;
        V - limits of this organism Limit(double maxWeight, int maxPopulation, int speed).
    */
    //TODO: needs to simplify it, maybe through reflection API
    public static final Map<String, Limit> LIMITS = Map.ofEntries(
            entry("Wolf", new Limit(50, 30, 3)),
            entry("Python", new Limit(15, 30, 1)),
            entry("Fox", new Limit(8, 30, 2)),
            entry("Bear", new Limit(500, 5, 2)),
            entry("Eagle", new Limit(6, 20, 3)),
            entry("Horse", new Limit(400, 20, 4)),
            entry("Deer", new Limit(300, 20, 4)),
            entry("Rabbit", new Limit(2, 150, 2)),
            entry("Mouse", new Limit(0.05, 500, 1)),
            entry("Goat", new Limit(60, 140, 3)),
            entry("Sheep", new Limit(70, 140, 3)),
            entry("Boar", new Limit(400, 50, 2)),
            entry("Buffalo", new Limit(700, 10, 3)),
            entry("Duck", new Limit(1, 200, 4)),
            entry("Caterpillar", new Limit(0.01, 1000, 0)),
            entry("Herb", new Limit(10, 200, 0))
    );

    /*
    Map<K,V> ICONS,where:
        K - organism String type;
        V - organism icon.
    */
    public static final Map<String, String> ICONS = Map.ofEntries(
            entry("Wolf", "🐺"),
            entry("Python", "🐍"),
            entry("Fox", "🦊"),
            entry("Bear", "🐻"),
            entry("Eagle", "🦅"),
            entry("Horse", "🐎"),
            entry("Deer", "🦌"),
            entry("Rabbit", "🐇"),
            entry("Mouse",  "🐁"),
            entry("Goat", "🦌"),
            entry("Sheep", "🐑"),
            entry("Boar", "🐗"),
            entry("Buffalo", "🐃"),
            entry("Duck", "🦆"),
            entry("Caterpillar", "🐛"),
            entry("Herb", "☘")
    );

}
