package org.island.entity;


import lombok.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganismDTO {
    private UUID uuid;
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

    public EOrganisms getOrganismType() {
        try {
            return EOrganisms.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EOrganisms.HERB;
        }
    }


}
