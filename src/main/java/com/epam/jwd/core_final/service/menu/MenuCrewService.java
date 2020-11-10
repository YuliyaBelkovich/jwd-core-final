package com.epam.jwd.core_final.service.menu;

public interface MenuCrewService {
    void searchCrewMemberById();

    void searchCrewMemberByName();

    void searchAllCrewMembersByRank();

    void searchAllCrewMembersByRole();

    void updateCrewMembersRank();

    void updateCrewMembersRole();

    void updateCrewMembersName();

}
