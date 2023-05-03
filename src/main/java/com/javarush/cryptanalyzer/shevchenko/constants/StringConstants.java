package com.javarush.cryptanalyzer.shevchenko.constants;

import static com.javarush.cryptanalyzer.shevchenko.constants.Alphabet.LENGTH_CHARACTERS;

public abstract class StringConstants {

    public static final String TEXT_KEY = "Введите ключ:\n" + "Ключ должен быть целым числом в диапазоне от 1 до " + LENGTH_CHARACTERS + ".\nИли нажмите Enter, чтобы задать ключ по умолчанию.";

    public static final String TEXT_PATH_INPUT_ENCODE = "Введите путь к файлу, который нужно зашифровать.\n" + "Или нажмите Enter, чтобы задать файл по умолчанию.";

    public static final String PATH_INPUT_TXT_DECODE = "Введите путь к файлу, который нужно расшифровать.\n" + "Или нажмите Enter, чтобы задать файл по умолчанию.";

    public static final String TEXT_PATH_OUTPUT_ENCODE = "Введите путь к файлу, в котором сохранится зашифрованный текст.\n" + "Или нажмите Enter, чтобы задать файл по умолчанию.";

    public static final String PATH_OUTPUT_TXT_DECODE = "Введите путь к файлу, в котором сохранится расшифрованный текст.\n" + "Или нажмите Enter, чтобы задать файл по умолчанию.";

    public static final String TEXT_MODE_CHECK = "Введите:\n" + "1, зашифровать текст.\n" + "2, расшифровать текст,вы знаете ключ.\n" + "3, чтобы расшифровать текст с использованием brute force.";

    public static final int DELIMITER_COUNT = 40;

    public static final String DELIMITER = "- ".repeat(DELIMITER_COUNT);

}
