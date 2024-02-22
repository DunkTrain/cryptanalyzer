package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.*;

public class TextEncoder {
    public static String encodeText(String inputText, int encryptionKey) {
        StringBuilder encodedText = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            encodedText.append(encryptCharRight(inputText.charAt(i), encryptionKey));
        }
        return encodedText.toString();
    }

    public static char encryptCharRight(char symbol, int key) {
        if (ALPHABET.indexOf(symbol) != -1) {
            return ALPHABET.charAt((ALPHABET.indexOf(symbol) + key) % LENGTH_ALPHABET);
        } else {
            return symbol;
        }
    }
}


