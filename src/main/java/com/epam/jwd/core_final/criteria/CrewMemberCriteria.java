package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private final Integer roleId;
    private final Integer rankId;

    public CrewMemberCriteria(Long id, String name, Integer roleId, Integer rankId) {
        super(id, name);
        this.roleId = roleId;
        this.rankId = rankId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getRankId() {
        return rankId;
    }

    public static class Builder extends Criteria.Builder<CrewMemberCriteria.Builder> {
        public Integer roleId;
        public Integer rankId;

        public Builder roleId(Integer value) {
            this.roleId = value;
            return this;
        }

        public Builder rankId(Integer value) {
            this.rankId = value;
            return this;
        }

        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(id, name, roleId, rankId);
        }
    }

}
