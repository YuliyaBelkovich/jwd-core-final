package com.epam.jwd.core_final.service.menu;

public interface MenuMissionService {
    void createMission();

    void searchAllMissions();

    void searchMissionById();

    void searchMissionByName();

    void searchMissionByDistance();

    void searchAllByStartDate();

    void updateMissionName();

    void updateMissionDistance();

    void updateMissionDuration();

    void updateMissionStatus();
}
