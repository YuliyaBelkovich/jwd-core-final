package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory {

    @Override
    public Spaceship create(Object... args) throws EntityCreationException {
        if (args[0] instanceof String
                && args[1] instanceof Long
                && args[2] instanceof Map) {

            return new Spaceship((String) args[0],
                    (Long) args[1],
                    (Map<Role, Short>) args[2]);

        } else throw new EntityCreationException("Spaceship");
    }
}
