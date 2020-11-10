package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.entity.CrewService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {

    private static CrewServiceImpl crewService;
    private static NassaContext nassaContext = NassaContext.getInstance();
    private static final Logger logger = Logger.getLogger(CrewServiceImpl.class);

    private CrewServiceImpl() {
    }

    public static CrewServiceImpl getInstance() {
        if (crewService == null) {
            return crewService = new CrewServiceImpl();
        } else return crewService;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List) nassaContext.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria) throws UnknownEntityException {
        return nassaContext.retrieveBaseEntityList(CrewMember.class)
                .stream()
                .filter(crewMember -> {
                    Long id = criteria.getId();
                    return id == null || crewMember.getId().equals(id);
                })
                .filter(crewMember -> {
                    String name = criteria.getName();
                    return name == null || crewMember.getName().equals(name);
                })
                .filter(crewMember ->
                        criteria.getRoleId() == null || crewMember
                                .getRole()
                                .equals(Role.resolveRoleById(criteria.getRoleId())))
                .filter(crewMember ->
                        criteria.getRankId() == null || crewMember
                                .getRank()
                                .equals(Rank.resolveRankById(criteria.getRankId())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria) throws UnknownEntityException {
        return nassaContext.retrieveBaseEntityList(CrewMember.class)
                .stream()
                .filter(crewMember -> {
                    Long id = criteria.getId();
                    return id == null || crewMember.getId().equals(id);
                })
                .filter(crewMember -> {
                    String name = criteria.getName();
                    return name == null || crewMember.getName().equals(name);
                })
                .filter(crewMember ->
                        criteria.getRoleId() == null
                                || crewMember
                                .getRole()
                                .equals(Role.resolveRoleById(criteria.getRoleId())))
                .filter(crewMember ->
                        criteria.getRankId() == null
                                || crewMember
                                .getRank()
                                .equals(Rank.resolveRankById(criteria.getRankId())))
                .findAny();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        return null;
    }

    @Override
    public void assignCrewMemberOnMission(FlightMission mission) throws StorageException {
        Map<Role, Short> capacity = mission.getSpaceship().getCrew();

        capacity.forEach((role, aShort) -> {
            List<CrewMember> availableCrew = nassaContext.retrieveBaseEntityList(CrewMember.class)
                    .stream()
                    .filter(crewMember ->
                            crewMember.getRole().equals(role) && crewMember.isReadyForNextMissions())
                    .collect(Collectors.toList());
            if (availableCrew.size() < aShort) {
                throw new StorageException("Not enough available crew members. Try again later");
            } else {
                for (int i = 0; i < aShort; i++) {
                    mission.setCrewMember(availableCrew.get(i));
                }
            }
        });
    }

    @Override
    public CrewMember createCrewMember(Integer roleId, String name, Integer rankId) throws StorageException, EntityCreationException {
        EntityFactory<CrewMember> crewFactory = new CrewMemberFactory();
        CrewMember crewMember = crewFactory.create(roleId, name, rankId);

        for (CrewMember crewMember1 : nassaContext.retrieveBaseEntityList(CrewMember.class)) {
            if (crewMember1.equals(crewMember)) {
                throw new StorageException("This crew member is already exists");
            }
        }

        nassaContext.retrieveBaseEntityList(CrewMember.class).add(crewMember);
        crewMember.setId();
        logger.info("Crew member id=" + crewMember.getId() + " created and stored");

        return crewMember;
    }
}
