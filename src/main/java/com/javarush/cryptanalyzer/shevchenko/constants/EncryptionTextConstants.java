package com.javarush.cryptanalyzer.shevchenko.constants;


import static com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet.LENGTH_ALPHABET;

public abstract class EncryptionTextConstants {

    public static final String ENTER_KEY = String.format("""
            Enter the encryption key:
            The key must be an integer from 1 to %d.
            Press Enter to use default key.""", LENGTH_ALPHABET);
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


}
