package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<CrewMember> {
    private Long distance;
    private Map<Role, Short> capacity;

    public SpaceshipCriteria(Long id, String name, Long distance, Map<Role, Short> capacity) {
        super(id, name);
        this.distance = distance;
        this.capacity = capacity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getDistance() {
        return distance;
    }

    public Map<Role, Short> getCapacity() {
        return capacity;
    }


    public static class Builder extends Criteria.Builder<SpaceshipCriteria.Builder> {
        Long distance;
        Map<Role, Short> capacity;

        public Builder distance(Long value) {
            this.distance = value;
            return this;
        }

        public Builder capacity(Map<Role, Short> value) {
            this.capacity = value;
            return this;
        }

        public SpaceshipCriteria build() {
            return new SpaceshipCriteria(id, name, distance, capacity);
        }
    }
}
