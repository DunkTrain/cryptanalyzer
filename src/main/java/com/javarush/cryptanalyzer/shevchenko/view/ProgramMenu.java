package com.javarush.cryptanalyzer.shevchenko.view;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputMode;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowBruteForceMenu.bruteForceMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowDecodeMenu.decodeMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowEncodeMenu.encodeMenu;

public class ProgramMenu {

    public static void presentProgramMenu() {

        selectInputMode();

        int start = getInputMode(ENTER_MODE);

        switch (start) {
            case 1 -> encodeMenu();
            case 2 -> decodeMenu();
            case 3 -> bruteForceMenu();
        }
    }

    public static void selectInputMode() {
        System.out.println(DESCRIPTION);
    }
}
