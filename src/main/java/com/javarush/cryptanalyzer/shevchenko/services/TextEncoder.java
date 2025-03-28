package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.ALPHABET;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.LENGTH_ALPHABET;

/**
 * Класс содержит методы для шифрования текста.
 */
public class TextEncoder {

    /**
     * Шифрует текст с использованием ключа.
     *
     * @param inputText исходный текст.
     * @param encryptionKey ключ шифрования.
     * @return зашифрованный текст.
     */
    public static String encodeText(String inputText, int encryptionKey) {
        StringBuilder encodedText = new StringBuilder();

        for (int i = 0; i < inputText.length(); i++) {
            encodedText.append(encryptCharRight(inputText.charAt(i), encryptionKey));
        }

        return encodedText.toString();
    }

    /**
     * Сдвигает символ вправо по алфавиту.
     *
     * @param symbol исходный символ
     * @param key величина сдвига
     * @return преобразованный символ
     */
    public static char encryptCharRight(char symbol, int key) {
        if (ALPHABET.indexOf(symbol) != -1) {
            return ALPHABET.charAt((ALPHABET.indexOf(symbol) + key) % LENGTH_ALPHABET);
        } else {
            return symbol;
        }
    }
}
