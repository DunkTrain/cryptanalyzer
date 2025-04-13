package ru.cooper.cryptanalyzer.domain.model.languages;

import ru.cooper.cryptanalyzer.domain.model.Alphabet;

/**
 * Singleton implementation of the {@link Alphabet} interface for the Russian language.
 * <p>
 * Includes uppercase and lowercase Russian letters (including Ё/ё), digits, and common punctuation symbols.
 * This class uses a thread-safe lazy initialization pattern.
 */
public final class RussianAlphabet implements Alphabet {

    public static final String LETTERS_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String LETTERS_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static final String NUMBERS = "0123456789";
    public static final String SYMBOLS = ".,\":-!? ";

    public static final String FULL = LETTERS_UPPER + LETTERS_LOWER + NUMBERS + SYMBOLS;
    public static final int LENGTH = FULL.length();

    /**
     * Private constructor to prevent instantiation.
     * This class is a singleton and must be accessed via {@link #getInstance()}.
     */
    private RussianAlphabet() {
    }

    /**
     * Holder class for lazy, thread-safe singleton initialization.
     */
    private static class Holder {
        private static final RussianAlphabet INSTANCE = new RussianAlphabet();
    }

    /**
     * Returns the singleton instance of the Russian alphabet.
     *
     * @return the Russian alphabet instance
     */
    public static RussianAlphabet getInstance() {
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
