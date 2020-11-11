package com.epam.jwd.core_final.service.menu.impl;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.service.entity.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.service.menu.MenuSpaceshipService;

import static com.epam.jwd.core_final.util.InputValidator.validateLongInput;

import java.util.Scanner;

public class MenuSpaceshipServiceImpl implements MenuSpaceshipService {

    private static MenuSpaceshipServiceImpl instance;
    private Spaceship entity;

    private MenuSpaceshipServiceImpl() {
    }

    public static MenuSpaceshipServiceImpl getInstance() {
        if (instance == null) {
            return instance = new MenuSpaceshipServiceImpl();
        } else return instance;
    }

    public void searchSpaceshipById() throws StorageException {
        long input = validateLongInput("Enter the id\n");
        SpaceshipCriteria criteria = SpaceshipCriteria
                .builder()
                .id(input)
                .build();

        this.entity = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(criteria).orElseThrow(() -> new StorageException("Spaceship not found"));
        System.out.println(entity.toString());
    }

    public void searchSpaceshipByName() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name\n");
        SpaceshipCriteria criteria = SpaceshipCriteria
                .builder()
                .name(scanner.nextLine())
                .build();

        this.entity = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(criteria).orElseThrow(() -> new StorageException("Spaceship not found"));
        System.out.println(entity.toString());
    }

    public void searchSpaceshipByDistance() throws StorageException {
        long input = validateLongInput("Enter the distance\n");
        SpaceshipCriteria criteria = SpaceshipCriteria
                .builder()
                .distance(input)
                .build();

        this.entity = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(criteria).orElseThrow(() -> new StorageException("Spaceship not found"));
        System.out.println(entity.toString());
    }

    public void updateSpaceshipsName() {
        if (entity.isReadyForNextMissions()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new spaceship's name\n");

            this.entity.setName(scanner.nextLine());
            System.out.println("Name updated!");
        } else {
            System.out.println("Sorry, this spaceship is not available to update!\n");
        }
    }

    public void updateSpaceshipsDistance() {
        if (entity.isReadyForNextMissions()) {
            long input = validateLongInput("Enter the new spaceship's distance\n");
            this.entity.setDistance(input);
            System.out.println("Distance updated!");
        } else {
            System.out.println("Sorry, this spaceship is not available to update!\n");
        }
    }

}
