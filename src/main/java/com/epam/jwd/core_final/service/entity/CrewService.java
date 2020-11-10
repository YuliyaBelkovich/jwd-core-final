package com.epam.jwd.core_final.service.entity;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.StorageException;

import java.util.List;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    List<CrewMember> findAllCrewMembers();

    List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria);

    Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria);

    CrewMember updateCrewMemberDetails(CrewMember crewMember);

    // todo create custom exception for case, when crewMember is not able to be assigned
    void assignCrewMemberOnMission(FlightMission mission) throws RuntimeException;

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // crewmember unique criteria - only name!
    CrewMember createCrewMember(Integer roleId, String name, Integer rankId) throws RuntimeException, StorageException, EntityCreationException;
}