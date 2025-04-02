package ru.cooper.cryptanalyzer.controllers.ui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import ru.cooper.cryptanalyzer.controllers.ui.helpers.AlertHelper;
import ru.cooper.cryptanalyzer.controllers.ui.helpers.FileHelper;
import ru.cooper.cryptanalyzer.controllers.ui.tasks.BruteForceTask;
import ru.cooper.cryptanalyzer.controllers.ui.tasks.DecodeTask;
import ru.cooper.cryptanalyzer.controllers.ui.tasks.EncodeTask;
import ru.cooper.cryptanalyzer.controllers.ui.validators.KeyValidator;
import ru.cooper.cryptanalyzer.core.TextBruteForce;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

import java.io.File;
import java.util.Random;

/**
 * Контроллер основного окна приложения.
 * Управляет логикой шифрования, дешифрования и взлома текста методом Цезаря.
 */
public class MainController {

    @FXML
    private TextField keyField;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Label hintLabel;

    @FXML
    private Button encodeButton;

    @FXML
    private Button decodeButton;

    @FXML
    private Button bruteForceButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    private static final Random RANDOM = new Random();
    private static final int MAX_KEY_VALUE = CryptoAlphabet.LENGTH_ALPHABET - 1;
    private static final String KEY_RANGE_PROMPT = "Ключ от 1 до " + MAX_KEY_VALUE;

    private final KeyValidator keyValidator;
    private final FileHelper fileHelper;
    private final AlertHelper alertHelper;

    /**
     * Конструктор контроллера.
     * Инициализирует валидатор ключа, помощник для работы с файлами и уведомлениями.
     */
    public MainController() {
        this.keyValidator = new KeyValidator(MAX_KEY_VALUE);
        this.fileHelper = new FileHelper();
        this.alertHelper = new AlertHelper();
    }

    /**
     * Инициализация JavaFX-компонентов после загрузки FXML.
     * Настраивает подсказки, стили и валидацию полей.
     */
    @FXML
    private void initialize() {
        setupUIComponents();
        setupEventHandlers();
    }

    private void setupUIComponents() {
        hintLabel.setText(
                "Используйте стрелку, чтобы перенести зашифрованный текст в поле ввода для дальнейшей обработки"
        );
        hintLabel.getStyleClass().add("hint-label");

        statusLabel.setText("");
        statusLabel.getStyleClass().add("status-label");

        keyField.setPromptText(KEY_RANGE_PROMPT);

        encodeButton.setDisable(true);
        decodeButton.setDisable(true);
        bruteForceButton.setDisable(true);

        progressBar.setVisible(false);
    }

