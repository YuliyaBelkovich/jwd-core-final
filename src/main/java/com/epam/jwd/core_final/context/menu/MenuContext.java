package com.epam.jwd.core_final.context.menu;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.service.entity.CrewService;
import com.epam.jwd.core_final.service.menu.MenuCrewService;
import com.epam.jwd.core_final.service.menu.MenuMissionService;
import com.epam.jwd.core_final.service.entity.SpaceshipService;
import com.epam.jwd.core_final.service.entity.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.menu.MenuSpaceshipService;
import com.epam.jwd.core_final.service.menu.impl.MenuCrewServiceImpl;
import com.epam.jwd.core_final.service.menu.impl.MenuMissionServiceImpl;
import com.epam.jwd.core_final.service.entity.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.service.menu.impl.MenuSpaceshipServiceImpl;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MenuContext {

    private static MenuContext menuContext;
    Map<KeyMenu, Supplier<Menu>> storage = new HashMap<>();
    private static final Logger logger = Logger.getLogger(MenuContext.class);

    {
        fillStorage();
    }

    private MenuContext() {
    }

    public static MenuContext getInstance() {
        if (menuContext == null) {
            return menuContext = new MenuContext();
        } else return menuContext;
    }

    public Menu execute(Menu menu, Integer input) {

        if (input < 1 || input > menu.getNumberOfOptions()) {
            System.out.println("Invalid input. Please, press the correct number");
            return menu;
        }
        return storage.get(new KeyMenu(menu, input)).get();
    }


    private void fillStorage() {
        assignStartMenuOptions();

        assignMissionControlMenuOptions();
        assignMissionCriteriaOptions();
        assignMissionUpdateOptions();

        assignCrewControlMenuOptions();
        assignCrewCriteriaOptions();
        assignCrewUpdateOptions();

        assignSpaceshipControlMenuOptions();
        assignSpaceshipCriteriaOptions();
        assignSpaceshipUpdateOptions();
    }

    private void assignStartMenuOptions() {
        this.storage.put(new KeyMenu(Menu.START, 1), () -> Menu.MISSION_CONTROL);   /* go to mission control from start menu*/

        this.storage.put(new KeyMenu(Menu.START, 2), () -> Menu.CREW_CONTROL); /* go to crew control menu from start menu*/

        this.storage.put(new KeyMenu(Menu.START, 3), () -> Menu.SPACESHIP_CONTROL);   /* go to spaceship control menu from start menu*/

        this.storage.put(new KeyMenu(Menu.START, 4), () -> Menu.EXIT); /* exit from start menu*/
    }

    private void assignMissionControlMenuOptions() {
        /*create a mission*/
        this.storage.put(new KeyMenu(Menu.MISSION_CONTROL, 1), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            service.createMission();
            return Menu.MISSION_CONTROL;
        });

        this.storage.put(new KeyMenu(Menu.MISSION_CONTROL, 2), () -> Menu.MISSION_CRITERIA); /*go to mission criteria menu*/

        this.storage.put(new KeyMenu(Menu.MISSION_CONTROL, 3), () -> Menu.START);  /*exit from mission control menu*/
    }

    private void assignMissionCriteriaOptions() {
        /*search all missions*/
        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 1), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            service.searchAllMissions();
            return Menu.MISSION_CRITERIA;
        });

        /*search one mission by id*/
        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 2), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            try {
                service.searchMissionById();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.MISSION_CRITERIA;
            }
            return Menu.MISSION_UPDATE;
        });

        /*search one mission by name*/
        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 3), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            try {
                service.searchMissionByName();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.MISSION_CRITERIA;
            }
            return Menu.MISSION_UPDATE;
        });

        /*search one mission by distance*/
        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 4), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            try {
                service.searchMissionByDistance();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.MISSION_CRITERIA;
            }
            return Menu.MISSION_UPDATE;
        });

        /*search all missions by start date*/
        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 5), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            try {
                service.searchAllByStartDate();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
            }
            return Menu.MISSION_CRITERIA;
        });

        this.storage.put(new KeyMenu(Menu.MISSION_CRITERIA, 6), () -> Menu.MISSION_CONTROL); /*exit from mission criteria menu*/

    }

    private void assignMissionUpdateOptions() {
        /*update mission's name*/
        this.storage.put(new KeyMenu(Menu.MISSION_UPDATE, 1), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            service.updateMissionName();

            return Menu.MISSION_CONTROL;
        });

        /*update mission distance*/
        this.storage.put(new KeyMenu(Menu.MISSION_UPDATE, 2), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            service.updateMissionDistance();

            return Menu.MISSION_CONTROL;
        });

        /*update mission duration*/
        this.storage.put(new KeyMenu(Menu.MISSION_UPDATE, 3), () -> {
            MenuMissionService service = MenuMissionServiceImpl.getInstance();
            service.updateMissionDuration();

            return Menu.MISSION_CONTROL;
        });

        this.storage.put(new KeyMenu(Menu.MISSION_UPDATE, 4), () -> Menu.MISSION_CONTROL);    /*exit from mission update*/
    }

    private void assignCrewControlMenuOptions() {
        this.storage.put(new KeyMenu(Menu.CREW_CONTROL, 1), () -> Menu.CREW_CRITERIA); /*go to crew criteria*/

        this.storage.put(new KeyMenu(Menu.CREW_CONTROL, 2), () -> Menu.START); /*go to start menu*/
    }

    private void assignCrewCriteriaOptions() {
        /*search all crew members*/
        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 1), () -> {
            CrewService service = CrewServiceImpl.getInstance();

            for (CrewMember crewMember : service.findAllCrewMembers()) {
                System.out.println(crewMember.toString());
            }

            return Menu.CREW_CRITERIA;
        });

        /*search one crew member by id*/
        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 2), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            try {
                service.searchCrewMemberById();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.CREW_CRITERIA;
            }
            return Menu.CREW_UPDATE;
        });

        /*search one crew member by name*/
        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 3), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            try {
                service.searchCrewMemberByName();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.CREW_CRITERIA;
            }
            return Menu.CREW_UPDATE;
        });

        /*search all crew members by rank*/
        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 4), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            try {
                service.searchAllCrewMembersByRank();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.CREW_CRITERIA;
            }
            return Menu.CREW_CRITERIA;
        });

        /*search all crew members by role*/
        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 5), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            try {
                service.searchAllCrewMembersByRole();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
            }
            return Menu.CREW_CRITERIA;
        });

        this.storage.put(new KeyMenu(Menu.CREW_CRITERIA, 6), () -> Menu.CREW_CONTROL); /*go to crew control panel*/
    }

    private void assignCrewUpdateOptions() {
        /*update crew member's name*/
        this.storage.put(new KeyMenu(Menu.CREW_UPDATE, 1), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            service.updateCrewMembersName();

            return Menu.CREW_CONTROL;
        });

        /*update crew member's role*/
        this.storage.put(new KeyMenu(Menu.CREW_UPDATE, 2), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            service.updateCrewMembersRole();

            return Menu.CREW_CONTROL;
        });

        /*update crew member's rank*/
        this.storage.put(new KeyMenu(Menu.CREW_UPDATE, 3), () -> {
            MenuCrewService service = MenuCrewServiceImpl.getInstance();
            service.updateCrewMembersRank();

            return Menu.CREW_CONTROL;
        });

        this.storage.put(new KeyMenu(Menu.CREW_UPDATE, 4), () -> Menu.CREW_CONTROL); /*go to crew control panel*/
    }

    private void assignSpaceshipControlMenuOptions() {
        this.storage.put(new KeyMenu(Menu.SPACESHIP_CONTROL, 1), () -> Menu.SPACESHIP_CRITERIA); /*go to  spaceship criteria*/

        this.storage.put(new KeyMenu(Menu.SPACESHIP_CONTROL, 2), () -> Menu.START); /*go to start menu*/
    }

    private void assignSpaceshipCriteriaOptions() {
        /*search all spaceships*/
        this.storage.put(new KeyMenu(Menu.SPACESHIP_CRITERIA, 1), () -> {
            SpaceshipService service = SpaceshipServiceImpl.getInstance();

            for (Spaceship spaceship :
                    service.findAllSpaceships()) {
                System.out.println(spaceship.toString());
            }
            return Menu.SPACESHIP_CRITERIA;
        });

        this.storage.put(new KeyMenu(Menu.SPACESHIP_CRITERIA, 2), () -> {
            /*search one by id*/
            MenuSpaceshipService service = MenuSpaceshipServiceImpl.getInstance();
            try {
                service.searchSpaceshipById();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.SPACESHIP_CRITERIA;
            }
            return Menu.SPACESHIP_UPDATE;
        });

        /*search one spaceship by name*/
        this.storage.put(new KeyMenu(Menu.SPACESHIP_CRITERIA, 3), () -> {
            MenuSpaceshipService service = MenuSpaceshipServiceImpl.getInstance();
            try {
                service.searchSpaceshipByName();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.SPACESHIP_CRITERIA;
            }
            return Menu.SPACESHIP_UPDATE;
        });

        /*search one by distance*/
        this.storage.put(new KeyMenu(Menu.SPACESHIP_CRITERIA, 4), () -> {
            MenuSpaceshipService service = MenuSpaceshipServiceImpl.getInstance();
            try {
                service.searchSpaceshipByDistance();
            } catch (StorageException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
                return Menu.SPACESHIP_CRITERIA;
            }
            return Menu.SPACESHIP_UPDATE;
        });

        this.storage.put(new KeyMenu(Menu.SPACESHIP_CRITERIA, 5), () -> Menu.SPACESHIP_CONTROL); /*go to spaceship control menu*/
    }

    private void assignSpaceshipUpdateOptions() {
        /*update spaceship's name*/
        this.storage.put(new KeyMenu(Menu.SPACESHIP_UPDATE, 1), () -> {
            MenuSpaceshipService service = MenuSpaceshipServiceImpl.getInstance();
            service.updateSpaceshipsName();

            return Menu.SPACESHIP_CONTROL;
        });

        /*update spaceship's distance*/
        this.storage.put(new KeyMenu(Menu.SPACESHIP_UPDATE, 2), () -> {
            MenuSpaceshipService service = MenuSpaceshipServiceImpl.getInstance();
            service.updateSpaceshipsDistance();

            return Menu.SPACESHIP_CONTROL;
        });

        this.storage.put(new KeyMenu(Menu.SPACESHIP_UPDATE, 3), () -> Menu.SPACESHIP_CONTROL);   /*go to spaceship control menu*/
    }
}
