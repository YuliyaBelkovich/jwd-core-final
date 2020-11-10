package com.epam.jwd.core_final.exception;

public class EntityCreationException extends Exception {
    private final String entityName;

    public EntityCreationException(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return this.entityName + "can't be created";
    }
}
