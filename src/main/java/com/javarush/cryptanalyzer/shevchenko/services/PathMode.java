package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.Scanner;

import static com.javarush.cryptanalyzer.shevchenko.constants.DefaultPath.ENCODED;
import static com.javarush.cryptanalyzer.shevchenko.constants.DefaultPath.INPUT;
import static com.javarush.cryptanalyzer.shevchenko.constants.DefaultPath.OUTPUT;

/**
 * Класс содержит методы для работы с путями файлов.
 */
public class PathMode {

    static Scanner in = new Scanner(System.in);

    /**
     * Получает путь к файлу для шифрования.
     * Если пользователь не вводит путь, используется путь по умолчанию.
     *
     * @param message приглашение для ввода пути
     * @return путь к файлу для шифрования
     */
    public static String getInputFilePathEncode(String message) {
        System.out.println(message);
        String pathInput = in.nextLine();

        if (pathInput.equals("")) {
            pathInput = INPUT;
        }

        return pathInput;
    }

    /**
     * Получает путь для сохранения зашифрованного файла.
     * Если пользователь не вводит путь, используется путь по умолчанию.
     *
     * @param message приглашение для ввода пути
     * @return путь для сохранения зашифрованного файла
     */
    public static String getOutputFilePathEncode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();

        if (pathOutputDecode.equals("")) {
            pathOutputDecode = ENCODED;
        }

        return pathOutputDecode;
    }

    /**
     * Получает путь к файлу для дешифрования.
     * Если пользователь не вводит путь, используется путь по умолчанию.
     *
     * @param message приглашение для ввода пути
     * @return путь к файлу для дешифрования
     */
    public static String getInputFilePathDecode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();

        if (pathOutputDecode.equals("")) {
            pathOutputDecode = ENCODED;
        }

        return pathOutputDecode;
    }

    /**
     * Получает путь для сохранения расшифрованного файла.
     * Если пользователь не вводит путь, используется путь по умолчанию.
     *
     * @param message приглашение для ввода пути
     * @return путь для сохранения расшифрованного файла
     */
    public static String getOutputFilePathDecode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();

        if (pathOutputDecode.equals("")) {
            pathOutputDecode = OUTPUT;
        }

        return pathOutputDecode;
    }
}
