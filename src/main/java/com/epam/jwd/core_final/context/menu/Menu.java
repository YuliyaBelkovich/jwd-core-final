package com.epam.jwd.core_final.context.menu;

public enum Menu {
    START(4),
    MISSION_CONTROL(3),
    MISSION_CRITERIA(6),
    MISSION_UPDATE(5),
    SPACESHIP_CONTROL(2),
    SPACESHIP_CRITERIA(5),
    SPACESHIP_UPDATE(3),
    CREW_CONTROL(2),
    CREW_CRITERIA(6),
    CREW_UPDATE(4),
    EXIT(0);

    private final int numberOfOptions;

    Menu(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    public int getNumberOfOptions() {
        return numberOfOptions;
    }
}
