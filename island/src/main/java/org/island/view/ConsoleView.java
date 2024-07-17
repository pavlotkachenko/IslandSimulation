package org.island.view;

import lombok.RequiredArgsConstructor;
import org.island.model.Island;

@RequiredArgsConstructor
public class ConsoleView implements View {
    private final Island island;
    @Override
    public void showStatistics() {

    }

    @Override
    public void showMap() {

    }
}
