package ru.cooper.cryptanalyzer.core;

import ru.cooper.cryptanalyzer.domain.model.Alphabet;

/**
 * Utility class for encrypting text using the Caesar cipher method with a given {@link Alphabet}.
 */
public class TextEncoder {

    private final Alphabet alphabet;

    /**
     * Constructs a {@code TextEncoder} for a specific alphabet.
     *
     * @param alphabet the alphabet to be used for encryption
     */
    public TextEncoder(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Encrypts the given input text using the Caesar cipher with the provided key.
     *
     * @param inputText     the plain text to encrypt
     * @param encryptionKey the Caesar cipher shift key
     * @return the encrypted text
     */
    public String encodeText(String inputText, int encryptionKey) {
        if (inputText == null || inputText.isEmpty()) {
            return "";
        }

        StringBuilder encodedText = new StringBuilder(inputText.length());

        for (char symbol : inputText.toCharArray()) {
            encodedText.append(encryptCharRight(symbol, encryptionKey));
        }

        return encodedText.toString();
    }

    /**
     * Performs a right shift of a character within the alphabet.
     * If the character is not part of the alphabet, it is returned unchanged.
     *
     * @param symbol the character to shift
     * @param key    the number of positions to shift
     * @return the shifted character
     */
    public char encryptCharRight(char symbol, int key) {
        if (alphabet.contains(symbol)) {
            int index = alphabet.getIndexOf(symbol);
            int newIndex = (index + key) % alphabet.length();

            return alphabet.getCharAt(newIndex);
        }

        return symbol;
    }
}
