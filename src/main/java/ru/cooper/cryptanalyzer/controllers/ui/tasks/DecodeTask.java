package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextDecoder;
import ru.cooper.cryptanalyzer.domain.model.Alphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.RussianAlphabet;

/**
 * A background task that performs decryption using the Caesar cipher.
 * <p>
 * The text is processed line by line using the provided key and {@link TextDecoder}.
 */
public class DecodeTask extends Task<String> {

    private final String text;
    private final int key;
    private final TextDecoder decoder;

    /**
     * Constructs a new decryption task.
     *
     * @param text     the encrypted text to decrypt
     * @param key      the Caesar cipher key (should be between 1 and {@link RussianAlphabet#LENGTH})
     * @param alphabet the alphabet used for decryption
     */
    public DecodeTask(String text, int key, Alphabet alphabet) {
        this.text = text;
        this.key = key;
        this.decoder = new TextDecoder(alphabet);
    }

    @Override
    protected String call() {
        StringBuilder decodedContent = new StringBuilder();

        for (String line : text.split("\n")) {
            String decodedLine = decoder.decrypt(line, key);
            decodedContent.append(decodedLine).append("\n");
        }

        return decodedContent.toString().trim();
    }
}
