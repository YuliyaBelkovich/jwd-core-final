package com.epam.jwd.core_final.domain;

import java.util.Map;
import java.util.Objects;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    //todo
    private Map<Role, Short> crew;
    private Long distance;
    private boolean isReadyForNextMissions = true;

    public Spaceship(String name, Long distance, Map<Role, Short> crew) {
        super(name);
        this.crew = crew;
        this.distance = distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getDistance() {
        return distance;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    @Override
    public String toString() {
        return "[Spaceship " + "\"" + super.getName() + "\"\n" +
                "Capacity " + crew.toString() + "\n" +
                "Distance " + distance + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spaceship)) return false;
        Spaceship spaceship = (Spaceship) o;
        return getCrew().equals(spaceship.getCrew()) &&
                getDistance().equals(spaceship.getDistance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCrew(), getDistance());
    }
}
