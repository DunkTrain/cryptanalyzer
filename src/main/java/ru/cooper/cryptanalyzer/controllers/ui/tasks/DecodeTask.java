package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextDecoder;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

/**
 * Фоновая задача для дешифрования текст методом Цезаря.
 * Обрабатывает текст построчно с указанным ключом через {@link TextDecoder}.
 */
public class DecodeTask extends Task<String> {

    private final String text;
    private final int key;

    /**
     * Создает задачу для дешифрования.
     *
     * @param text зашифрованный текст.
     * @param key  ключ дешифрования (должен быть от 1 до {@link CryptoAlphabet#LENGTH_ALPHABET}
     */
    public DecodeTask(String text, int key) {
        this.text = text;
        this.key = key;
    }

    /**
     * Выполняет дешифрование в фоновом потоке.
     *
     * @return расшифрованный текст.
     */
    @Override
    protected String call() {
        StringBuilder decodedContent = new StringBuilder();

        for (String line : text.split("\n")) {
            String decodedLine = TextDecoder.decrypt(line, key);
            decodedContent.append(decodedLine).append("\n");
        }

        return decodedContent.toString().trim();
    }
}
