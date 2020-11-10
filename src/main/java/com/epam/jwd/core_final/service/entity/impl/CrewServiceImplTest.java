package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrewServiceImplTest {

    @Test
    public void findAllCrewMembers() {
    }

    @Test
    public void findAllCrewMembersByCriteria() {
    }

    @Test
    public void findCrewMemberByCriteria() {

        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .name("Davey Bentley")
                .rankId(2)
                .roleId(4)
                .build();
    }

    @Test
    public void createCrewMember() {
    }
}