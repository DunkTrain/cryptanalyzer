package com.javarush.cryptanalyzer.shevchenko.constants;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * Содержит набор часто используемых русских слов для анализа текста.
 * Слова сгруппированы по частям речи и частоте использования.
 */
public final class Dictionary {

    /**
     * Множество наиболее употребительных русских слов.
     * Включает местоимения, предлоги, глаголы, существительные и другие части речи.
     */
    public static final Set<String> COMMON_RUSSIAN_WORDS = initCommonWords();

    private Dictionary() {
        throw new UnsupportedOperationException("Это служебный класс и не может быть инстанциирован");
    }

    /**
     * Инициализирует и возвращает набор часто используемых русских слов.
     *
     * @return неизменяемый набор русских слов
     */
    private static Set<String> initCommonWords() {
        Set<String> words = new HashSet<>(200);

        // Местоимения и частицы
        addWords(words, "я", "ты", "он", "она", "мы", "вы", "они",
                "это", "то", "все", "что", "как", "так", "да", "нет");

        // Предлоги и союзы
        addWords(words, "в", "на", "с", "по", "за", "из", "от", "до", "для",
                "и", "а", "но", "или", "если", "чтобы", "когда", "потому");

        // Вопросы
        addWords(words, "кто", "что", "где", "куда", "откуда", "почему", "зачем", "какой");

        // Приветствия и общение
        addWords(words, "привет", "здравствуй", "здравствуйте", "добрый",
                "утро", "вечер", "спасибо", "пожалуйста", "извини",
                "прости", "пока", "свидания");

        // Глаголы
        addWords(words, "быть", "есть", "был", "была", "было", "стать",
                "сказать", "говорить", "знать", "хотеть", "мочь", "делать",
                "идти", "прийти", "видеть", "смотреть", "любить", "жить",
                "работать", "думать", "понимать", "ждать", "дать", "взять");

        // Существительные
        addWords(words, "человек", "мужчина", "женщина", "ребенок", "друг",
                "мать", "отец", "брат", "сестра", "семья", "имя", "время",
                "день", "год", "работа", "дом", "жизнь");

        // Прилагательные
        addWords(words, "хороший", "плохой", "новый", "старый", "большой",
                "маленький", "должный", "первый", "последний", "русский",
                "другой", "каждый", "самый", "весь");

        // Числительные
        addWords(words, "один", "два", "три", "четыре", "пять", "десять",
                "сто", "тысяча");

        // Наречия
        addWords(words, "здесь", "там", "тут", "сейчас", "тогда", "очень",
                "много", "мало", "хорошо", "плохо", "быстро", "медленно",
                "вместе", "снова", "почти", "уже");

        // Современные слова
        addWords(words, "компьютер", "телефон", "интернет", "программа",
                "файл", "сообщение", "пароль");

        return Set.copyOf(words);
    }

    /**
     * Вспомогательный метод для добавления группы слов в набор.
     *
     * @param words набор для добавления
     * @param items слова для добавления
     */
    private static void addWords(Set<String> words, String... items) {
        words.addAll(Arrays.asList(items));
    }
}
