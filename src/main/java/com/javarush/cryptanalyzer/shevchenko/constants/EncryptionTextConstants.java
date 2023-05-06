package com.javarush.cryptanalyzer.shevchenko.constants;

public abstract class EncryptionTextConstants {

    public static final String ENTER_KEY = """
            Enter the encryption key:
            The key must be an integer from 1 to 33.
            Press Enter to use default key.""";
    public static final String ENTER_INPUT_ENCODE_FILEPATH = """
            Enter the file path to encode:
            Press Enter to use default file path.""";

    public static final String ENTER_INPUT_DECODE_FILEPATH = """
            Enter the file path to decode:
            Press Enter to use default file path.""";

    public static final String ENTER_OUTPUT_ENCODE_FILEPATH = """
            Enter the file path to save the encoded file:
            Press Enter to use default file path.""";

    public static final String ENTER_OUTPUT_DECODE_FILEPATH = """
            Enter the file path to save the decoded file:
            Press Enter to use default file path.""";

    public static final String ENTER_MODE = """
            Enter 1 to encode text.
            Enter 2 to decode text using the encryption key.
            Enter 3 to decode text using the Brute force method.""";

    public static final String DESCRIPTION = """
            Welcome to "Caesar Cipher".
            The Caesar cipher was invented in ancient Rome and named after Julius Caesar, although Caesar himself was not the actual inventor of the cipher.
            This cipher was widely used by Roman armies to protect the transmission of important messages.
            It was based on the primitive principle of replacing each letter in a message with another letter, shifting it by a certain number of positions in the alphabet.
            This program is designed to encrypt and decrypt text using the Caesar cipher algorithm.
            The cryptographic alphabet is based on all the letters of the Russian alphabet and punctuation marks!""";

}
