package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.TextEncoderException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_INPUT_ENCODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_KEY;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_OUTPUT_ENCODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputKey;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getInputFilePathEncode;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getOutputFilePathEncode;
import static com.javarush.cryptanalyzer.shevchenko.services.TextEncoder.encodeText;

/**
 * Класс реализует меню шифрования.
 */
public class ShowEncodeMenu {

    /**
     * Отображает меню шифрования и выполняет кодирование.
     */
    public static void encodeMenu() {

        int encryptionKey = getInputKey(ENTER_KEY);

        String inputFilePath = getInputFilePathEncode(ENTER_INPUT_ENCODE_FILEPATH);

        String outputFilePathEncode = getOutputFilePathEncode(ENTER_OUTPUT_ENCODE_FILEPATH);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePathEncode);

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile)));
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputFile)))) {

            while (bufferedReader.ready()) {
                bufferedWriter.write(encodeText(bufferedReader.readLine(), encryptionKey));
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new TextEncoderException("Ошибка при шифровании текста с использованием предоставленного ключа", e);
        }

        System.out.printf("Текст зашифрован и сохранен в файле %s с ключом %d.%n", outputFilePathEncode, encryptionKey);
    }
}
