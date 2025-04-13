package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextEncoder;
import ru.cooper.cryptanalyzer.domain.model.Alphabet;

/**
 * A background task that performs encryption using the Caesar cipher.
 * <p>
 * Validates the input text and encrypts it line by line using {@link TextEncoder}.
 */
public class EncodeTask extends Task<String> {

    private final String text;
    private final int key;
    private final TextEncoder textEncoder;

    private static final int MIN_LENGTH = 10;
    private static final int MIN_WORDS = 2;

    /**
     * Constructs a new encryption task.
     *
     * @param text     the plain text to encrypt
     * @param key      the Caesar cipher key
     * @param alphabet the alphabet used for encryption
     */
    public EncodeTask(String text, int key, Alphabet alphabet) {
        this.text = text;
        this.key = key;
        this.textEncoder = new TextEncoder(alphabet);
    }

    @Override
    protected String call() {
        String trimmed = text.trim();
        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Текст должен содержать не менее " + MIN_LENGTH + " символов.");
        }

        String[] words = trimmed.split("\\s+");
        if (words.length < MIN_WORDS) {
            throw new IllegalArgumentException("Текст должен содержать хотя бы два слова.");
        }

        StringBuilder encodedContent = new StringBuilder();

        for (String line : text.split("\n")) {
            String encodedLine = textEncoder.encodeText(line, key);
            encodedContent.append(encodedLine).append("\n");
        }

        return encodedContent.toString().trim();
    }
}
