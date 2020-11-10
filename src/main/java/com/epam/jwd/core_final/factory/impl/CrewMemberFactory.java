package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) throws EntityCreationException {

        if (args[0] instanceof Integer && args[2] instanceof Integer && args[1] instanceof String) {

            Role role = null;
            if (args[0] != null) {
                role = Role.resolveRoleById((Integer) args[0]);
            }

            Rank rank = null;
            if (args[2] != null) {
                rank = Rank.resolveRankById((Integer) args[2]);
            }

            return new CrewMember(
                    role,
                    (String) args[1],
                    rank);
        } else throw new EntityCreationException("Crew Member");
    }

}
