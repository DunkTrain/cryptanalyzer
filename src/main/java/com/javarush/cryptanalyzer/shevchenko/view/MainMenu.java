package com.javarush.cryptanalyzer.shevchenko.view;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputMode;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowBruteForceMenu.bruteForceMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowDecodeMenu.decodeMenu;
import static com.javarush.cryptanalyzer.shevchenko.view.ShowEncodeMenu.encodeMenu;

public class MainMenu {

    //      Главное меню
    public static void mainMenu() {

        welcome();

        int process = getInputMode(ENTER_MODE);

        switch (process) {
            case 1 -> encodeMenu();
            case 2 -> decodeMenu();
            case 3 -> bruteForceMenu();
        }
    }

    //      Приветствие
    public static void welcome() {
        System.out.println("Welcome to \"Caesar Cipher\".");
        System.out.println("This program encrypts and decrypts text using the Caesar cipher algorithm.");
    }
}
