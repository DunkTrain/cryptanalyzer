package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.in;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.ALPHABET;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.LENGTH_ALPHABET;

public class Mode {
    public static String generateRandomKey() {
        int random = (int) (1 + Math.random() * ALPHABET.length() - 1);
        return String.valueOf(random);
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
            } else if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= LENGTH_ALPHABET) {
                return Integer.parseInt(input);
            } else {
                System.out.printf("You can only input numbers from 1 to %d. \n", LENGTH_ALPHABET);
            }
        }
    }
}

