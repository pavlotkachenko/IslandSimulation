package org.island.services.config;
import org.island.settings.Config;
import org.island.settings.Default;
import org.island.repo.Limit;
import org.island.repo.maps.FoodMap;

import java.util.Map;


public class ConfigDefaultSettingService {
    private final ConfigFoodMapService foodMapService;

    public ConfigDefaultSettingService(ConfigFoodMapService foodMapService) {
        this.foodMapService = foodMapService;
    }

    public void loadDefaultSetting(Config config) {
        config.setRows(Default.ROWS);
        config.setColumns(Default.COLUMNS);
        config.setPeriod(Default.PERIOD);
        config.setRationTable(Default.RATION_TABLE);
        config.setLimits(Default.LIMITS);
        config.setIcons(Default.ICONS);
        config.setStartWeightFactor(Default.START_WEIGHT_FACTOR);
        config.setWeightDecreaseFactor(Default.WEIGHT_DECREASE_FACTOR);
        config.setDeathThreshold(Default.DEATH_THRESHOLD);

        FoodMap foodMap = foodMapService.createFoodMap(Default.RATION_TABLE);
        config.setFoodMap(foodMap);
    }
}
