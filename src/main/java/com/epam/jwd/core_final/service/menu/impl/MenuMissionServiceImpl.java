package com.epam.jwd.core_final.service.menu.impl;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.service.entity.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.menu.MenuMissionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuMissionServiceImpl implements MenuMissionService {

    private static MenuMissionServiceImpl instance;
    private FlightMission entity;

    private MenuMissionServiceImpl() {
    }

    public static MenuMissionServiceImpl getInstance() {
        if (instance == null) {
            return instance = new MenuMissionServiceImpl();
        } else return instance;
    }

    public void createMission() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the mission's name:");
        String name = scanner.nextLine();

        long input;
        do {
            System.out.println("Enter the distance:\n");
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextLong();
        } while (input < 0);

        Long distance = input;

        do {
            System.out.println("Enter the duration in days:\n");
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextLong();
        } while (input < 0);

        Long duration = input;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDateTimeFormat());
        LocalDateTime startDate = LocalDateTime.now().plusDays((long) (Math.random() * 100));
        LocalDateTime endDate = startDate.plusDays(duration);

        startDate.format(formatter);
        endDate.format(formatter);

        try {
            FlightMission mission = MissionServiceImpl.getInstance().createMission(name, startDate, endDate, distance);
            System.out.println("Mission created!\n" + mission);
        } catch (EntityCreationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchMissionById() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        long input;
        do {
            System.out.println("Enter the id\n");
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextLong();
        } while (input < 0);

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .id(input)
                .build();

        this.entity = MissionServiceImpl.getInstance().findMissionByCriteria(criteria).orElseThrow(() -> new StorageException("Mission not found"));
        System.out.println(entity.toString());
    }

    public void searchMissionByName() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name\n");

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .name(scanner.nextLine())
                .build();

        this.entity = MissionServiceImpl.getInstance().findMissionByCriteria(criteria).orElseThrow(() -> new StorageException("Mission not found"));
        System.out.println(entity.toString());
    }

    public void searchMissionByDistance() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        long input;
        do {
            System.out.println("Enter the distance\n");
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextLong();
        } while (input < 0);

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .distance(input)
                .build();

        this.entity = MissionServiceImpl.getInstance().findMissionByCriteria(criteria).orElseThrow(() -> new StorageException("Mission not found"));
        System.out.println(entity.toString());
    }

    public void searchAllByStartDate() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start date in format " + ApplicationProperties.getInstance().getDateTimeFormat());

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .startDate(LocalDateTime.parse(scanner.nextLine()))
                .build();

        List<FlightMission> result = MissionServiceImpl.getInstance().findAllMissionsByCriteria(criteria);

        if (result.size() == 0) {
            throw new StorageException("Missions not found");
        }

        for (FlightMission mission : result) {
            System.out.println(mission.toString() + "\n");
        }
    }

    public void updateMissionName() {
        if (!this.entity.getMissionStatus().equals(MissionStatus.FAILED)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new name\n");
            this.entity.setName(scanner.nextLine());

            System.out.println("Name updated!");

        } else {
            System.out.println("Mission's name can't be updated, because mission is failed");
        }
    }

    public void updateMissionDistance() {
        if (!this.entity.getMissionStatus().equals(MissionStatus.FAILED)) {

            Scanner scanner = new Scanner(System.in);
            long input;
            do {
                System.out.println("Enter the distance\n");
                while (!scanner.hasNextLong()) {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                input = scanner.nextLong();
            } while (input < 0);

            this.entity.setDistance(input);

            System.out.println("Distance updated!");

        } else {
            System.out.println("Mission's distance can't be updated, because mission is failed");
        }
    }

    public void updateMissionDuration() {
        if (!this.entity.getMissionStatus().equals(MissionStatus.FAILED)) {

            Scanner scanner = new Scanner(System.in);
            long duration;
            do {
                System.out.println("Enter the new duration in days:\n");
                while (!scanner.hasNextLong()) {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                duration = scanner.nextLong();
            } while (duration < 0);

            while (entity.getStartDate().plusDays(duration).isAfter(LocalDateTime.now())) {
                System.out.println("Duration is too low!");
                duration = scanner.nextLong();
            }

            this.entity.setEndDate(entity.getStartDate().plusDays(duration));

        } else {
            System.out.println("Mission's duration can't be updated, because mission is failed");
        }
    }
}
