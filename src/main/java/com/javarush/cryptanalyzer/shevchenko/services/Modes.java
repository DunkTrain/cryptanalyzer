package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.Random;
import java.util.Scanner;

import static com.javarush.cryptanalyzer.shevchenko.constants.Alphabet.LENGTH_CHARACTERS;

public class Modes {
    static Scanner in = new Scanner(System.in);

    // Проверка режимов программы ввода
    public static int mode (String message) {
        String input;
        while (true) {
            System.out.println(message);
            input = in.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Введите одно из трех чисел: 1, 2 или 3");
            }
        }
    }

    public static int isKey(String message) {
        String input;
        while (true) {
            System.out.println(message);
            input = in.nextLine();
            if (input.equals("")) {
                return generateRandomKey(); // Генерация случайного ключа
            } else if (isValidKey(input)) {
                return Integer.parseInt(input);
            } else {
                System.out.printf("Вводите только числа от 1 до %d.\n", LENGTH_CHARACTERS);
            }
        }
    }

    // Проверка валидности введенного ключа
    public static boolean isValidKey(String key) {
        try {
            int intKey = Integer.parseInt(key);
            return intKey >= 1 && intKey <= LENGTH_CHARACTERS;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Генерация случайного ключа
    public static int generateRandomKey() {
        Random random = new Random();
        return random.nextInt(LENGTH_CHARACTERS) + 1;
    }


}
