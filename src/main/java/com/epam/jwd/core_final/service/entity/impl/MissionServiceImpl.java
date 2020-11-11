package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.MissionFactory;
import com.epam.jwd.core_final.service.entity.CrewService;
import com.epam.jwd.core_final.service.entity.MissionService;
import com.epam.jwd.core_final.service.entity.SpaceshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {

    private static MissionServiceImpl missionService;
    private static NassaContext nassaContext = NassaContext.getInstance();
    private static final Logger log = Logger.getLogger(MissionServiceImpl.class);

    private MissionServiceImpl() {
    }

    public static MissionServiceImpl getInstance() {
        if (missionService == null) {
            return missionService = new MissionServiceImpl();
        } else return missionService;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return (List) nassaContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria) {
        return nassaContext.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(flightMission -> {
                    String name = criteria.getName();
                    return name == null || flightMission.getName().equals(name);
                })
                .filter(flightMission -> {
                    LocalDateTime startDate = criteria.getStartDate();
                    return startDate == null || flightMission.getStartDate().isEqual(startDate);
                })
                .filter(flightMission -> {
                    LocalDateTime endDate = criteria.getEndDate();
                    return endDate == null || flightMission.getEndDate().isEqual(endDate);
                })
                .filter(flightMission -> {
                    Long distance = criteria.getDistance();
                    return distance == null || flightMission.getDistance().equals(distance);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria) {
        return nassaContext.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(flightMission -> {
                    String name = criteria.getName();
                    return name == null || flightMission.getName().equals(name);
                })
                .filter(flightMission -> {
                    LocalDateTime startDate = criteria.getStartDate();
                    return startDate == null || flightMission.getStartDate().isEqual(startDate);
                })
                .filter(flightMission -> {
                    LocalDateTime endDate = criteria.getStartDate();
                    return endDate == null || flightMission.getEndDate().isEqual(endDate);
                })
                .filter(flightMission -> {
                    Long distance = criteria.getDistance();
                    return distance == null || flightMission.getDistance().equals(distance);
                })
                .findAny();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        return null;
    }

    @Override
    public FlightMission createMission(String name, LocalDateTime startDate, LocalDateTime endDate, Long distance) throws EntityCreationException {
        EntityFactory<FlightMission> factory = new MissionFactory();
        FlightMission mission = factory.create(name, startDate, endDate, distance);

        try {
            SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
            spaceshipService.assignSpaceshipOnMission(mission);

            CrewService crewService = CrewServiceImpl.getInstance();
            crewService.assignCrewMemberOnMission(mission);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            throw new EntityCreationException("Flight Mission");
        }

        nassaContext.retrieveBaseEntityList(FlightMission.class).add(mission);
        mission.setMissionStatus(MissionStatus.PLANNED);
        mission.setId();

        log.info("Mission id=" + mission.getId() + " created and stored");
        return mission;
    }

    public void writeToJson(FlightMission flightMission) {
        String baseFile = "src" + File.separator
                + "main" + File.separator +
                "resources" + File.separator +
                "output" + File.separator +
                ApplicationProperties.getInstance().getMissionsFileName() + ".json";

        File file = new File(baseFile);
        FileWriter fileWriter = null;
        try {
            file.createNewFile();
            fileWriter = new FileWriter(baseFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(fileWriter, flightMission);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
