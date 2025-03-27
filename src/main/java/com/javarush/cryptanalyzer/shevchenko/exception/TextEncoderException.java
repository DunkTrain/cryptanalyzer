package com.javarush.cryptanalyzer.shevchenko.exception;

/**
 * Исключение возникает при ошибках во время шифрования текста.
 */
public class TextEncoderException extends RuntimeException {

    /**
     * @param message описание ошибки
     * @param cause исходное исключение
     */
    public TextEncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
