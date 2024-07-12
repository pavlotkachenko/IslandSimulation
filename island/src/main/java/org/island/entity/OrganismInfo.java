package org.island.entity;

import lombok.*;
import org.island.repo.maps.Ration;
import org.island.util.Fullness;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganismInfo {
    private String type;
    private int groupId;
    private int organismId;
    private boolean isAlive;
    private double currentWeight;
    private String icon;
    private Fullness fullness;
    private Ration ration;

    //Limit
    private double maxWeight;
    private int maxPopulation;
    private int speed;

}
