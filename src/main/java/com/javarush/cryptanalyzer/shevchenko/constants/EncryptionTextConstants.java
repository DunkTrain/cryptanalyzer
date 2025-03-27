package com.javarush.cryptanalyzer.shevchenko.constants;

/**
 * Класс содержит секстовые константы для взаимодействия с пользователем.
 */
public final class EncryptionTextConstants {

    /**
     * Основное описание программы
     */
    public static final String DESCRIPTION = """
        Добро пожаловать в программу "Шифр Цезаря".
        
        Шифр Цезаря был изобретен в Древнем Риме и назван в честь Юлия Цезаря, 
        хотя сам Цезарь не был его фактическим изобретателем.
        
        Основные возможности:
        - Шифрование текста с заданным ключом
        - Дешифрование с известным ключом
        - Автоматическое дешифрование методом перебора (Brute Force)
        
        Используемый алфавит: русские буквы, цифры и основные знаки препинания.
        """;

    /**
     * Константы для выбора режима работы
     */
    public static final String ENTER_MODE = """
            Выберите режим работы программы:
            1. Шифрование текста
            2. Дешифрование с ключом
            3. Автоматическое дешифрование (Brute Force)
            
            Введите номер выбранного режима:
            """;

    /**
     * Константы для ввода ключа шифрования
     */
    public static final String ENTER_KEY = """
            Введите ключ шифрования:
            Нажмите Enter, чтобы использовать ключ по умолчанию.
            """;

    /**
     * Константы для ввода путей к файлам для шифрования
     */
    public static final String ENTER_INPUT_ENCODE_FILEPATH = """
            Введите путь к файлу для шифрования:
            Нажмите Enter, чтобы использовать путь по умолчанию.
            """;

    public static final String ENTER_OUTPUT_ENCODE_FILEPATH = """
            Введите путь для сохранения зашифрованного файла:
            Нажмите Enter, чтобы использовать путь по умолчанию.
            """;

    /**
     * Константы для ввода путей к файлам для дешифрования
     */
    public static final String ENTER_INPUT_DECODE_FILEPATH = """
            Введите путь к файлу для дешифрования:
            Нажмите Enter, чтобы использовать путь по умолчанию.""";

    public static final String ENTER_OUTPUT_DECODE_FILEPATH = """
            Введите путь для сохранения расшифрованного файла:
            Нажмите Enter, чтобы использовать путь по умолчанию.""";

    private EncryptionTextConstants() {
        // Запрещаем создавать экземпляр
    }
}
