package com.javarush.cryptanalyzer.shevchenko.controllers;

import com.javarush.cryptanalyzer.shevchenko.exception.TextDecoderException;
import com.javarush.cryptanalyzer.shevchenko.exception.TextEncoderException;
import com.javarush.cryptanalyzer.shevchenko.services.TextBruteForce;
import com.javarush.cryptanalyzer.shevchenko.services.TextDecoder;
import com.javarush.cryptanalyzer.shevchenko.services.TextEncoder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML private Button encodeButton;
    @FXML private Button decodeButton;
    @FXML private Button bruteForceButton;
    @FXML private TextField keyField;
    @FXML private TextArea inputTextArea;
    @FXML private TextArea outputTextArea;

    private File inputFile;
    private File outputFile;

    @FXML
    private void initialize() {
        LOGGER.info("Инициализация контроллера");

        keyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                keyField.setText(oldValue);
            }
        });
    }

    @FXML
    private void selectInputFile() {
        LOGGER.info("Выбор входного файла");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для шифрования/дешифрования");
        inputFile = fileChooser.showOpenDialog(null);

        if (inputFile != null) {
            try {
                String content = Files.readString(inputFile.toPath(), StandardCharsets.UTF_8);
                inputTextArea.setText(content);
            } catch (IOException e) {
                showAlert("Ошибка чтения файла", e.getMessage());
            }
        }
    }

    @FXML
    private void handleEncode() {
        LOGGER.info("Обработка кодирования");

        try {
            int key = parseKey();
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите текст для шифрования");
                return;
            }

            String encodedText = encodeTextToFile(inputText, key);
            outputTextArea.setText(encodedText);
        } catch (NumberFormatException | TextEncoderException e) {
            showAlert("Ошибка шифрования", e.getMessage());
        }
    }

    @FXML
    private void handleDecode() {
        LOGGER.info("Обработка декодирования");

        try {
            int key = parseKey();
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            String decodedText = decodeTextFromFile(inputText, key);
            outputTextArea.setText(decodedText);
        } catch (NumberFormatException | TextDecoderException e) {
            showAlert("Ошибка дешифрования", e.getMessage());
        }
    }

    @FXML
    private void handleBruteForce() {
        LOGGER.info("Обработка брут-форса");

        try {
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            TextBruteForce.BruteForceResult result = TextBruteForce.bruteForce(inputText);

            outputTextArea.setText(result.getDecryptedText());
            keyField.setText(String.valueOf(result.getKey()));

            showAlert("Брут-форс", String.format(
                    "Ключ: %d\nУверенность в расшифровке: %.2f%%",
                    result.getKey(),
                    result.getConfidenceScore() * 10
            ));
        } catch (Exception e) {
            showAlert("Ошибка брут-форса", e.getMessage());
        }
    }

    private int parseKey() {
        String keyText = keyField.getText().trim();
        if (keyText.isEmpty()) {
            showAlert("Ошибка", "Введите ключ шифрования");
            throw new NumberFormatException("Пустой ключ");
        }
        return Integer.parseInt(keyText);
    }

    private String encodeTextToFile(String text, int key) {
        try {
            // Генерируем путь для выходного файла
            Path outputPath = Paths.get("encoded.txt");

            // Кодируем каждую строку
            StringBuilder encodedContent = new StringBuilder();
            for (String line : text.split("\n")) {
                String encodedLine = TextEncoder.encodeText(line, key);
                encodedContent.append(encodedLine).append("\n");
            }

            // Сохраняем в файл
            Files.writeString(outputPath, encodedContent.toString(), StandardCharsets.UTF_8);

            return encodedContent.toString();
        } catch (IOException e) {
            throw new TextEncoderException("Ошибка при сохранении зашифрованного текста", e);
        }
    }

    private String decodeTextFromFile(String text, int key) {
        try {
            // Генерируем путь для выходного файла
            Path outputPath = Paths.get("decoded.txt");

            // Декодируем каждую строку
            StringBuilder decodedContent = new StringBuilder();
            for (String line : text.split("\n")) {
                String decodedLine = TextDecoder.decrypt(line, key);
                decodedContent.append(decodedLine).append("\n");
            }

            // Сохраняем в файл
            Files.writeString(outputPath, decodedContent.toString(), StandardCharsets.UTF_8);

            return decodedContent.toString();
        } catch (IOException e) {
            throw new TextDecoderException("Ошибка при сохранении расшифрованного текста", e);
        }
    }

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
