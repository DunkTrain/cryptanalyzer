package ru.cooper.cryptanalyzer.core;

import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

/**
 * Реализует алгоритм дешифрования текста, зашифрованного шифром Цезаря.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Дешифрование строки текста с заданным ключом</li>
 *   <li>Дешифрование отдельных символов</li>
 *   <li>Сохранение символов, не входящих в криптографический алфавит</li>
 * </ul>
 */
public class TextDecoder {

    /**
     * Дешифрует текст с использованием указанного ключа.
     *
     * @param ciphertext зашифрованный текст.
     * @param key ключ дешифрования.
     * @return расшифрованный текст.
     */
    public static String decrypt(String ciphertext, int key) {
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
     * Дешифрует символ со сдвигом влево
     *
     * @param symbol символ для дешифрования
     * @param key ключ дешифрования (сдвиг)
     * @return дешифрованный символ
     */
    public static char leftOffset(char symbol, int key) {
        if (CryptoAlphabet.contains(symbol)) {
            int alphabetIndex = CryptoAlphabet.getIndexOf(symbol);
            int newIndex = (alphabetIndex + CryptoAlphabet.LENGTH_ALPHABET - key) % CryptoAlphabet.LENGTH_ALPHABET;

            return CryptoAlphabet.getCharAt(newIndex);
        } else {
            return symbol;
        }
    }
}
