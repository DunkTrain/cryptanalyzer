package com.javarush.cryptanalyzer.shevchenko.entity;

public class CaesarCipher {

   /* private final int key;

    public CaesarCipher(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String encode(String plainText) {
        validateInput(plainText);

        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (Alphabet.ALL_CHARACTERS.indexOf(c) == -1) {
                continue;
            }

            int index = Alphabet.ALL_CHARACTERS.indexOf(c);
            int newIndex = (index + key) % Alphabet.ALL_CHARACTERS.length();
            cipherText.append(Alphabet.ALL_CHARACTERS.charAt(newIndex));
        }

        return cipherText.toString();
    }

    public String decode(String cipherText) {
        validateInput(cipherText);

        StringBuilder plainText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (Alphabet.ALL_CHARACTERS.indexOf(c) == -1) {
                continue;
            }

            int index = Alphabet.ALL_CHARACTERS.indexOf(c);
            int newIndex = (index - key + Alphabet.ALL_CHARACTERS.length()) % Alphabet.ALL_CHARACTERS.length();
            plainText.append(Alphabet.ALL_CHARACTERS.charAt(newIndex));
        }

        return plainText.toString();
    }

    private void validateInput(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new InvalidInputException("Недопустимый ввод: текст не может быть пустым или отсутствовать");
        }
    }*/
}

