package ru.cooper.cryptanalyzer.core;

import ru.cooper.cryptanalyzer.domain.model.Alphabet;

/**
 * Utility class for decrypting Caesar cipher-encoded text using a given {@link Alphabet}.
 */
public class TextDecoder {

    private final Alphabet alphabet;

    /**
     * Constructs a {@code TextDecoder} for a specific alphabet.
     *
     * @param alphabet the alphabet to be used for decryption
     */
    public TextDecoder(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Decrypts the given ciphertext by shifting each character to the left by the specified key.
     *
     * @param ciphertext the encrypted text
     * @param key        the Caesar cipher shift key
     * @return the decrypted plain text
     */
    public String decrypt(String ciphertext, int key) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            return "";
        }

        StringBuilder plaintext = new StringBuilder(ciphertext.length());

        for (char symbol : ciphertext.toCharArray()) {
            plaintext.append(leftOffset(symbol, key));
        }

        return plaintext.toString();
    }

    /**
     * Performs a left shift of a character within the alphabet.
     * If the character is not part of the alphabet, it is returned unchanged.
     *
     * @param symbol the character to shift
     * @param key    the number of positions to shift
     * @return the shifted character
     */
    public char leftOffset(char symbol, int key) {
        if (alphabet.contains(symbol)) {
            int index = alphabet.getIndexOf(symbol);
            int newIndex = (index + alphabet.length() - key) % alphabet.length();
            return alphabet.getCharAt(newIndex);
        }
        return symbol;
    }
}
