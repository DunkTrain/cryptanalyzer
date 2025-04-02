package ru.cooper.cryptanalyzer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextBruteForceTest {
    private TextBruteForce bruteForce;

    @BeforeEach
    void setUp() {
        bruteForce = new TextBruteForce();
    }

    @Test
    @DisplayName("Проверка bruteForce с null входными данными")
    void testBruteForceWithNullInput() {
        TextBruteForce.BruteForceResult result = bruteForce.bruteForce(null);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("", result.getDecryptedText(), "Расшифрованный текст должен быть пустым");
    }

    @Test
    @DisplayName("Проверка bruteForce с пустой строкой")
    void testBruteForceWithEmptyString() {
        TextBruteForce.BruteForceResult result = bruteForce.bruteForce("");
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("", result.getDecryptedText(), "Расшифрованный текст должен быть пустым");
    }

    @Test
    @DisplayName("Проверка bruteForce с коротким текстом")
    void testBruteForceWithShortText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bruteForce.bruteForce("abc"));
        assertTrue(exception.getMessage().contains("Текст слишком короткий"),
                "Ожидается сообщение об ошибке: 'Текст слишком короткий'");
    }
}
