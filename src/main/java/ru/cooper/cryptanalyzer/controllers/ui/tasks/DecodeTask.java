package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextDecoder;
import ru.cooper.cryptanalyzer.domain.model.Alphabet;
import ru.cooper.cryptanalyzer.domain.model.languages.RussianAlphabet;

/**
 * Фоновая задача для дешифрования текст методом Цезаря.
 * Обрабатывает текст построчно с указанным ключом через {@link TextDecoder}.
 */
public class DecodeTask extends Task<String> {

    private final String text;
    private final int key;
    private final TextDecoder decoder;

    /**
     * Создает задачу для дешифрования.
     *
     * @param text зашифрованный текст.
     * @param key  ключ дешифрования (должен быть от 1 до {@link RussianAlphabet#LENGTH}
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
