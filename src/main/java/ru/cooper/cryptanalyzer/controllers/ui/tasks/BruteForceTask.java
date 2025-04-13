package ru.cooper.cryptanalyzer.controllers.ui.tasks;

import javafx.concurrent.Task;
import ru.cooper.cryptanalyzer.core.TextBruteForce;
import ru.cooper.cryptanalyzer.util.LanguageProfile;

import java.util.Collections;

/**
 * A background task that performs brute-force decryption of the given encrypted text.
 * <p>
 * Uses {@link TextBruteForce} to automatically guess the Caesar cipher key
 * based on language profile heuristics.
 */
public class BruteForceTask extends Task<TextBruteForce.BruteForceResult> {

    private final String text;
    private final TextBruteForce bruteForce;

    /**
     * Constructs a new brute-force decryption task.
     *
     * @param text    the encrypted text to analyze
     * @param profile the language profile used for frequency analysis
     */
    public BruteForceTask(String text, LanguageProfile profile) {
        this.text = text;
        this.bruteForce = new TextBruteForce(Collections.singletonList(profile));
    }


    @Override
    protected TextBruteForce.BruteForceResult call() {
        return bruteForce.bruteForce(text);
    }
}
