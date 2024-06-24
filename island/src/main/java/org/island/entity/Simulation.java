package org.island.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.island.location.Island;
import org.island.repo.factory.Factory;
import org.island.view.View;

@Getter
@RequiredArgsConstructor
public class Simulation {
    private final Island island;
    private final Factory entityFactory;
    private final View view;
}
