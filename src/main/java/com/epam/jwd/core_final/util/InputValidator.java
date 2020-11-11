package com.epam.jwd.core_final.util;

import java.util.Scanner;

public class InputValidator {

    public static int validateIntInput(String message) {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextInt();
        } while (input < 0);

        return input;
    }

    public static long validateLongInput(String message) {
        Scanner scanner = new Scanner(System.in);
        long input;
        do {
            System.out.println(message);
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            input = scanner.nextLong();
        } while (input < 0);
        return input;
    }
}
