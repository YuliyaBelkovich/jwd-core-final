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

import static com.epam.jwd.core_final.util.InputValidator.validateIntInput;
import static com.epam.jwd.core_final.util.InputValidator.validateLongInput;

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

        Long distance = validateLongInput("Enter the distance:\n");
        Long duration = validateLongInput("Enter the duration in days:\n");

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
        long input = validateLongInput("Enter the id\n");

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
        long input = validateLongInput("Enter the distance\n");

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .distance(input)
                .build();

        this.entity = MissionServiceImpl.getInstance().findMissionByCriteria(criteria).orElseThrow(() -> new StorageException("Mission not found"));
        System.out.println(entity.toString());
    }

    public void searchAllByStartDate() throws StorageException {

        int year = validateIntInput("Enter the year YYYY:\n");
        int month = validateIntInput("Enter the month MM:\n");
        int day = validateIntInput("Enter the day DD:\n");
        int hour = validateIntInput("Enter the hour:\n");
        int minute = validateIntInput("Enter the minute MM:\n");
        int second = validateIntInput("Enter the second SS:\n");

        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute, second);

        FlightMissionCriteria criteria = FlightMissionCriteria
                .builder()
                .startDate(date)
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
            long input = validateLongInput("Enter the distance\n");
            this.entity.setDistance(input);

            System.out.println("Distance updated!");
        } else {
            System.out.println("Mission's distance can't be updated, because mission is failed");
        }
    }

    public void updateMissionDuration() {
        if (!this.entity.getMissionStatus().equals(MissionStatus.FAILED)) {
            long duration = validateLongInput("Enter the new duration in days:\n");

            while (entity.getStartDate().plusDays(duration).isAfter(LocalDateTime.now())) {
                System.out.println("Duration is too low!");
                duration = validateLongInput("Enter the new duration in days:\n");
            }

            this.entity.setEndDate(entity.getStartDate().plusDays(duration));

        } else {
            System.out.println("Mission's duration can't be updated, because mission is failed");
        }
    }
}
