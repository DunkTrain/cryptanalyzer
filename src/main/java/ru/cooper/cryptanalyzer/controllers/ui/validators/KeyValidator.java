package ru.cooper.cryptanalyzer.controllers.ui.validators;

/**
 * Validator for Caesar cipher key input.
 * <p>
 * Ensures that the input consists of digits only and lies within the valid key range.
 */
public class KeyValidator {

    private final int maxKeyValue;

    /**
     * Constructs a new validator with the specified maximum allowed key value.
     *
     * @param maxKeyValue the upper bound of the valid key range (inclusive)
     */
    public KeyValidator(int maxKeyValue) {
        this.maxKeyValue = maxKeyValue;
    }

    /**
     * Validates the input string as a numeric key within the allowed range.
     *
     * @param newValue the new input value to validate
     * @throws IllegalArgumentException if the input is not a valid integer or out of bounds
     */
    public void validate(String newValue, String oldValue) {
        if(!newValue.matches("\\d*")) {
            System.out.println("Invalid input: " + newValue + ". Reverting to: " + oldValue);
            throw new IllegalArgumentException("Digits only");
        }

        if(!newValue.isEmpty()) {
            try {
                int value = Integer.parseInt(newValue);
                validateKeyRange(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ключ должен быть числом");
            }
        }
    }

    /**
     * Validates that the key is within the allowed range.
     *
     * @param key the key value to check
     * @throws IllegalArgumentException if the key is outside [1, maxKeyValue]
     */
    public void validateKeyRange(int key) {
        if (key < 1 || key > maxKeyValue) {
            throw new IllegalArgumentException("Ключ должен быть числом от 1 до " + maxKeyValue);
        }
    }
}
