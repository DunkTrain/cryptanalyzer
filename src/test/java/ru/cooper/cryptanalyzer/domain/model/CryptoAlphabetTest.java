package ru.cooper.cryptanalyzer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Криптографический алфавит")
class CryptoAlphabetTest {

    @Test
    @DisplayName("Должен иметь корректную длину")
    void shouldHaveCorrectLength() {
        int expectedLength = CryptoAlphabet.LETTERS_UPPER_CASE.length() +
                CryptoAlphabet.LETTERS_LOWER_CASE.length() +
                CryptoAlphabet.NUMBERS.length() +
                CryptoAlphabet.SYMBOLS.length();

        assertThat(CryptoAlphabet.LENGTH_ALPHABET)
                .isEqualTo(expectedLength)
                .isEqualTo(CryptoAlphabet.ALPHABET.length());
    }

    @ParameterizedTest
    @ValueSource(chars = {'А', 'Я', 'а', 'я', '0', '9', '.', ' '})
    @DisplayName("Должен содержать допустимые символы")
    void shouldContainValidChars(char symbol) {
        assertThat(CryptoAlphabet.contains(symbol)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(chars = {'@', '#', '$', 'A', 'Z', 'a', 'z'})
    @DisplayName("Не должен содержать недопустимые символы")
    void shouldNotContainInvalidChars(char symbol) {
        assertThat(CryptoAlphabet.contains(symbol)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "'А', 0",
            "'Я', 32",
            "'а', 33",
            "'я', 65",
            "'0', 66",
            "'9', 75",
            "'.', 76",
            "'?', 82"
    })
    @DisplayName("Должен возвращать корректный индекс для символа")
    void shouldReturnCorrectIndex(char symbol, int expectedIndex) {
        assertThat(CryptoAlphabet.getIndexOf(symbol))
                .isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 'А'",
            "32, 'Я'",
            "33, 'а'",
            "65, 'я'",
            "66, '0'",
            "75, '9'",
            "76, '.'",
            "82, '?'"
    })
    @DisplayName("Должен возвращать корректный символ по индексу")
    void shouldReturnCorrectCharByIndex(int index, char expectedSymbol) {
        assertThat(CryptoAlphabet.getCharAt(index))
                .isEqualTo(expectedSymbol);
    }

    @Test
    @DisplayName("Должен бросать исключение при недопустимом индексе")
    void shouldThrowExceptionForInvalidIndex() {
        assertThatThrownBy(() -> CryptoAlphabet.getCharAt(-1))
                .isInstanceOf(StringIndexOutOfBoundsException.class);

        assertThatThrownBy(() -> CryptoAlphabet.getCharAt(CryptoAlphabet.LENGTH_ALPHABET + 1))
                .isInstanceOf(StringIndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("Нельзя создать экземпляр класса")
    void shouldNotAllowInstantiation() {
        assertThatThrownBy(() -> {
            var constructor = CryptoAlphabet.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}
