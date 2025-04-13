package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextEncoder;
import ru.cooper.cryptanalyzer.domain.model.Alphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.RussianAlphabet;

/**
 * Фоновая задача для шифрования текста методом Цезаря.
 * Шифрует текст построчно с указанным ключом через {@link TextEncoder}.
 */
public class EncodeTask extends Task<String> {

    private final String text;
    private final int key;
    private final TextEncoder textEncoder;

    /**
     * @param text  исходный текст.
     * @param key   ключ шифрования
     * @param alphabet алфавит для шифрования
     */
    public EncodeTask(String text, int key, Alphabet alphabet) {
        this.text = text;
        this.key = key;
        this.textEncoder = new TextEncoder(alphabet);
    }

    @Override
    protected String call() {
        StringBuilder encodedContent = new StringBuilder();

        for (String line : text.split("\n")) {
            String encodedLine = textEncoder.encodeText(line, key);
            encodedContent.append(encodedLine).append("\n");
        }

        return encodedContent.toString().trim();
    }
}
