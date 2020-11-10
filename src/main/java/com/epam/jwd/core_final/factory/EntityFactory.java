package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.EntityCreationException;

public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args) throws EntityCreationException;
}
