package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.javarush.cryptanalyzer.shevchenko.constants.Alphabet.LENGTH_CHARACTERS;

public class BruteForceDecode {
    private static final String REGEX = "^([а-яА-Я-]+[,:;]?\\s){2,}";

    public static String bruteForce(String inputTxt) {
        String outputTxt = "";
        for (int i = 0; i < LENGTH_CHARACTERS; i++) {
            outputTxt = Decode.decode(inputTxt, i);
            Matcher matcher = Pattern.compile(REGEX).matcher(outputTxt);
            if (matcher.find()) {
                break;
            }
        }
        return outputTxt;
    }
}
