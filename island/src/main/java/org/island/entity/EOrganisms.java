package org.island.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EOrganisms {
    WOLF("Wolf", 0, true),
    PYTHON("Python", 1, true),
    FOX("Fox", 2, true),
    BEAR("Bear", 3, true),
    EAGLE("Eagle", 4, true),
    HORSE("Horse", 5, true),
    DEER("Deer", 6, true),
    RABBIT("Rabbit", 7, true),
    MOUSE("Mouse", 8, true),
    GOAT("Goat", 9, true),
    SHEEP("Sheep", 10, true),
    BOAR("Boar", 11, true),
    BUFFALO("Buffalo", 12, true),
    DUCK("Duck", 13, true),
    CATERPILLAR("Caterpillar", 14, true),
    HERB("Herb", 15, false);

    private final String type;
    private final int groupId;
    private final boolean isAnimal;
    //groupId -> group (plants, grasseaters, predators)
}
