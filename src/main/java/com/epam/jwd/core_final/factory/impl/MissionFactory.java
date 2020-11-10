package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDateTime;

public class MissionFactory implements EntityFactory {
    @Override
    public FlightMission create(Object... args) throws EntityCreationException {
        if (args[0] instanceof String
                && args[1] instanceof LocalDateTime
                && args[2] instanceof LocalDateTime
                && args[3] instanceof Long) {

            return new FlightMission((String) args[0],
                    (LocalDateTime) args[1],
                    (LocalDateTime) args[2],
                    (Long) args[3]
            );

        } else throw new EntityCreationException("Flight Mission");
    }
}
