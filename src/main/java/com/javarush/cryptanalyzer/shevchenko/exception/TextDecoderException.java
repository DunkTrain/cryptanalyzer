package com.javarush.cryptanalyzer.shevchenko.exception;

/**
 * Исключение возникает при ошибках во время дешифрования
 */
public class TextDecoderException extends RuntimeException {

    /**
     * @param message описание ошибки
     * @param cause исходное исключение
     */
    public TextDecoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
