package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CrewServiceImplTest {

    @BeforeClass
    public static void start() throws InvalidStateException {
        Application.start();
    }

    @Test
    public void findAllCrewMembers() {
        List<CrewMember> crewMembers = CrewServiceImpl.getInstance().findAllCrewMembers();
        assertEquals(100,crewMembers.size());
    }

    @Test
    public void findAllCrewMembersByCriteria() {
        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .rankId(1)
                .build();

        List<CrewMember> crewMembers = CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria);

        for(CrewMember crewMember: crewMembers){
            long actual = crewMember.getRank().getId();
            assertEquals(1L, actual);
        }
    }

    @Test
    public void findCrewMemberByCriteria() {
        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .name("Davey Bentley")
                .build();
        CrewMember crewMember = CrewServiceImpl.getInstance().findCrewMemberByCriteria(criteria).get();
        assertEquals(criteria.getName(),crewMember.getName());
    }
}