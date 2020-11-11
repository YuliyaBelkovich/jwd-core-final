package com.epam.jwd.core_final.context.strategy;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;


public interface Strategy {
    void execute(ApplicationProperties properties) throws InvalidStateException;
}
