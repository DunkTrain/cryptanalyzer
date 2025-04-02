package ru.cooper.cryptanalyzer.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DictionaryTest {

    @Test
    @DisplayName("Проверка, что список COMMON_RUSSIAN_WORDS не пуст")
    void testCommonWordsNotEmpty() {
        assertFalse(Dictionary.COMMON_RUSSIAN_WORDS.isEmpty(), "COMMON_RUSSIAN_WORDS не должен быть пустым");
    }

    @Test
    @DisplayName("Проверка наличия распространённых слов в словаре")
    void testContainsCommonWords() {
        assertTrue(Dictionary.COMMON_RUSSIAN_WORDS.contains("привет"), "Должно содержать 'привет'");
        assertTrue(Dictionary.COMMON_RUSSIAN_WORDS.contains("компьютер"), "Должно содержать 'компьютер'");
        assertTrue(Dictionary.COMMON_RUSSIAN_WORDS.contains("работа"), "Должно содержать 'работа'");
    }

    @Test
    @DisplayName("Проверка отсутствия несуществующих слов")
    void testDoesNotContainNonexistentWords() {
        assertFalse(Dictionary.COMMON_RUSSIAN_WORDS.contains("xyz123"), "Не должно содержать 'xyz123'");
    }

    @Test
    @DisplayName("Проверка неизменяемости множества словаря")
    void testUnmodifiableSet() {
        assertThrows(UnsupportedOperationException.class, () ->
                Dictionary.COMMON_RUSSIAN_WORDS.add("новое_слово"), "Множество должно быть неизменяемым");
    }
}
