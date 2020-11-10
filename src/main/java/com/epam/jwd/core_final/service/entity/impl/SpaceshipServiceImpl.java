package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.entity.SpaceshipService;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {

    private static SpaceshipServiceImpl spaceshipService;
    private static NassaContext nassaContext = NassaContext.getInstance();
    private static final Logger logger = Logger.getLogger(SpaceshipServiceImpl.class);

    private SpaceshipServiceImpl() {
    }

    public static SpaceshipServiceImpl getInstance() {
        if (spaceshipService == null) {
            return spaceshipService = new SpaceshipServiceImpl();
        } else return spaceshipService;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List) nassaContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(SpaceshipCriteria criteria) {
        return nassaContext.retrieveBaseEntityList(Spaceship.class)
                .stream()
                .filter(spaceship -> {
                    Long id = criteria.getId();
                    return id == null || spaceship.getId().equals(id);
                })
                .filter(spaceship -> {
                    String name = criteria.getName();
                    return name == null || spaceship.getName().equals(name);
                })
                .filter(spaceship -> {
                    Long distance = criteria.getDistance();
                    return distance == null || spaceship.getDistance().equals(distance);
                })
                .filter(spaceship -> {
                    Map<Role, Short> capacity = criteria.getCapacity();
                    return capacity == null || spaceship.getCrew().equals(capacity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(SpaceshipCriteria criteria) {
        return nassaContext.retrieveBaseEntityList(Spaceship.class)
                .stream()
                .filter(spaceship -> {
                    Long id = criteria.getId();
                    return id == null || spaceship.getId().equals(id);
                })
                .filter(spaceship -> {
                    String name = criteria.getName();
                    return name == null || spaceship.getName().equals(name);
                })
                .filter(spaceship -> {
                    Long distance = criteria.getDistance();
                    return distance == null || spaceship.getDistance().equals(distance);
                })
                .filter(spaceship -> {
                    Map<Role, Short> capacity = criteria.getCapacity();
                    return capacity == null || spaceship.getCrew().equals(capacity);
                })
                .findAny();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        return null;
    }

    @Override
    public void assignSpaceshipOnMission(FlightMission mission) throws StorageException {
        mission.setSpaceship(nassaContext.retrieveBaseEntityList(Spaceship.class)
                .stream()
                .filter(spaceship ->
                        spaceship.getDistance() >= mission.getDistance() && spaceship.isReadyForNextMissions())
                .findAny()
                .orElseThrow(() -> new StorageException("Not enough available spaceships at this moment. Try again later"))); // exception

    }

    @Override
    public Spaceship createSpaceship(String name, Long distance, Map<Role, Short> capacity) throws StorageException, EntityCreationException {
        EntityFactory<Spaceship> spaceshipFactory = new SpaceshipFactory();
        Spaceship spaceship = spaceshipFactory.create(name, distance, capacity);

        for (Spaceship spaceship1 : nassaContext.retrieveBaseEntityList(Spaceship.class)) {
            if (spaceship1.equals(spaceship)) {
                throw new StorageException("This spaceship is already exists");
            }
        }

        nassaContext.retrieveBaseEntityList(Spaceship.class).add(spaceship);
        spaceship.setId();
        logger.info("Spaceship id=" + spaceship.getId() + " created and stored");

        return spaceship;
    }
}
