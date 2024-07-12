package org.island.services.config;

import org.island.entity.Group;
import org.island.repo.maps.FoodMap;
import org.island.repo.maps.Ration;

public class ConfigFoodMapService {

    public FoodMap createFoodMap(int[][] rationTable) {
        FoodMap foodMap = new FoodMap();
        Group[] groups = Group.values();
        int countGroups = groups.length;

        for (int groupId = 0; groupId < countGroups; groupId++) {
            Group group = groups[groupId];
            String type = group.getType();
            Ration ration = new Ration();

            for (int targetGroupId = 0; targetGroupId < countGroups; targetGroupId++) {
                Group targetGroup = groups[targetGroupId];
                String targetType = targetGroup.getType();
                int chanceToEat = rationTable[groupId][targetGroupId];
                if (chanceToEat > 0) {
                    ration.put(targetType, chanceToEat);
                }
            }
            foodMap.put(type, ration);
        }
        return foodMap;
    }
}
