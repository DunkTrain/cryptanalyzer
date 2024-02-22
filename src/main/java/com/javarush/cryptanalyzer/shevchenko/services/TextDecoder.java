package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.*;

public class TextDecoder {

    public static String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            plaintext.append(leftOffset(ciphertext.charAt(i), key));
        }
        return plaintext.toString();
    }

        public static char leftOffset(char symbol, int key) {
        if (ALPHABET.indexOf(symbol) != -1) {
            return ALPHABET.charAt((ALPHABET.indexOf(symbol) + LENGTH_ALPHABET - key) % LENGTH_ALPHABET);
        } else {
            return symbol;
        }
    }

}
