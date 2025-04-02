package ru.cooper.cryptanalyzer.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кодировщик текста")
class TextEncoderTest {

    private final TextEncoder encoder = new TextEncoder();

    @Test
    @DisplayName("Должен возвращать пустую строку для null или пустого ввода")
    void shouldHandleEmptyInput() {
        assertThat(encoder.encodeText(null, 5)).isEmpty();
        assertThat(encoder.encodeText("", 3)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "'Привет', 3, 'Тулезх'",
            "'12345', 5, '6789.'",
            "'Тест.', 10, 'ЬоыьВ'",
            "'А', 33, 'а'",
            "'а', 33, '0'"
    })
    @DisplayName("Должен корректно шифровать строки")
    void shouldEncodeTextCorrectly(String input, int key, String expected) {
        assertThat(encoder.encodeText(input, key))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'A', 5, 'A'",
            "'@', 10, '@'",
            "'#тест!', 3, '#хзфхА'"
    })
    @DisplayName("Должен сохранять символы не из алфавита")
    void shouldPreserveNonAlphabetChars(String input, int key, String expected) {
        assertThat(encoder.encodeText(input, key))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'А', 0, 'А'",
            "'А', 33, 'а'",
            "'а', 33, '0'",
            "'Я', 1, 'а'",
            "'0', 10, '.'",
            "' ', 5, 'Д'"
    })
    @DisplayName("Должен корректно шифровать отдельные символы")
    void shouldEncodeSingleCharCorrectly(char symbol, int key, char expected) {
        assertThat(encoder.encryptCharRight(symbol, key))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Должен корректно обрабатывать ключ больше длины алфавита")
    void shouldHandleKeyLargerThanAlphabet() {
        String input = "Тест";
        int key = CryptoAlphabet.LENGTH_ALPHABET + 5;

        assertThat(encoder.encodeText(input, key))
                .isEqualTo(encoder.encodeText(input, 5));
    }
}
