package com.javarush.cryptanalyzer.shevchenko.repository;

import com.javarush.cryptanalyzer.shevchenko.constants.Alphabet;
import com.javarush.cryptanalyzer.shevchenko.entity.CaesarCipher;

import java.io.*;

public class FileRepository {

    // TODO: Refactor this code for better performance.

   /* public final String inputFileName;

    public FileRepository(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void encodeToFile(int key) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
             PrintStream printStream = new PrintStream(new FileOutputStream(ENCODED_FILE_NAME))) {
            String line;
            CaesarCipher cipher = new CaesarCipher(key);
            while ((line = bufferedReader.readLine()) != null) {
                String encodedLine = cipher.encode(line);
                printStream.println(encodedLine);
            }
        } catch (IOException e) {
            throw new CipherNotFoundException("Входной файл не найден");
        } catch (InvalidInputException e) {
            throw new CipherNotFoundException("Входной файл содержит недопустимый ввод");
        }
    }

    public void decodeToFile(int key) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ENCODED_FILE_NAME));
             PrintStream printStream = new PrintStream(new FileOutputStream(DECODED_FILE_NAME))) {
            String line;
            CaesarCipher cipher = new CaesarCipher(key);
            while ((line = bufferedReader.readLine()) != null) {
                String encodedLine = cipher.decode(line);
                printStream.println(encodedLine);
            }
        } catch (IOException e) {
            throw new CipherNotFoundException("Файл для записи закодированного текста не найден");
        } catch (InvalidInputException e) {
            throw new CipherNotFoundException("Файл с закодированным текстом содержит недопустимый ввод");
        }
    }

    public boolean bruteForceDecodeToFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ENCODED_FILE_NAME))) {
            String line;
            CaesarCipher cipher;
            int tries = 0;
            while ((line = bufferedReader.readLine()) != null) {
                for (int i = 1; i <= Alphabet.ALL_CHARACTERS.length(); i++) {
                    cipher = new CaesarCipher(i);
                    String result = cipher.decode(line);
                    tries++;
                    if (result.contains(" ")) {
                        String[] words = result.split(" ");
                        int count = 0;
                        for (String word : words) {
                            if (word.matches("[А-Яа-я]+")) {
                                count++;
                            }
                        }
                        if (count >= (words.length / 2)) {
                            try (PrintStream printStream = new PrintStream(new FileOutputStream(DECODED_FILE_NAME))) {
                                printStream.println("Ключ найден. Значение ключа: " + i);
                                printStream.println(result);
                                printStream.println("Всего сделано попыток: " + tries);
                                return true;
                            } catch (IOException e) {
                                throw new CipherNotFoundException("Файл не найден");
                            }
                        }
                    }
                }
            }
            try (PrintStream printStream = new PrintStream(new FileOutputStream(DECODED_FILE_NAME))) {
                printStream.println("Ключ не найден");
                printStream.println("Всего сделано попыток: " + tries);
            } catch (IOException e) {
                throw new CipherNotFoundException("Файл не найден");
            }
        } catch (IOException e) {
            throw new CipherNotFoundException("Файл для чтения закодированного текста не найден");
        } catch (InvalidInputException e) {
            throw new CipherNotFoundException("Файл с закодированным текстом содержит недопустимый ввод");
        }
        return false;
    }*/
}
