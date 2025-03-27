package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.TextDecoderException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_INPUT_DECODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_KEY;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.ENTER_OUTPUT_DECODE_FILEPATH;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputKey;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getInputFilePathDecode;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.getOutputFilePathDecode;
import static com.javarush.cryptanalyzer.shevchenko.services.TextDecoder.decrypt;

/**
 * Класс реализует меню обычного дешифрования.
 */
public class ShowDecodeMenu {

    /**
     * Отображает меню дешифрования и выполняет расшифровку.
     */
    public static void decodeMenu(){

        int key = getInputKey(ENTER_KEY);

        String pathInput = getInputFilePathDecode(ENTER_INPUT_DECODE_FILEPATH);

        String pathOutputDecode = getOutputFilePathDecode(ENTER_OUTPUT_DECODE_FILEPATH);

        File reader = new File(pathInput);
        File writer = new File(pathOutputDecode);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(reader)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writer)))) {

            while (bufferedReader.ready()) {
                bufferedWriter.write(decrypt(bufferedReader.readLine(), key));
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new TextDecoderException("Ошибка при дешифровании текста с использованием предоставленного ключа", e);
        }

        System.out.printf("Текст расшифрован и сохранен в файле %s с ключом %d.%n", pathOutputDecode, key);
    }
}
