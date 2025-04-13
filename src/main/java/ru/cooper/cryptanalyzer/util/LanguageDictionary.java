package ru.cooper.cryptanalyzer.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * A simple word dictionary used for language detection and validation during cryptanalysis.
 * <p>
 * Supports both Russian and English dictionaries. Designed to be immutable and thread-safe.
 */
public final class LanguageDictionary {

    public final Set<String> words;

    /**
     * Constructs a {@code LanguageDictionary} with the specified set of words.
     *
     * @param words a set of valid language words
     */
    private LanguageDictionary(Set<String> words) {
        this.words = Collections.unmodifiableSet(words);
    }

    /**
     * Creates a Russian language dictionary with common pronouns, verbs, adjectives, nouns,
     * and modern terms.
     *
     * @return a {@code LanguageDictionary} instance for the Russian language
     */
    public static LanguageDictionary createRussianDictionary() {
        Set<String> russianWords = new HashSet<>(200);

        // Местоимения и частицы
        addWords(russianWords, "я", "ты", "он", "она", "мы", "вы", "они",
                "это", "то", "все", "что", "как", "так", "да", "нет");

        // Предлоги и союзы
        addWords(russianWords, "в", "на", "с", "по", "за", "из", "от", "до", "для",
                "и", "а", "но", "или", "если", "чтобы", "когда", "потому");

        // Вопросы
        addWords(russianWords, "кто", "что", "где", "куда", "откуда", "почему", "зачем", "какой");

        // Приветствия и общение
        addWords(russianWords, "привет", "здравствуй", "здравствуйте", "добрый",
                "утро", "вечер", "спасибо", "пожалуйста", "извини",
                "прости", "пока", "свидания");

        // Глаголы
        addWords(russianWords, "быть", "есть", "был", "была", "было", "стать",
                "сказать", "говорить", "знать", "хотеть", "мочь", "делать",
                "идти", "прийти", "видеть", "смотреть", "любить", "жить",
                "работать", "думать", "понимать", "ждать", "дать", "взять");

        // Существительные
        addWords(russianWords, "человек", "мужчина", "женщина", "ребенок", "друг",
                "мать", "отец", "брат", "сестра", "семья", "имя", "время",
                "день", "год", "работа", "дом", "жизнь");

        // Прилагательные
        addWords(russianWords, "хороший", "плохой", "новый", "старый", "большой",
                "маленький", "должный", "первый", "последний", "русский",
                "другой", "каждый", "самый", "весь");

        // Числительные
        addWords(russianWords, "один", "два", "три", "четыре", "пять", "десять",
                "сто", "тысяча");

        // Наречия
        addWords(russianWords, "здесь", "там", "тут", "сейчас", "тогда", "очень",
                "много", "мало", "хорошо", "плохо", "быстро", "медленно",
                "вместе", "снова", "почти", "уже");

        // Современные слова
        addWords(russianWords, "компьютер", "телефон", "интернет", "программа",
                "файл", "сообщение", "пароль");

        return new LanguageDictionary(russianWords);
    }

    /**
     * Creates an English language dictionary with basic vocabulary, including
     * pronouns, verbs, common adjectives, and modern terms.
     *
     * @return a {@code LanguageDictionary} instance for the English language
     */
    public static LanguageDictionary createEnglishDictionary() {
        Set<String> englishWords = new HashSet<>();

        // Pronouns
        addWords(englishWords, "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them");

        // Possessive Pronouns
        addWords(englishWords, "my", "your", "his", "her", "its", "our", "their");

        // Demonstratives
        addWords(englishWords, "this", "that", "these", "those");

        // Question Words
        addWords(englishWords, "what", "who", "whom", "which", "whose", "when", "where", "why", "how");

        // Auxiliary Verbs
        addWords(englishWords, "be", "am", "is", "are", "was", "were", "been", "being");
        addWords(englishWords, "have", "has", "had", "having");
        addWords(englishWords, "do", "does", "did", "doing");

        // Modal Verbs
        addWords(englishWords, "can", "could", "may", "might", "shall", "should", "will", "would", "must", "ought");

        // Common Verbs
        addWords(englishWords, "say", "get", "make", "go", "know", "think", "take", "see", "come", "want", "look",
                "use", "find", "give", "tell", "work", "call", "try", "ask", "need", "feel", "become", "leave", "put",
                "mean", "keep", "let", "begin", "seem", "help", "talk", "turn", "start", "show", "hear", "play", "run",
                "move", "live", "believe", "bring", "happen", "write", "provide", "sit", "stand", "lose", "pay", "meet",
                "include", "continue", "set", "learn", "change", "lead", "understand", "watch", "follow", "stop",
                "create", "speak", "read", "allow", "add", "spend", "grow", "open", "walk", "win", "offer", "remember",
                "love", "consider", "appear", "buy", "wait", "serve", "die", "send", "expect", "build", "stay", "fall",
                "cut", "reach", "kill", "remain");

        // Common Nouns
        addWords(englishWords, "time", "person", "year", "way", "day", "thing", "man", "world", "life", "hand",
                "part", "child", "eye", "woman", "place", "work", "week", "case", "point", "government", "company",
                "number", "group", "problem", "fact", "home", "water", "room", "mother", "area", "money", "story",
                "issue", "side", "school", "state", "family", "student", "group", "country", "city", "community",
                "name", "president", "team", "minute", "idea", "kid", "body", "information", "back", "parent",
                "face", "others", "level", "office", "door", "health", "person", "art", "war", "history", "party",
                "result", "change", "morning", "reason", "research", "girl", "guy", "moment", "air", "teacher",
                "force", "education");

        // Common Adjectives
        addWords(englishWords, "good", "new", "first", "last", "long", "great", "little", "own",
                "other", "old", "right", "big", "high", "different", "small", "large", "next", "early",
                "young", "important", "few", "public", "bad", "same", "able");

        // Common Adverbs
        addWords(englishWords, "up", "so", "out", "just", "now", "how", "then", "more", "also",
                "here", "well", "only", "very", "even", "back", "there", "down", "still", "in", "as",
                "too", "when", "never", "really", "most");

        // Prepositions
        addWords(englishWords, "in", "to", "of", "for", "on", "with", "at", "by", "from", "up",
                "about", "into", "over", "after", "beneath", "under", "above");

        // Conjunctions
        addWords(englishWords, "and", "but", "or", "because", "so", "although", "if", "while", "whereas");

        // Articles
        addWords(englishWords, "a", "an", "the");

        // Numbers
        addWords(englishWords, "one", "two", "three", "four", "five", "six", "seven",
                "eight", "nine", "ten", "hundred", "thousand");

        // Common Expressions
        addWords(englishWords, "hello", "hi", "thanks", "thank", "please", "sorry",
                "yes", "no", "okay", "ok", "bye", "goodbye");

        return new LanguageDictionary(englishWords);
    }


    /**
     * Checks whether the given word exists in the dictionary.
     * The check is case-insensitive.
     *
     * @param word the word to check
     * @return true if the word is in the dictionary; false otherwise
     */
    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }

    /**
     * Returns the full set of words in this dictionary.
     * The returned set is unmodifiable.
     *
     * @return a set of words
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Adds an array of words to the provided set.
     *
     * @param set   the set to add words to
     * @param items words to add
     */
    private static void addWords(Set<String> set, String... items) {
        set.addAll(Arrays.asList(items));
    }
}
