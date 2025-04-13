package ru.cooper.cryptanalyzer.domain.model.languages;

import ru.cooper.cryptanalyzer.domain.model.Alphabet;

/**
 * Singleton implementation of the {@link Alphabet} interface for the English language.
 * <p>
 * Includes uppercase and lowercase English letters, digits, and common punctuation symbols.
 * This class uses a thread-safe lazy initialization pattern.
 */
public final class EnglishAlphabet implements Alphabet {

    private static final String LETTERS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERS_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOL = ".,\":-!? ";

    private static final String FULL = LETTERS_UPPER + LETTERS_LOWER + NUMBERS + SYMBOL;
    private static final int LENGTH = FULL.length();

    /**
     * Private constructor to prevent instantiation.
     * This class is a singleton and must be accessed via {@link #getInstance()}.
     */
    private EnglishAlphabet() {
    }

    /**
     * Holder class for lazy, thread-safe singleton initialization.
     */
    private static class Holder {
        private static final EnglishAlphabet INSTANCE = new EnglishAlphabet();
    }

    /**
     * Returns the singleton instance of the English alphabet.
     *
     * @return the English alphabet instance
     */
    public static EnglishAlphabet getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String getFullAlphabet() {
        return FULL;
    }

    @Override
    public boolean contains(char symbol) {
        return getIndexOf(symbol) != -1;
    }

    @Override
    public int getIndexOf(char symbol) {
        return FULL.indexOf(symbol);
    }

    @Override
    public char getCharAt(int index) {
        return FULL.charAt(index);
    }

    @Override
    public int length() {
        return LENGTH;
    }
}
