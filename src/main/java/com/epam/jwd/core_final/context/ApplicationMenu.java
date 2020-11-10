package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.menu.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext(); // в методе гет менющку должен возвращать

    default void printAvailableOptions(Menu menu) {
        switch (menu) {
            case START: {
                System.out.println("Press 1 to go to the MISSION CONTROL PANEL\n" +
                        "Press 2 to go to the CREW CONTROL PANEL\n" +
                        "Press 3 to go to the SPACESHIP CONTROL PANEL\n" +
                        "Press 4 to EXIT\n");
                break;
            }
            case MISSION_CONTROL: {
                System.out.println("Press 1 to create a mission\n" +
                        "Press 2 to search a mission\n" +
                        "Press 3 to return to the start menu\n");
                break;
            }
            case MISSION_CRITERIA: {
                System.out.println("Press 1 to see the list of all existing missions\n" +
                        "Press 2 to search mission by its id\n" +
                        "Press 3 to search mission by its name\n" +
                        "Press 4 to search mission by its distance\n" +
                        "Press 5 to see the list of all missions starting at chosen day\n" +
                        "Press 6 to return to the mission control panel\n");
                break;
            }
            case MISSION_UPDATE: {
                System.out.println("Press 1 to change the name\n" +
                        "Press 2 to change the distance\n" +
                        "Press 3 to change the duration\n" +
                        "Press 4 to return to the mission control panel\n");
                break;
            }
            case CREW_CONTROL: {
                System.out.println("Press 1 to search a crew member\n" +
                        "Press 2 to return to the start menu\n");
                break;
            }
            case CREW_CRITERIA: {
                System.out.println("Press 1 to see the list of all crew members\n" +
                        "Press 2 to search a crew member by its id\n" +
                        "Press 3 to search a crew member by its name\n" +
                        "Press 4 to see the list of crew members with a chosen rank\n" +
                        "Press 5 to see the list of crew members with a chosen role\n" +
                        "Press 6 to return to the crew control panel\n");
                break;
            }
            case CREW_UPDATE: {
                System.out.println("Press 1 to change the name\n" +
                        "Press 2 to change the role\n" +
                        "Press 3 to change the rank\n" +
                        "Press 4 to return to the crew control panel\n");
                break;
            }
            case SPACESHIP_CONTROL: {
                System.out.println("Press 1 to search spaceships\n" +
                        "Press 2 to return to the start menu\n");
                break;
            }
            case SPACESHIP_CRITERIA: {
                System.out.println("Press 1 to see the list of all spaceships\n" +
                        "Press 2 to search spaceship by its id\n" +
                        "Press 3 to search spaceship by its name\n" +
                        "Press 4 to search spaceship by its distance\n" +
                        "Press 5 to return to the spaceship control panel\n");
                break;
            }
            case SPACESHIP_UPDATE: {
                System.out.println("Press 1 to change the name\n" +
                        "Press 2 to change the distance\n" +
                        "Press 3 to return to the spaceship control panel\n");
                break;
            }
            default: {
            }
        }
    }

    default Integer handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            while (!scanner.hasNextInt()) {
                String tmp = scanner.next();
                System.out.println("Invalid input. Please, enter the correct number");
            }
            input = scanner.nextInt();
        } while (input < 0);
        return input;
    }
}