package org.island.model;

import lombok.*;
import org.island.entity.OrganismDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

@Data
@RequiredArgsConstructor
@ToString
public class Location {
    private final int row;
    private final int column;
    private List<Location> directions;
    private HashMap<String, Set<OrganismDTO>> residents;

    @Getter
    private final Lock lock;
}
