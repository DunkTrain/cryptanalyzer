package com.javarush.cryptanalyzer.shevchenko.constants;

public abstract class CryptoAlphabet {
    private static final String lettersUpperCase = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String lettersLowerCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String numbers = "0123456789";
    private static final String symbols = ".,\":-!? ";
    public static final String ALPHABET = lettersUpperCase + lettersLowerCase + numbers + symbols;
    public static final int LENGTH_ALPHABET = ALPHABET.length();

}

