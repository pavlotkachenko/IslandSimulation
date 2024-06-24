package org.island.repo;


import lombok.*;
import org.island.repo.maps.Ration;
import org.island.util.Fullness;

import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganismInfo {
    private String type;
    private int groupId;
    private int organismId;
    private boolean isAlive;
    private double currentWeight;
    private String icon;
    private Fullness fullness;
    private Ration ration;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o ==null || getClass() != o.getClass()) return false;
        OrganismInfo that = (OrganismInfo) o;
        return groupId == that.groupId && organismId == that.organismId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(groupId, organismId);
    }
}
