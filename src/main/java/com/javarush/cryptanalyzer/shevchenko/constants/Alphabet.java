package com.javarush.cryptanalyzer.shevchenko.constants;

public abstract class Alphabet {
    public static final String UPPER_CASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String LOWER_CASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static final String DIGITS = "0123456789";
    public static final String SYMBOLS = ".,\":-!? ";
    public static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SYMBOLS;

    public static final int LENGTH_CHARACTERS = ALL_CHARACTERS.length();

}
