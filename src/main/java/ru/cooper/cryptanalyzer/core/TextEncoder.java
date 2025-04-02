package ru.cooper.cryptanalyzer.core;

import static ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet.ALPHABET;
import static ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet.LENGTH_ALPHABET;

/**
 * Реализует алгоритм шифрования текста с использованием шифра Цезаря.
 * <p>
 * Обеспечивает:
 * <ui>
 *     <li>Шифрование строки текста с заданным ключом</li>
 *     <li>Шифрование отдельных символов</li>
 *     <li>Сохранение символов, не входящих в криптографический алфавит</li>
 * </ui>
 *
 * <p>Все методы класса статические - экземпляры не создаются</p>
 */
public class TextEncoder {

    /**
     * Шифрует текст с использованием указанного ключа.
     *
     * @param inputText исходный текст.
     * @param encryptionKey ключ шифрования.
     * @return зашифрованный текст.
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
     * Шифрует символ со сдвигом вправо.
     *
     * @param symbol символ для шифрования
     * @param key ключ шифрования (величина сдвига)
     * @return зашифрованный символ
     */
    public char encryptCharRight(char symbol, int key) {
        if (ALPHABET.indexOf(symbol) != -1) {
            return ALPHABET.charAt((ALPHABET.indexOf(symbol) + key) % LENGTH_ALPHABET);
        } else {
            return symbol;
        }
    }
}
