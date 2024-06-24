package org.island.entity;

public enum Group {
    WOLF("Wolf", 0),
    PYTHON("Python", 1),
    FOX("Fox", 2),
    BEAR("Bear", 3),
    EAGLE("Eagle", 4),
    HORSE("Horse", 5),
    DEER("Deer", 6),
    RABBIT("Rabbit", 7),
    MOUSE("Mouse", 8),
    GOAT("Goat", 9),
    SHEEP("Sheep", 10),
    BOAR("Boar", 11),
    BUFFALO("Buffalo", 12),
    DUCK("Duck", 13),
    CATERPILLAR("Caterpillar", 14),
    HERB("Herb", 15);

    private final String type;
    private final int groupId;

    public int getGroupId(){
        return groupId;
    }

    public String getType(){
        return type;
    }

    Group(String type, int groupId){
        this.type = type;
        this.groupId = groupId;
    }


}
