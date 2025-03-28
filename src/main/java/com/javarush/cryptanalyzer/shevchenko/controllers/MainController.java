package com.javarush.cryptanalyzer.shevchenko.controllers;

import com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet;
import com.javarush.cryptanalyzer.shevchenko.services.TextBruteForce;
import com.javarush.cryptanalyzer.shevchenko.services.TextDecoder;
import com.javarush.cryptanalyzer.shevchenko.services.TextEncoder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

/**
 * Контроллер для основного окна приложения.
 * Обрабатывает пользовательский ввод и управляет шифрованием/дешифрованием текста.
 */
public class MainController {

    @FXML private TextField keyField;
    @FXML private TextArea inputTextArea;
    @FXML private TextArea outputTextArea;
    @FXML private Label hintLabel;

    private static final Random RANDOM = new Random();
    private static final int MAX_KEY_VALUE = CryptoAlphabet.LENGTH_ALPHABET - 1;
    private static final String KEY_RANGE_PROMPT = "Ключ от 1 до " + MAX_KEY_VALUE;

    /**
     * Инициализирует контроллер после загрузки FXML.
     * Настраивает валидацию поля ключа и подсказки.
     */
    @FXML
    private void initialize() {
        configureKeyFieldValidation();
        setupHintLabel();
        keyField.setPromptText(KEY_RANGE_PROMPT);
    }

    private void configureKeyFieldValidation() {
        keyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                keyField.setText(oldValue);
                return;
            }

            if (!newValue.isEmpty()) {
                validateKeyRange(newValue, oldValue);
            }
        });
    }

    private void validateKeyRange(String newValue, String oldValue) {
        try {
            int value = Integer.parseInt(newValue);
            if (value < 1 || value > MAX_KEY_VALUE) {
                keyField.setText(oldValue);
            }
        } catch (NumberFormatException e) {
            keyField.setText(oldValue);
        }
    }

    private void setupHintLabel() {
        hintLabel.setText("Используйте стрелку, чтобы перенести зашифрованный текст. " +
                "Работа всегда идет с текстом из верхней колонки");
        hintLabel.getStyleClass().add("hint-label");
    }

    /**
     * Открывает диалог выбора файла и загружает его содержимое.
     */
    @FXML
    private void selectInputFile() {
        File file = createFileChooser().showOpenDialog(null);
        if (file != null) {
            loadFileContent(file);
        }
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        return fileChooser;
    }

    private void loadFileContent(File file) {
        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            inputTextArea.setText(content);
        } catch (IOException e) {
            showAlert("Ошибка", "Не удалось прочитать файл");
        }
    }

    /**
     * Обработчик кнопки шифрования текста.
     * Считывает текст из поля ввода, шифрует его с указанным ключом
     * и выводит зашифрованный текст в поле вывода.
     * В случае ошибки показывает диалоговое окно с описанием проблемы.
     */
    @FXML
    private void handleEncode() {
        try {
            int key = parseKey(true);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите текст для шифрования");
                return;
            }

            String encodedText = encodeText(inputText, key);
            outputTextArea.setText(encodedText);
            keyField.setText(String.valueOf(key)); // Показываем ключ
        } catch (NumberFormatException e) {
            showAlert("Ошибка шифрования", e.getMessage());
        }
    }

    /**
     * Обработчик кнопки дешифрования текста.
     * Считывает зашифрованный текст из поля ввода, расшифровывает его
     * с указанным ключом и выводит расшифрованный текст в поле вывода.
     * В случае ошибки показывает диалоговое окно с описанием проблемы.
     */
    @FXML
    private void handleDecode() {
        try {
            int key = parseKey(false);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            String decodedText = decodeText(inputText, key);
            outputTextArea.setText(decodedText);
        } catch (NumberFormatException e) {
            showAlert("Ошибка дешифрования", e.getMessage());
        }
    }

    /**
     * Обработчик кнопки brute-force (подбор ключа)
     * Пытается автоматически расшифровать текст, подбирая наиболее вероятный ключ.
     * Выводит расшифрованный текст, устанавливает найденный ключ
     * и показывает диалоговое окно с результатами подбора.
     */
    @FXML
    private void handleBruteForce() {
        try {
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            TextBruteForce.BruteForceResult result = TextBruteForce.bruteForce(inputText);

            outputTextArea.setText(result.getDecryptedText());
            keyField.setText(String.valueOf(result.getKey()));

            showAlert("Brute-force", String.format(
                    "Ключ: %d\nУверенность в расшифровке: %.2f%%",
                    result.getKey(),
                    result.getConfidenceScore() * 10
            ));
        } catch (Exception e) {
            showAlert("Ошибка brute-force", e.getMessage());
        }
    }

    /**
     * Парсинг ключа шифрования из текстового поля.
     * Если ключ не введён и это шифрование, генерирует случайный ключ.
     *
     * @param allowRandom если true, генерирует случайный ключ при пустом поле
     * @return Целочисленное значение ключа шифрования
     * @throws NumberFormatException если ключ некорректен или обязателен, но не введён
     */
    private int parseKey(boolean allowRandom) {
        String keyText = keyField.getText().trim();
        if (keyText.isEmpty()) {
            if (allowRandom) {
                return RANDOM.nextInt(MAX_KEY_VALUE) + 1;
            } else {
                showAlert("Ошибка", "Введите ключ для дешифрования (от 1 до " + MAX_KEY_VALUE + ")");
                throw new NumberFormatException("Пустой ключ");
            }
        }
        return Integer.parseInt(keyText);
    }

    private String encodeText(String text, int key) {
        StringBuilder encodedContent = new StringBuilder();
        for (String line : text.split("\n")) {
            String encodedLine = TextEncoder.encodeText(line, key);
            encodedContent.append(encodedLine).append("\n");
        }
        return encodedContent.toString().trim();
    }

    private String decodeText(String text, int key) {
        StringBuilder decodedContent = new StringBuilder();
        for (String line : text.split("\n")) {
            String decodedLine = TextDecoder.decrypt(line, key);
            decodedContent.append(decodedLine).append("\n");
        }
        return decodedContent.toString().trim();
    }

    /**
     * Отображение информационного диалогового окна.
     * Выполняется в потоке JavaFX для корректной работы с интерфейсом.
     *
     * @param title Заголовок диалогового окна
     * @param content Текст сообщения
     */
    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    @FXML
    private void copyOutputToInput() {
        String outputText = outputTextArea.getText();
        if (!outputText.isEmpty()) {
            inputTextArea.setText(outputText);
            outputTextArea.clear();
        }
    }
}
