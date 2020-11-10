package com.epam.jwd.core_final.domain;

import java.time.LocalDateTime;

public enum MissionStatus {
    FAILED,
    PLANNED,
    IN_PROGRESS,
    COMPLETED;

    public static MissionStatus updateMissionStatus(FlightMission flightMission) {
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isBefore(flightMission.getStartDate())) {
            return MissionStatus.PLANNED;
        } else if (currentDate.isAfter(flightMission.getStartDate()) || currentDate.isBefore(flightMission.getEndDate())) {
            changeAvailability(flightMission, false);

            double chanceOfFailure = 0.03;
            double currentValue = Math.random();

            if (currentValue < chanceOfFailure) {
                return MissionStatus.FAILED;
            } else {
                return MissionStatus.IN_PROGRESS;
            }
        } else {
            if (!flightMission.getMissionStatus().equals(MissionStatus.FAILED)) {
                changeAvailability(flightMission, true);
                return MissionStatus.COMPLETED;
            } else return MissionStatus.FAILED;
        }
    }

    private static void changeAvailability(FlightMission mission, boolean status) {
        mission.getSpaceship().setReadyForNextMissions(status);
        for (CrewMember member : mission.getAssignedCrew()) {
            member.setReadyForNextMissions(status);
        }
    }
}
