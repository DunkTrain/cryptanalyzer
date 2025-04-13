package ru.cooper.cryptanalyzer.domain.model;

/**
 * Represents a character set used in text processing operations such as encoding, decoding,
 * and cryptographic analysis. An implementation defines a full alphabet including upper/lowercase
 * letters, digits, and symbols.
 */
public interface Alphabet {

    /**
     * Returns the full string representation of the alphabet, including all characters
     * (e.g., letters, digits, punctuation).
     *
     * @return a string containing all characters in the alphabet
     */
    String getFullAlphabet();

    /**
     * Checks if the given character exists in the alphabet.
     *
     * @param symbol the character to check
     * @return true if the character is present in the alphabet; false otherwise
     */
    boolean contains(char symbol);

    /**
     * Returns the index of the specified character in the alphabet.
     *
     * @param symbol the character to look up
     * @return the index of the character, or -1 if not found
     */
    int getIndexOf(char symbol);

    /**
     * Returns the character at the specified index in the alphabet.
     *
     * @param index the index of the character
     * @return the character at the given index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    char getCharAt(int index);

    /**
     * Returns the total number of characters in the alphabet.
     *
     * @return the length of the alphabet
     */
    int length();
}
