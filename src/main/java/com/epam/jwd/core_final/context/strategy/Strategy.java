package com.epam.jwd.core_final.context.strategy;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.Collection;

public interface Strategy {
    public void execute(ApplicationProperties properties) throws InvalidStateException;
}
