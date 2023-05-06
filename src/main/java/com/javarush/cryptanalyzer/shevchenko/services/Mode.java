package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.Random;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.in;
public class Mode {
    public static String generateRandomKey() {
        Random random = new Random();
        return String.valueOf(random.nextInt(33) + 1);
    }
    public static int getInputMode(String message) {
        String input;
        while (true) {
            System.out.println(message);
            input = in.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("You can only input 1, 2, or 3.");
            }
        }
    }
    public static int getInputKey(String message) {
        String input;
        while (true) {
            System.out.println(message);
            input = in.nextLine();
            if (input.equals("")) {
                input = generateRandomKey();
                return Integer.parseInt(input);
            } else if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 33) {
                return Integer.parseInt(input);
            } else {
                System.out.println("You can only input numbers from 1 to 33.");
            }
        }
    }
}