    private void setupEventHandlers() {
        keyField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                keyValidator.validate(newValue, oldValue);
                showStatus("", false);
            } catch (IllegalArgumentException e) {
                keyField.setText(oldValue);
                showStatus(e.getMessage(), true);
            }
        });

        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasText = !newValue.trim().isEmpty();
            encodeButton.setDisable(!hasText);
            decodeButton.setDisable(!hasText);
            bruteForceButton.setDisable(!hasText);
        });
    }

    /**
     * Открывает диалоговое окно для выбора файла с текстом.
     * Загружает содержимое файла в поле ввода.
     */
    @FXML
    private void selectInputFile() {
        FileChooser fileChooser = fileHelper.createTextFileChooser("Выберите файл");
        File file = fileChooser.showOpenDialog(inputTextArea.getScene().getWindow());

        if (file != null) {
            try {
                String content = fileHelper.readTextFromFile(file);
                inputTextArea.setText(content);
                showStatus("Файл успешно загружен: " + file.getName(), false);
            } catch (Exception e) {
                alertHelper.showAlert("Ошибка", "Не удалось прочитать файл: " + e.getMessage());
            }
        }
    }

    /**
     * Открывает диалоговое окно для сохранения результата в файл.
     * Сохраняет содержимое поля вывода в выбранный файл.
     */
    @FXML
    private void saveOutputFile() {
        String outputText = outputTextArea.getText();
        if (outputText.isEmpty()) {
            alertHelper.showAlert("Ошибка", "Нет текста для сохранения");
            return;
        }

        FileChooser fileChooser = fileHelper.createTextFileChooser("Сохранить результат");
        File file = fileChooser.showSaveDialog(outputTextArea.getScene().getWindow());

        if (file != null) {
            try {
                fileHelper.saveTextToFile(outputText, file);
                showStatus("Файл успешно сохранен: " + file.getName(), false);
            } catch (Exception e) {
                alertHelper.showAlert("Ошибка", "Не удалось сохранить файл: " + e.getMessage());
            }
        }
    }

    /**
     * Обрабатывает нажатие кнопки "Зашифровать".
     * Шифрует текст из поля ввода с указанным (или случайным) ключом.
     */
    @FXML
    private void handleEncode() {
        try {
            disableControls(true);
            progressBar.setVisible(true);

            int key = parseKey(true);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                alertHelper.showAlert("Ошибка", "Введите текст для шифрования");
                resetControlsState();
                return;
            }

            EncodeTask encodeTask = new EncodeTask(inputText, key);
            setupTaskHandlers(encodeTask, "Текст успешно зашифрован с ключом " + key, "Ошибка шифрования");

            encodeTask.setOnSucceeded(event -> {
                String encodedText = encodeTask.getValue();
                outputTextArea.setText(encodedText);
                keyField.setText(String.valueOf(key));
                showStatus("Текст успешно зашифрован с ключом " + key, false);
                resetControlsState();
            });

            new Thread(encodeTask).start();

        } catch (NumberFormatException e) {
            alertHelper.showAlert("Ошибка шифрования", e.getMessage());
            resetControlsState();
        }
    }

    /**
     * Обрабатывает нажатие кнопки "Дешифровать".
     * Дешифрует текст из поля ввода с указанным ключом.
     */
    @FXML
    private void handleDecode() {
        try {
            disableControls(true);
            progressBar.setVisible(true);

            int key = parseKey(false);
            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                alertHelper.showAlert("Ошибка", "Введите зашифрованный текст");
                resetControlsState();
                return;
            }

            DecodeTask decodeTask = new DecodeTask(inputText, key);
            setupTaskHandlers(decodeTask, "Текст успешно дешифрован с ключом " + key, "Ошибка дешифрования");

            new Thread(decodeTask).start();

        } catch (NumberFormatException e) {
            alertHelper.showAlert("Ошибка дешифрования", e.getMessage());
            resetControlsState();
        }
    }

    /**
     * Обрабатывает нажатие кнопки "Brute-force".
     * Автоматически подбирает ключ для зашифрованного текста.
     */
    @FXML
    private void handleBruteForce() {
        try {
            disableControls(true);
            progressBar.setVisible(true);

            String inputText = inputTextArea.getText();

            if (inputText.isEmpty()) {
                alertHelper.showAlert("Ошибка", "Введите зашифрованный текст");
                resetControlsState();
                return;
            }

            BruteForceTask bruteForceTask = new BruteForceTask(inputText);

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

                alertHelper.showAlert("Brute-force", message);
                showStatus("Brute-force завершен. Найден ключ: " + result.getKey(), false);
                resetControlsState();
            });

            bruteForceTask.setOnFailed(event -> {
                alertHelper.showAlert("Ошибка brute-force", bruteForceTask.getException().getMessage());
                resetControlsState();
            });

            new Thread(bruteForceTask).start();

        } catch (Exception e) {
            alertHelper.showAlert("Ошибка brute-force", e.getMessage());
            resetControlsState();
        }
    }

    /**
     * Настраивает обработчики событий для асинхронной задачи.
     *
     * @param task           задача (шифрование/дешифрование)
     * @param successMessage сообщение при успешном выполнении
     * @param errorTitle     заголовок ошибки при сбое
     */
    private <T> void setupTaskHandlers(Task<T> task, String successMessage, String errorTitle) {
        task.setOnSucceeded(event -> {
            outputTextArea.setText(task.getValue().toString());
            showStatus(successMessage, false);
            resetControlsState();
        });

        task.setOnFailed(event -> {
            alertHelper.showAlert(errorTitle, task.getException().getMessage());
            resetControlsState();
        });
    }

    private void resetControlsState() {
        disableControls(false);
        progressBar.setVisible(false);
    }

    private void disableControls(boolean disable) {
        encodeButton.setDisable(disable);
        decodeButton.setDisable(disable);
        bruteForceButton.setDisable(disable);
        keyField.setDisable(disable);
        inputTextArea.setDisable(disable);
    }

    /**
     * Парсит ключ из текстового поля.
     * Если поле пустое и разрешена генерация случайного ключа, создает его.
     *
     * @param allowRandom разрешена ли генерация случайного ключа
     * @return числовой ключ
     * @throws NumberFormatException если ключ невалиден или обязателен, но не введен
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
            int key = Integer.parseInt(keyText);
            keyValidator.validateKeyRange(key);
            return key;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ключ должен быть числом");
        } catch (IllegalArgumentException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    /**
     * Отображает статус операции в нижней части окна.
     *
     * @param message текст сообщения
     * @param isError является ли сообщение ошибкой
     */
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

    /**
     * Копирует текст из поля вывода в поле ввода.
     * Используется для повторного шифрования/дешифрования.
     */
    @FXML
    private void copyOutputToInput() {
        String outputText = outputTextArea.getText();
        if (!outputText.isEmpty()) {
            inputTextArea.setText(outputText);
            outputTextArea.clear();
            showStatus("Текст перемещен в поле ввода", false);
        }
    }

    /**
     * Очищает все поля ввода/вывода.
     */
    @FXML
    private void clearAllFields() {
        inputTextArea.clear();
        outputTextArea.clear();
        keyField.clear();
        showStatus("Все поля очищены", false);
    }

    /**
     * Показывает справочное окно с инструкцией по использованию приложения.
     */
    @FXML
    private void showHelp() {
        alertHelper.showAlert("Справка",
                "Криптоанализатор - приложение для шифрования и дешифрования текста методом Цезаря.\n\n" +
                        "Порядок работы:\n" +
                        "1. Введите текст или загрузите файл\n" +
                        "2. Укажите ключ шифрования (число от 1 до " + MAX_KEY_VALUE + ")\n" +
                        "3. Выберите операцию (шифрование, дешифрование или взлом)\n\n" +
                        "При шифровании можно оставить поле ключа пустым - будет сгенерирован случайный ключ.\n" +
                        "Для brute-force ключ указывать не требуется, приложение автоматически найдет наиболее вероятный ключ."
        );
    }
}
