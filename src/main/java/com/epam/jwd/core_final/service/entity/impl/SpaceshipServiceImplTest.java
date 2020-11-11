package com.epam.jwd.core_final.service.entity.impl;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpaceshipServiceImplTest {

    @BeforeClass
    public static void start() throws InvalidStateException {
        Application.start();
    }

    @Test
    public void findAllSpaceships() {
        List<Spaceship> spaceships = SpaceshipServiceImpl.getInstance().findAllSpaceships();
        assertEquals(30,spaceships.size());
    }

    @Test
    public void findAllSpaceshipsByCriteria() {
        SpaceshipCriteria criteria = SpaceshipCriteria
                .builder()
                .distance(100000L)
                .build();

        List<Spaceship> spaceships = SpaceshipServiceImpl.getInstance().findAllSpaceshipsByCriteria(criteria);

        for (Spaceship spaceship : spaceships){
            long actual = spaceship.getDistance();
            assertNotEquals(99999L,actual);
        }
    }

    @Test
    public void findSpaceshipByCriteria() {

        SpaceshipCriteria criteria = SpaceshipCriteria
                .builder()
                .distance(201117L)
                .build();

        Spaceship spaceship = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(criteria).get();
        assertEquals(criteria.getDistance(),spaceship.getDistance());

    }
}