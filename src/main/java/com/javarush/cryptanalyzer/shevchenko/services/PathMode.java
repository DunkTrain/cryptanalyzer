package com.javarush.cryptanalyzer.shevchenko.services;

import java.util.Scanner;
import static com.javarush.cryptanalyzer.shevchenko.constants.DefaultPath.*;


public class PathMode {
    static Scanner in = new Scanner(System.in);

    public static String getInputFilePathEncode(String message) {
        System.out.println(message);
        String pathInput = in.nextLine();
        if (pathInput.equals("")) {
            pathInput = INPUT;
        }
        return pathInput;
    }
    public static String getOutputFilePathEncode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();
        if (pathOutputDecode.equals("")) {
            pathOutputDecode = ENCODED;
        }
        return pathOutputDecode;
    }

    public static String getInputFilePathDecode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();
        if (pathOutputDecode.equals("")) {
            pathOutputDecode = ENCODED;
        }
        return pathOutputDecode;
    }
    public static String getOutputFilePathDecode(String message) {
        System.out.println(message);
        String pathOutputDecode = in.nextLine();
        if (pathOutputDecode.equals("")) {
            pathOutputDecode = OUTPUT;
        }
        return pathOutputDecode;
    }
}