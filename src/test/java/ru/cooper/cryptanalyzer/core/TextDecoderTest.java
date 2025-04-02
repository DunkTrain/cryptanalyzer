package ru.cooper.cryptanalyzer.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.cooper.cryptanalyzer.domain.model.CryptoAlphabet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Декодер текста")
class TextDecoderTest {

    private final TextDecoder decoder = new TextDecoder();

    @Test
    @DisplayName("Должен возвращать пустую строку для null или пустого ввода")
    void shouldHandleEmptyInput() {
        assertThat(decoder.decrypt(null, 5)).isEmpty();
        assertThat(decoder.decrypt("", 3)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "'Тулезх', 3, 'Привет'",
            "'6789.', 5, '12345'",
            "'ЬоыьВ', 10, 'Тест.'",
            "'а', 33, 'А'",
            "'0', 33, 'а'"
    })
    @DisplayName("Должен корректно дешифровать строки")
    void shouldDecodeTextCorrectly(String input, int key, String expected) {
        assertThat(decoder.decrypt(input, key))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'A', 5, 'A'",
            "'@', 10, '@'",
            "'#хзфхА', 3, '#тест!'"
    })
    @DisplayName("Должен сохранять символы не из алфавита")
    void shouldPreserveNonAlphabetChars(String input, int key, String expected) {
        assertThat(decoder.decrypt(input, key))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'А', 0, 'А'",
            "'а', 33, 'А'",
            "'0', 33, 'а'",
            "'а', 1, 'Я'",
            "'.', 10, '0'",
            "'Д', 5, ' '"
    })
    @DisplayName("Должен корректно дешифровать отдельные символы")
    void shouldDecodeSingleCharCorrectly(char symbol, int key, char expected) {
        assertThat(decoder.leftOffset(symbol, key))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Должен корректно обрабатывать ключ больше длины алфавита")
    void shouldHandleKeyLargerThanAlphabet() {
        String input = "Чслзкч";
        int key = CryptoAlphabet.LENGTH_ALPHABET + 3;

        assertThat(decoder.decrypt(input, key))
                .isEqualTo(decoder.decrypt(input, 3));
    }

    @Test
    @DisplayName("Должен работать как обратная операция к кодировщику")
    void shouldBeInverseOfEncoder() {
        TextEncoder encoder = new TextEncoder();
        String original = "Тестовое сообщение 123!";
        int key = 7;

        String encoded = encoder.encodeText(original, key);
        String decoded = decoder.decrypt(encoded, key);

        assertThat(decoded).isEqualTo(original);
    }
}
