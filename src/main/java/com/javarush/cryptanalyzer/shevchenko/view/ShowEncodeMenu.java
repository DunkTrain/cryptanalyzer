package com.javarush.cryptanalyzer.shevchenko.view;

import com.javarush.cryptanalyzer.shevchenko.exception.TextEncoderException;
import java.io.*;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.*;
import static com.javarush.cryptanalyzer.shevchenko.services.Mode.getInputKey;
import static com.javarush.cryptanalyzer.shevchenko.services.TextEncoder.encodeText;

public class ShowEncodeMenu {
    public static void encodeMenu(){

        int encryptionKey = getInputKey(ENTER_KEY);

        String inputFilePath = getInputFilePathEncode(ENTER_INPUT_ENCODE_FILEPATH);

        String outputFilePathEncode = getOutputFilePathEncode(ENTER_OUTPUT_ENCODE_FILEPATH);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePathEncode);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))){
            while (bufferedReader.ready()) {
                bufferedWriter.write(encodeText(bufferedReader.readLine(), encryptionKey));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new TextEncoderException("Error while encoding text using the provided key", e);
        }

        System.out.printf("Text encrypted to file %s with key %d.%n", outputFilePathEncode, encryptionKey);
    }

}