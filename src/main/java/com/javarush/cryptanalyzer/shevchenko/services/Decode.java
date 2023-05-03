package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.Alphabet.ALL_CHARACTERS;
import static com.javarush.cryptanalyzer.shevchenko.constants.Alphabet.LENGTH_CHARACTERS;

public class Decode {
    public static String decode(String input, int key) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(charLeftOffset(c, key));
        }
        return output.toString();
    }

    public static char charLeftOffset(char c, int key) {
        int index = ALL_CHARACTERS.indexOf(Character.toLowerCase(c));
        if (index >= 0) {
            int newIndex = (index - key) % LENGTH_CHARACTERS;
            return ALL_CHARACTERS.charAt(newIndex);
        } else {
            return c;
        }
    }
}
