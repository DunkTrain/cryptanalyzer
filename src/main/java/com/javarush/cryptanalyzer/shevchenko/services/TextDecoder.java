package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.ALPHABET;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.LENGTH_ALPHABET;

/**
 * Класс содержит методы для дешифрования текста.
 */
public class TextDecoder {

    /**
     * Дешифрует текст с использованием ключа.
     *
     * @param ciphertext зашифрованный текст.
     * @param key ключ шифрования.
     * @return расшифрованный текст.
     */
    public static String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            plaintext.append(leftOffset(ciphertext.charAt(i), key));
        }

        return plaintext.toString();
    }

    /**
     * Сдвигает символ влево по алфавиту.
     *
     * @param symbol исходный символ.
     * @param key величина сдвига.
     * @return преобразованный символ.
     */
    public static char leftOffset(char symbol, int key) {
        if (ALPHABET.indexOf(symbol) != -1) {
            return ALPHABET.charAt((ALPHABET.indexOf(symbol) + LENGTH_ALPHABET - key) % LENGTH_ALPHABET);
        } else {
            return symbol;
        }
    }
}
