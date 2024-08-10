package org.island.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.island.settings.Config;


@Data
@ToString
public class Island {
    private Location[][] GRID;
}