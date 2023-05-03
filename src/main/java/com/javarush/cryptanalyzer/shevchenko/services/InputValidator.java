package com.javarush.cryptanalyzer.shevchenko.services;

import static com.javarush.cryptanalyzer.shevchenko.constants.FileConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Modes.in;

public class InputValidator {
    public static String getPathInputEncode(String promptMessage) {
        System.out.println(promptMessage);
        String inputPath = in.nextLine();
        if (inputPath.equals("")) {
            inputPath = INPUT_TXT;
        }
        return inputPath;
    }

    public static String getPathOutputEncode(String promptMessage) {
        System.out.println(promptMessage);
        String outputPath = in.nextLine();
        if (outputPath.equals("")) {
            outputPath = ENCODED_TXT;
        }
        return outputPath;
    }

    public static String getPathInputDecode(String promptMessage) {
        System.out.println(promptMessage);
        String inputPath = in.nextLine();
        if (inputPath.equals("")) {
            inputPath = ENCODED_TXT;
        }
        return inputPath;
    }

    public static String getPathOutputDecode(String promptMessage) {
        System.out.println(promptMessage);
        String outputPath = in.nextLine();
        if (outputPath.equals("")) {
            outputPath = OUTPUT_TXT;
        }
        return outputPath;
    }
}
