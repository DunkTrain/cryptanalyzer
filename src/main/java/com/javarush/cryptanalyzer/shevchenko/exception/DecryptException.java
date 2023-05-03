package com.javarush.cryptanalyzer.shevchenko.exception;

public class DecryptException extends RuntimeException{
    public DecryptException(String message, Exception e) {
        super(message, e);
    }
}
