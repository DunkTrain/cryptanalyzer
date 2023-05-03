package com.javarush.cryptanalyzer.shevchenko.services;


import com.javarush.cryptanalyzer.shevchenko.entity.CaesarCipher;
import com.javarush.cryptanalyzer.shevchenko.repository.FileRepository;

import java.util.Scanner;

public class CipherService {

    // TODO: Refactor this code for better performance.
   /* private final Scanner scanner;
    private final FileRepository fileRepository;

    public CipherService(String inputFileName) {
        this.scanner = new Scanner(System.in);
        this.fileRepository = new FileRepository(inputFileName);
    }

    public void encode() {
        System.out.println("Введите ключ для шифрования:");
        int key = scanner.nextInt();
        fileRepository.encodeToFile(key);
        System.out.println("Готово. Закодированный текст сохранен в " + FileRepository.ENCODED_FILE_NAME);
    }

    public void decode() {
        System.out.println("Введите ключ для декодирования");
        int key = scanner.nextInt();
        fileRepository.decodeToFile(key);
        System.out.println("Готово. Декодированный текст сохранен в " + FileRepository.DECODED_FILE_NAME);
    }

    public void bruteForceDecode() {
        boolean isKeyFound = fileRepository.bruteForceDecodeToFile();
        if (!isKeyFound) {
            System.out.println("Готово. Декодированный текст сохранен в " + FileRepository.DECODED_FILE_NAME);
        }
    }*/
}

