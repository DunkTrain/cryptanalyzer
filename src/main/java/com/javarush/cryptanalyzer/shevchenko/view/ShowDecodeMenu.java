package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.TextDecoderException;

import java.io.*;

import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.TextDecoder.decrypt;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputKey;

public class ShowDecodeMenu {
    public static void decodeMenu(){

        int key = getInputKey(ENTER_KEY);

        String pathInput = getInputFilePathDecode(ENTER_INPUT_DECODE_FILEPATH);

        String pathOutputDecode = getOutputFilePathDecode(ENTER_OUTPUT_DECODE_FILEPATH);

        File reader = new File(pathInput);
        File writer = new File(pathOutputDecode);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(reader)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writer)))){
            while (bufferedReader.ready()) {
                bufferedWriter.write(decrypt(bufferedReader.readLine(), key));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new TextDecoderException("Error while decoding text using the provided key", e);
        }

        System.out.printf("Text decrypted to file %s with key %d.%n", pathOutputDecode, key);
    }

}