package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextBruteForce;

/**
 * Фоновая задача для brute-force зашифрованного текста.
 * Использует {@link TextBruteForce} для автоматического подбора ключа шифрования.
 */
public class BruteForceTask extends Task<TextBruteForce.BruteForceResult> {

    private final String text;

    /**
     * Создаем задачу для взлома текста.
     *
     * @param text зашифрованный текст для анализа.
     */
    public BruteForceTask(String text) {
        this.text = text;
    }

    /**
     * Запускает задачу для взлома текста.
     *
     * @return text зашифрованный текст для анализа.
     */
    @Override
    protected TextBruteForce.BruteForceResult call() {
        return TextBruteForce.bruteForce(text);
    }
}
