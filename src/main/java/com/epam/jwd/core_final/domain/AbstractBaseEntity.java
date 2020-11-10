package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    private static Long counter = 0L;
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        // todo
        return this.id;
    }

    @Override
    public String getName() {
        // todo
        return this.name;
    }

    public void setId() {
        this.id = counter++;
    }

    public void setName(String name) {
        this.name = name;
    }
}
