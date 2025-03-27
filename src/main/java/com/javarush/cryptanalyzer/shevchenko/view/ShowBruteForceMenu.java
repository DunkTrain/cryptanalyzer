package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.TextDecoderException;
import com.javarush.cryptanalyzer.shevchenko.services.TextBruteForce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_INPUT_DECODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_OUTPUT_DECODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getInputFilePathDecode;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getOutputFilePathDecode;

/**
 * Класс реализует меню bruteforce-дешифрования.
 */
public class ShowBruteForceMenu {

    /**
     * Отображает меню bruteforce-режима и выполняет дешифрование.
     */
    public static void bruteForceMenu() {
        String pathInput = getInputFilePathDecode(ENTER_INPUT_DECODE_FILEPATH);
        String pathOutputDecode = getOutputFilePathDecode(ENTER_OUTPUT_DECODE_FILEPATH);

        File reader = new File(pathInput);
        File writer = new File(pathOutputDecode);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(reader)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writer)))) {

            StringBuilder fullText = new StringBuilder();
            while (bufferedReader.ready()) {
                fullText.append(bufferedReader.readLine()).append("\n");
            }

            // Используем новый метод с расширенной статистикой
            TextBruteForce.BruteForceResult result =
                    TextBruteForce.bruteForce(fullText.toString());

            bufferedWriter.write(result.getDecryptedText());

            System.out.printf("Расшифрованный текст сохранен в файле %s.%n", pathOutputDecode);
            System.out.printf("Использованный ключ для дешифрования: %d%n", result.getKey());
            System.out.printf("Уверенность в расшифровке: %.2f%%%n", result.getConfidenceScore() * 10);

        } catch (IOException e) {
            throw new TextDecoderException("Ошибка при дешифровании текста методом перебора (brute force)", e);
        }
    }
}
