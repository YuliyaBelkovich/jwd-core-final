package com.epam.jwd.core_final.service.entity;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.EntityCreationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria);

    Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria);

    FlightMission updateMissionDetails(FlightMission flightMission);

    FlightMission createMission(String name, LocalDateTime startDate, LocalDateTime endDate, Long distance) throws EntityCreationException;

    void writeOneMissionToJson(FlightMission flightMission);

    void writeMissionsListToJson(List<FlightMission> flightMissions);
}
