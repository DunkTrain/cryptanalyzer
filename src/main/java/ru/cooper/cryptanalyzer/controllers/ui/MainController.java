package ru.cooper.cryptanalyzer.controllers.ui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import ru.cooper.cryptanalyzer.domain.model.Alphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.EnglishAlphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.RussianAlphabet;
import ru.cooper.cryptanalyzer.util.LanguageProfile;
import ru.cooper.cryptanalyzer.util.LanguageProfiles;

import java.io.File;
import java.util.Random;

/**
 * Controller for the main application window.
 * <p>
 * Manages Caesar cipher operations including encryption, decryption, and brute-force cracking.
 * Handles user interaction, validation, and UI state.
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

    @FXML
    private ComboBox<String> languageComboBox;

    private static final Random RANDOM = new Random();
    private static final int MAX_KEY_VALUE = RussianAlphabet.LENGTH - 1;
    private static final String KEY_RANGE_PROMPT = "Ключ от 1 до " + MAX_KEY_VALUE;

    private final KeyValidator keyValidator;
    private final FileHelper fileHelper;
    private final AlertHelper alertHelper;

    /**
     * Constructs the controller and initializes helpers:
     * key validator, file handler, and alert dialog utility.
     */
    public MainController() {
        this.keyValidator = new KeyValidator(MAX_KEY_VALUE);
        this.fileHelper = new FileHelper();
        this.alertHelper = new AlertHelper();
    }

    /**
     * Initializes JavaFX UI components after the FXML is loaded.
     * <p>
     * Sets up UI hints, control states, and language options.
     */
    @FXML
    private void initialize() {
        setupUIComponents();
        setupEventHandlers();
        languageComboBox.getItems().addAll("Русский", "English");
        languageComboBox.getSelectionModel().selectFirst();
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
     * Opens a file chooser dialog to load a text file into the input area.
     * Displays an alert on failure.
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
     * Opens a file chooser dialog to save the contents of the output area.
     * Displays an alert if there is no text or on I/O failure.
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
     * Handles the "Encode" button action.
     * <p>
     * Validates input, generates or reads a key, performs encryption asynchronously,
     * and displays the result or error.
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

            Alphabet selectedAlphabet = getSelectedAlphabet();

            EncodeTask encodeTask = new EncodeTask(inputText, key, selectedAlphabet);
            setupTaskHandlers(encodeTask,
                    "Текст успешно зашифрован с ключом " + key, "Ошибка шифрования");

            new Thread(encodeTask).start();

        } catch (NumberFormatException e) {
            alertHelper.showAlert("Ошибка шифрования", e.getMessage());
            resetControlsState();
        }
    }

    /**
     * Handles the "Decode" button action.
     * <p>
     * Validates input and key, performs decryption asynchronously,
     * and displays the result or error.
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

            Alphabet selectedAlphabet = getSelectedAlphabet();

            DecodeTask decodeTask = new DecodeTask(inputText, key, selectedAlphabet);
            setupTaskHandlers(decodeTask, "Текст успешно дешифрован с ключом " + key, "Ошибка дешифрования");

            new Thread(decodeTask).start();

        } catch (NumberFormatException e) {
            alertHelper.showAlert("Ошибка дешифрования", e.getMessage());
            resetControlsState();
        }
    }

    /**
     * Handles the "Brute-force" button action.
     * <p>
     * Automatically finds the most probable Caesar cipher key based on a language profile
     * and decrypts the input text.
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

            LanguageProfile selectedProfile = getSelectedProfile();

            BruteForceTask bruteForceTask = new BruteForceTask(inputText, selectedProfile);

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
     * Sets up success and failure handlers for a background task.
     *
     * @param task           the asynchronous task (encode/decode)
     * @param successMessage message to display on success
     * @param errorTitle     title for error dialog on failure
     * @param <T>            task result type
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
     * Parses the Caesar cipher key from the text field.
     * <p>
     * If the field is empty and random generation is allowed, returns a random key.
     *
     * @param allowRandom whether to generate a random key if none is provided
     * @return the parsed key
     * @throws NumberFormatException if the input is invalid or key is missing when required
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
     * Displays a status message in the status label with appropriate styling.
     *
     * @param message the status message
     * @param isError whether the message represents an error
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
     * Copies the content of the output text area into the input area.
     * <p>
     * Useful for chaining operations like encryption → decryption.
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
     * Clears the input, output, and key fields.
     * Resets the status message.
     */
    @FXML
    private void clearAllFields() {
        inputTextArea.clear();
        outputTextArea.clear();
        keyField.clear();
        showStatus("Все поля очищены", false);
    }

    /**
     * Displays a help dialog with usage instructions.
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

    /**
     * Returns the alphabet based on the selected language.
     *
     * @return {@link RussianAlphabet} or {@link EnglishAlphabet}
     */
    private Alphabet getSelectedAlphabet() {
        String selectedLang = languageComboBox.getSelectionModel().getSelectedItem();
        if ("Русский".equals(selectedLang)) {
            return RussianAlphabet.getInstance();
        } else {
            return EnglishAlphabet.getInstance();
        }
    }

    /**
     * Returns the language profile for frequency analysis based on selection.
     *
     * @return {@link LanguageProfile} for Russian or English
     */
    private LanguageProfile getSelectedProfile() {
        String selectedLang = languageComboBox.getSelectionModel().getSelectedItem();
        if ("Русский".equals(selectedLang)) {
            return LanguageProfiles.russianProfile();
        } else {
            return LanguageProfiles.englishProfile();
        }
    }
}
