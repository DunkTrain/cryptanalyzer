package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.DecryptException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import static com.javarush.cryptanalyzer.shevchenko.constants.StringConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.BruteForceDecode.bruteForce;
import static com.javarush.cryptanalyzer.shevchenko.services.InputValidator.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Modes.*;

public class MenuCracker {
    //TODO Implement encryption and decryption menu
    public static void bruteForceMenu(){
        // Получаем путь к файлу с зашифрованным текстом
        String pathInput = getPathInputDecode(PATH_INPUT_TXT_DECODE);

        // Получаем путь к файлу, куда сохраняем расшифрованный текст
        String pathOutputDecode = getPathOutputDecode(PATH_OUTPUT_TXT_DECODE);

        // Читаем из файла и записываем в другой файл в процессе расшифровки
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathInput), Charset.forName("UTF-8"));
            List<String> decryptedLines = new ArrayList<>();
            for(String line : lines) {
                decryptedLines.add(bruteForce(line));
            }
            Files.write(Paths.get(pathOutputDecode), decryptedLines, Charset.forName("UTF-8"));
        } catch (IOException e) { // Отлавливаем исключение при чтении/записи файла
            throw new DecryptException("Ошибка чтения/записи файла", e);
        }
        // Выводим сообщение о завершении операции расшифровки
        System.out.printf("Текст расшифрован в файл %s.%n", pathOutputDecode);
        System.out.println(DELIMITER);
    }

}
