package com.javarush.cryptanalyzer.shevchenko.view;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputMode;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowBruteForceMenu.bruteForceMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowDecodeMenu.decodeMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowEncodeMenu.encodeMenu;

public class ProgramMenu {

    public static void programMenu() {

        welcome();

        int process = getInputMode(ENTER_MODE);

        switch (process) {
            case 1 -> encodeMenu();
            case 2 -> decodeMenu();
            case 3 -> bruteForceMenu();
        }
    }

    public static void welcome() {
        System.out.println(DESCRIPTION);
    }
}
