package com.epam.jwd.core_final.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionStatus}
 */
public class FlightMission extends AbstractBaseEntity {
    // todo
    @JsonProperty("Start Date")
    private LocalDateTime startDate;
    @JsonProperty("End Date")
    private LocalDateTime endDate;
    @JsonProperty("Distance")
    private Long distance;
    @JsonProperty("Spaceship")
    private Spaceship spaceship;
    @JsonProperty("Crew")
    private List<CrewMember> assignedCrew = new ArrayList<>();
    @JsonProperty("Mission Status")
    private MissionStatus missionStatus;

    public FlightMission() {
    }

    public FlightMission(String name, LocalDateTime startDate, LocalDateTime endDate, Long distance) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionStatus getMissionStatus() {
        return this.missionStatus;
    }

    public MissionStatus updateMissionStatus() {
        return MissionStatus.updateMissionStatus(this);
    }


    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public void setMissionStatus(MissionStatus missionStatus) {
        this.missionStatus = missionStatus;
    }

    public void setCrewMember(CrewMember assignedCrewMember) {
        this.assignedCrew.add(assignedCrewMember);
        assignedCrewMember.setReadyForNextMissions(false);
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
        spaceship.setReadyForNextMissions(false);
    }

    @Override
    public String toString() {
        return "Mission " + super.getName() + " \n" +
                "Start: " + startDate
                .format(DateTimeFormatter
                        .ofPattern(ApplicationProperties
                                .getInstance()
                                .getDateTimeFormat())) + "\n" +
                "End: " + endDate
                .format(DateTimeFormatter
                        .ofPattern(ApplicationProperties
                                .getInstance()
                                .getDateTimeFormat())) + "\n" +
                "Spaceship: " + spaceship.getName() + "\n" +
                "Status: " + updateMissionStatus() + "\n";
    }
}
