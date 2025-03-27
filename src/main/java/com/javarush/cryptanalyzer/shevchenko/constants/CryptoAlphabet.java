package com.javarush.cryptanalyzer.shevchenko.constants;

/**
 * Класс содержит константы криптографического алфавита.
 */
public final class CryptoAlphabet {

    /**
     * Строка с заглавными буквами русского алфавита.
     */
    private static final String LETTERS_UPPER_CASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    /**
     * Строка со строчными буквами русского алфавита.
     */
    private static final String LETTERS_LOWER_CASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    /**
     * Строка с цифрами.
     */
    private static final String NUMBERS = "0123456789";

    /**
     * Строка со специальными символами.
     */
    private static final String SYMBOLS = ".,\":-!? ";

    /**
     * Полный криптографический алфавит (все символы объединены).
     */
    public static final String ALPHABET = LETTERS_UPPER_CASE + LETTERS_LOWER_CASE + NUMBERS + SYMBOLS;

    /**
     * Длина криптографического алфавита.
     */
    public static final int LENGTH_ALPHABET = ALPHABET.length();

    private CryptoAlphabet() {
        // Запрещаем создавать экземпляр
    }
}
