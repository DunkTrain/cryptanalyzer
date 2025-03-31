package ru.cooper.cryptanalyzer.controllers.ui;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;
import ru.cooper.cryptanalyzer.core.TextBruteForce;
import ru.cooper.cryptanalyzer.core.TextDecoder;
import ru.cooper.cryptanalyzer.core.TextEncoder;
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
    @FXML private Button encodeButton;
    @FXML private Button decodeButton;
    @FXML private Button bruteForceButton;
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;

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
        setupStatusLabel();
        keyField.setPromptText(KEY_RANGE_PROMPT);

        // Добавляем обработчики событий для текстовых полей
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasText = !newValue.trim().isEmpty();
            encodeButton.setDisable(!hasText);
            decodeButton.setDisable(!hasText);
            bruteForceButton.setDisable(!hasText);
        });

        // Инициализируем состояние кнопок
        encodeButton.setDisable(true);
        decodeButton.setDisable(true);
        bruteForceButton.setDisable(true);

        // Инициализируем прогресс-бар
        progressBar.setVisible(false);
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
                showStatus("Ключ должен быть числом от 1 до " + MAX_KEY_VALUE, true);
            } else {
                showStatus("", false);
            }
        } catch (NumberFormatException e) {
            keyField.setText(oldValue);
        }
    }

    private void setupHintLabel() {
        hintLabel.setText(
                "Используйте стрелку, чтобы перенести зашифрованный текст в поле ввода для дальнейшей обработки"
        );
        hintLabel.getStyleClass().add("hint-label");
    }

    private void setupStatusLabel() {
        statusLabel.setText("");
        statusLabel.getStyleClass().add("status-label");
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);

        if (isError) {
            statusLabel.getStyleClass().removeAll("status-success");
            statusLabel.getStyleClass().add("status-error");
        } else if (!message.isEmpty()) {
            statusLabel.getStyleClass().removeAll("status-error");
            statusLabel.getStyleClass().add("status-success");
        }
    }

    @FXML
    private void selectInputFile() {
        FileChooser fileChooser = createFileChooser();
        File file = fileChooser.showOpenDialog(inputTextArea.getScene().getWindow());
        if (file != null) {
            loadFileContent(file);
        }
    }

    @FXML
    private void saveOutputFile() {
        String outputText = outputTextArea.getText();
        if (outputText.isEmpty()) {
            showAlert("Ошибка", "Нет текста для сохранения");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить результат");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt")
        );

        File file = fileChooser.showSaveDialog(outputTextArea.getScene().getWindow());
        if (file != null) {
            saveTextToFile(outputText, file);
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
            showStatus("Файл успешно сохранен: " + file.getName(), false);
        } catch (IOException e) {
            showAlert("Ошибка", "Не удалось сохранить файл: " + e.getMessage());
        }
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt")
        );
        return fileChooser;
    }

    private void loadFileContent(File file) {
        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            inputTextArea.setText(content);
            showStatus("Файл успешно загружен: " + file.getName(), false);
        } catch (IOException e) {
            showAlert("Ошибка", "Не удалось прочитать файл: " + e.getMessage());
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
            disableControls(true);
            progressBar.setVisible(true);

            int key = parseKey(true);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите текст для шифрования");
                return;
            }

            Task<String> encodeTask = new Task<>() {
                @Override
                protected String call() {
                    return encodeText(inputText, key);
                }
            };

            encodeTask.setOnSucceeded(event -> {
                String encodedText = encodeTask.getValue();
                outputTextArea.setText(encodedText);
                keyField.setText(String.valueOf(key));
                showStatus("Текст успешно зашифрован с ключом " + key, false);
                disableControls(false);
                progressBar.setVisible(false);
            });

            encodeTask.setOnFailed(event -> {
                showAlert("Ошибка шифрования", encodeTask.getException().getMessage());
                disableControls(false);
                progressBar.setVisible(false);
            });

            new Thread(encodeTask).start();

        } catch (NumberFormatException e) {
            showAlert("Ошибка шифрования", e.getMessage());
            disableControls(false);
            progressBar.setVisible(false);
        }
    }

    @FXML
    private void handleDecode() {
        try {
            disableControls(true);
            progressBar.setVisible(true);

            int key = parseKey(false);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            Task<String> decodeTask = new Task<>() {
                @Override
                protected String call() {
                    return decodeText(inputText, key);
                }
            };

            decodeTask.setOnSucceeded(event -> {
                String decodedText = decodeTask.getValue();
                outputTextArea.setText(decodedText);
                showStatus("Текст успешно дешифрован с ключом " + key, false);
                disableControls(false);
                progressBar.setVisible(false);
            });

            decodeTask.setOnFailed(event -> {
                showAlert("Ошибка дешифрования", decodeTask.getException().getMessage());
                disableControls(false);
                progressBar.setVisible(false);
            });

            new Thread(decodeTask).start();

        } catch (NumberFormatException e) {
            showAlert("Ошибка дешифрования", e.getMessage());
            disableControls(false);
            progressBar.setVisible(false);
        }
    }

    @FXML
    private void handleBruteForce() {
        try {
            disableControls(true);
            progressBar.setVisible(true);

            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                showAlert("Ошибка", "Введите зашифрованный текст");
                return;
            }

            Task<TextBruteForce.BruteForceResult> bruteForceTask = new Task<>() {
                @Override
                protected TextBruteForce.BruteForceResult call() {
                    return TextBruteForce.bruteForce(inputText);
                }
            };

            bruteForceTask.setOnSucceeded(event -> {
                TextBruteForce.BruteForceResult result = bruteForceTask.getValue();
                outputTextArea.setText(result.getDecryptedText());
                keyField.setText(String.valueOf(result.getKey()));

                String message = String.format(
                        "Ключ: %d\nУверенность в расшифровке: %.2f%%",
                        result.getKey(),
                        result.getConfidenceScore() * 10
                );

                if (result.hasMessage()) {
                    message += "\n" + result.getMessage();
                }

                showAlert("Brute-force", message);
                showStatus("Брутфорс завершен. Найден ключ: " + result.getKey(), false);
                disableControls(false);
                progressBar.setVisible(false);
            });

            bruteForceTask.setOnFailed(event -> {
                showAlert("Ошибка brute-force", bruteForceTask.getException().getMessage());
                disableControls(false);
                progressBar.setVisible(false);
            });

            new Thread(bruteForceTask).start();

        } catch (Exception e) {
            showAlert("Ошибка brute-force", e.getMessage());
            disableControls(false);
            progressBar.setVisible(false);
        }
    }

    private void disableControls(boolean disable) {
        encodeButton.setDisable(disable);
        decodeButton.setDisable(disable);
        bruteForceButton.setDisable(disable);
        keyField.setDisable(disable);
        inputTextArea.setDisable(disable);
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
                throw new NumberFormatException("Введите ключ для дешифрования (от 1 до " + MAX_KEY_VALUE + ")");
            }
        }
        try {
            return Integer.parseInt(keyText);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ключ должен быть числом");
        }
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
            showStatus("Текст перемещен в поле ввода", false);
        }
    }

    @FXML
    private void clearAllFields() {
        inputTextArea.clear();
        outputTextArea.clear();
        keyField.clear();
        showStatus("Все поля очищены", false);
    }

    @FXML
    private void showHelp() {
        showAlert("Справка",
                "Криптоанализатор - приложение для шифрования и дешифрования текста методом Цезаря.\n\n" +
                        "Порядок работы:\n" +
                        "1. Введите текст или загрузите файл\n" +
                        "2. Укажите ключ шифрования (число от 1 до " + MAX_KEY_VALUE + ")\n" +
                        "3. Выберите операцию (шифрование, дешифрование или взлом)\n\n" +
                        "При шифровании можно оставить поле ключа пустым - будет сгенерирован случайный ключ.\n" +
                        "Для брутфорса ключ указывать не требуется, приложение автоматически найдет наиболее вероятный ключ."
        );
    }
}
