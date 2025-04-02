package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextEncoder;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

/**
 * Фоновая задача для шифрования текста методом Цезаря.
 * Шифрует текст построчно с указанным ключом через {@link TextEncoder}.
 */
public class EncodeTask extends Task<String> {

    private final String text;
    private final int key;

    /**
     * Создает задачу для шифрования.
     *
     * @param text исходный текст.
     * @param key  ключ шифрования (должен быть от 1 до {@link CryptoAlphabet#LENGTH_ALPHABET}.
     */
    public EncodeTask(String text, int key) {
        this.text = text;
        this.key = key;
    }

    /**
     * Выполняет шифрование в фоновом потоке.
     *
     * @return зашифрованный текст.
     */
    @Override
    protected String call() {
        StringBuilder encodedContent = new StringBuilder();

        for (String line : text.split("\n")) {
            String encodedLine = TextEncoder.encodeText(line, key);
            encodedContent.append(encodedLine).append("\n");
        }

        return encodedContent.toString().trim();
    }
}
