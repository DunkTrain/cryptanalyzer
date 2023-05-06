package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.*;

public class TextBruteForce extends TextDecoder {

    private static final String regex = "^([а-яА-Я]+(\\s|,\\s|:\\s|;\\s|-\\s)){2,}";
    private static final Pattern pattern = Pattern.compile(regex);

    public static String bruteForce(String textInput) {
        StringBuilder textOutput = new StringBuilder();
        for (int i = 0; i < LENGTH_ALPHABET; i++) {
            textOutput.delete(0, textOutput.length());
            textOutput.append(decrypt(textInput, i));
            Matcher matcher = pattern.matcher(textOutput);
            if (matcher.find()) {
                break;
            }
        }
        return textOutput.toString();
    }

}
