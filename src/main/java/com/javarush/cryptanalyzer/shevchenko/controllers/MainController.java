package com.javarush.cryptanalyzer.shevchenko.controllers;




public class MainController {

    // TODO: Refactor this code for better performance.
    /*private final Scanner scanner;

    public MainController() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Выберите операцию:");
        System.out.println("1 - кодировать");
        System.out.println("2 - декодировать");
        System.out.println("3 - декодировать методом перебора");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                encode();
                break;

            case 2:
                decode();
                break;

            case 3:
                bruteForceDecode();
                break;

            default:
                System.out.println("Недопустимый выбор");
                break;
        }
    }

    private void encode() {
        System.out.println("Введите имя файла для кодирования:");
        String inputFileName = scanner.nextLine();
        CipherService cipherService = new CipherService(inputFileName);
        cipherService.encode();
    }

    private void decode() {
        System.out.println("Введите имя файла для декодирования:");
        String inputFileName = scanner.nextLine();
        CipherService cipherService = new CipherService(inputFileName);
        cipherService.decode();
    }

    private void bruteForceDecode() {
        System.out.println("Введите имя файла для декодирования:");
        String inputFileName = scanner.nextLine();
        CipherService cipherService = new CipherService(inputFileName);
        cipherService.bruteForceDecode();
    }*/ //
}
