package ru.cooper.cryptanalyzer.controllers.ui.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyValidatorTest {

    private KeyValidator validator;

    @Before
    public void setUp() {
        validator = new KeyValidator(10);
    }

    @Test
    @DisplayName("Проверка корректного ввода ключа")
    public void testValidKey() {
        validator.validate("5", "0");
    }

    @Test
    @DisplayName("Проверка пустого ввода")
    public void testEmptyInput() {
        validator.validate("", "1");
    }

    @Test
    @DisplayName("Проверка ввода букв вместо цифр")
    public void testNonNumericInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("abc", "1"));
        assertEquals("Только цифры", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка ввода слишком большого числа")
    public void testKeyOutOfRangeHigh() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("15", "1"));
        assertEquals("Ключ должен быть числом от 1 до 10", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка ввода ключа меньше 1")
    public void testKeyOutOfRangeLow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("0", "1"));
        assertEquals("Ключ должен быть числом от 1 до 10", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка ввода невалидного числа")
    public void testInvalidNumberFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("999999999999", "1"));
        assertEquals("Ключ должен быть числом", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка граничных значений ключа")
    public void testBoundaryValues() {
        validator.validate("1", "0");
        validator.validate("10", "0");
    }

    @Test
    @DisplayName("Проверка validateKeyRange с корректным ключом")
    public void testValidateKeyRangeValid() {
        validator.validateKeyRange(5);
    }

    @Test
    @DisplayName("Проверка validateKeyRange с ключом вне диапазона")
    public void testValidateKeyRangeInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateKeyRange(11));
        assertEquals("Ключ должен быть числом от 1 до 10", exception.getMessage());
    }
}
