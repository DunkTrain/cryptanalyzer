package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.in;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.LENGTH_ALPHABET;

/**
 * Класс содержит методы для работы с режимами приложения.
 */
public class Mode {

    /**
     * Генерирует случайный ключ шифрования
     * @return случайный ключ в диапазоне [1, LENGTH_ALPHABET-1]
     */
    public static String generateRandomKey() {
        int random = (int) (1 + Math.random() * LENGTH_ALPHABET - 1);
        return String.valueOf(random);
    }

    /**
     * Получает режим работы от пользователя
     * @param message приглашение для ввода
     * @return выбранный режим (1-3)
     */
    public static int getInputMode(String message) {
        String input;

        while (true) {
            System.out.println(message);
            input = in.nextLine();

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Вы можете ввести только 1, 2, или 3.");
            }
        }
    }

    /**
     * Получает ключ шифрования от пользователя
     * @param message приглашение для ввода
     * @return валидный ключ шифрования
     */
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
                System.out.printf("Вы можете ввести только числа от 1 до %d. \n", LENGTH_ALPHABET);
            }
        }
    }
}
