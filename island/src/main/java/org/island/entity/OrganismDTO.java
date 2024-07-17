package org.island.entity;


import lombok.*;

import java.util.HashMap;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganismDTO {
    private String type;
    private int groupId;
    private int organismId;
    private boolean isAlive;
    private double currentWeight;
    private String icon;
    private HashMap<String, Integer> ration;
    private double maxWeight;
    private int maxPopulation;
    private int speed;
}
