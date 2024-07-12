package org.island.settings;

import org.island.repo.Limit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

import org.island.repo.maps.FoodMap;
import org.island.repo.maps.Ration;
import org.island.services.config.ConfigDefaultSettingService;
import org.island.services.config.ConfigFileUpdateService;
import org.island.services.config.ConfigFoodMapService;


@Getter
@Setter(AccessLevel.PUBLIC)
public final class Config {
    private static volatile Config CONFIG;

    private int rows;
    private int columns;
    private int period;
    private int[][] rationTable;
    private Map<String, Limit> limits;
    private Map<String, String> icons;
    private FoodMap foodMap;
    private double startWeightFactor;
    private double weightDecreaseFactor;
    private double deathThreshold;

    private Config() {
        ConfigFoodMapService foodMapService = new ConfigFoodMapService();
        ConfigDefaultSettingService defaultSettingService = new ConfigDefaultSettingService(foodMapService);
        ConfigFileUpdateService fileUpdateService = new ConfigFileUpdateService();

        defaultSettingService.loadDefaultSetting(this);
        fileUpdateService.updateFromFile(this);
    }

    public static Config getConfig() {
        Config result = CONFIG;
        if (result != null) {
            return result;
        }

        synchronized (Config.class) {
            if (CONFIG == null) {
                CONFIG = new Config();
            }
        }
        return CONFIG;
    }

    public Limit getLimit(String type) {
        return limits.get(type);
    }

    public String getIcon(String type) {
        return icons.get(type);
    }

    public Ration getRation(String type) {
        return foodMap.get(type);
    }
}
