package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.strategy.CrewStrategy;
import com.epam.jwd.core_final.context.strategy.SpaceshipStrategy;
import com.epam.jwd.core_final.context.strategy.Strategy;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;

import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private static NassaContext nassaContext;
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> missions = new ArrayList<>();
    private Strategy parsingStrategy;

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (nassaContext == null) {
            return nassaContext = new NassaContext();
        } else return nassaContext;
    }

    public void setParsingStrategy(Strategy parsingStrategy) {
        this.parsingStrategy = parsingStrategy;
    }

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) throws UnknownEntityException {

        switch (tClass.getName()) {
            case "com.epam.jwd.core_final.domain.CrewMember": {
                return (Collection<T>) crewMembers;
            }
            case "com.epam.jwd.core_final.domain.Spaceship": {
                return (Collection<T>) spaceships;
            }
            case "com.epam.jwd.core_final.domain.FlightMission": {
                return (Collection<T>) missions;
            }
            default: {
                throw new UnknownEntityException(tClass.getName() + " storage ");
            }
        }
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        setParsingStrategy(CrewStrategy.getInstance());
        parsingStrategy.execute(ApplicationProperties.getInstance());

        setParsingStrategy(SpaceshipStrategy.getInstance());
        parsingStrategy.execute(ApplicationProperties.getInstance());
    }
}
