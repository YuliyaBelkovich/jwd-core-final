package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public class Criteria<T extends BaseEntity> {
    private final Long id;
    private final String name;

    public Criteria(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder<SELF extends Criteria.Builder<SELF>> {
        Long id;
        String name;

        public SELF id(Long value) {
            this.id = value;
            return (SELF) this;
        }

        public SELF name(String value) {
            this.name = value;
            return (SELF) this;
        }

        public Criteria build() {
            return new Criteria(id, name);
        }
    }


}
