package com.javarush.cryptanalyzer.shevchenko.view;

import java.io.*;
import com.javarush.cryptanalyzer.shevchenko.services.TextBruteForce;
import com.javarush.cryptanalyzer.shevchenko.exception.TextDecoderException;

import static com.javarush.cryptanalyzer.shevchenko.services.PathMode.*;
import static com.javarush.cryptanalyzer.shevchenko.constants.EncryptionTextConstants.*;
import static com.javarush.cryptanalyzer.shevchenko.services.TextBruteForce.bruteForce;

public class ShowBruteForceMenu {
    public static void bruteForceMenu() {

        String pathInput = getInputFilePathDecode(ENTER_INPUT_DECODE_FILEPATH);
        String pathOutputDecode = getOutputFilePathDecode(ENTER_OUTPUT_DECODE_FILEPATH);
        File reader = new File(pathInput);
        File writer = new File(pathOutputDecode);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(reader)));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writer)))) {
            while (bufferedReader.ready()) {
                bufferedWriter.write(bruteForce(bufferedReader.readLine()));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new TextDecoderException("Error while decoding text using brute force method", e);
        }

        System.out.printf("Decrypted text saved in the file %s.%n", pathOutputDecode);
        System.out.printf("Key used for decryption: %d%n", TextBruteForce.getKey());
    }
}
