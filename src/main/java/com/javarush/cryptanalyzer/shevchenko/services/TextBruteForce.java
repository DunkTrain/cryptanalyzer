package com.javarush.cryptanalyzer.shevchenko.services;

import com.javarush.cryptanalyzer.shevchenko.constants.CryptoAlphabet;
import com.javarush.cryptanalyzer.shevchenko.constants.Dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализует взлом шифра Цезаря методом полного перебора с анализом частотности символов,
 * структуры текста и совпадений со словарем.
 */
public class TextBruteForce extends TextDecoder {

    private static final double FREQUENCY_MULTIPLIER = 10.0;
    private static final double STRUCTURE_WEIGHT = 3.0;
    private static final double FREQUENCY_WEIGHT = 10.0;
    private static final double DICTIONARY_WEIGHT = 7.0;

    /** Статистика частоты букв в русском языке. */
    private static final Map<Character, Double> RUSSIAN_LETTER_FREQUENCY = createFrequencyMap();

    /** Регулярное выражение для проверки структуры осмысленного текста. */
    private static final String MEANINGFUL_TEXT_REGEX = "^\\W*[а-яА-Я]+([\\s,.:;-]+[а-яА-Я]+)*";
    private static final Pattern MEANINGFUL_PATTERN = Pattern.compile(MEANINGFUL_TEXT_REGEX);

    private static Map<Character, Double> createFrequencyMap() {
        Map<Character, Double> frequencyMap = new HashMap<>();
        frequencyMap.put('о', 0.1097); frequencyMap.put('е', 0.0849);
        frequencyMap.put('а', 0.0801); frequencyMap.put('и', 0.0735);
        frequencyMap.put('н', 0.0669); frequencyMap.put('т', 0.0666);
        frequencyMap.put('с', 0.0574); frequencyMap.put('р', 0.0545);
        frequencyMap.put('в', 0.0473); frequencyMap.put('л', 0.0473);
        frequencyMap.put('к', 0.0352); frequencyMap.put('м', 0.0321);
        frequencyMap.put('д', 0.0249); frequencyMap.put('п', 0.0233);
        frequencyMap.put('у', 0.0217); frequencyMap.put('я', 0.0201);
        frequencyMap.put('г', 0.0170); frequencyMap.put('з', 0.0094);
        frequencyMap.put('б', 0.0094); frequencyMap.put('ч', 0.0072);
        frequencyMap.put('й', 0.0072); frequencyMap.put('ь', 0.0072);
        frequencyMap.put('ю', 0.0072); frequencyMap.put('ш', 0.0049);
        frequencyMap.put('щ', 0.0036); frequencyMap.put('ц', 0.0036);
        frequencyMap.put('э', 0.0036); frequencyMap.put('ж', 0.0023);
        frequencyMap.put('ф', 0.0023); frequencyMap.put('ъ', 0.0011);
        frequencyMap.put('ё', 0.0011);
        return frequencyMap;
    }

    /**
     * Выполняет перебор всех возможных ключей для расшифровки текста.
     *
     * @param encryptedText зашифрованный текст
     * @return результат с наилучшим вариантом расшифровки
     */
    public static BruteForceResult bruteForce(String encryptedText) {
        BruteForceResult bestResult = new BruteForceResult();
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int key = 0; key < CryptoAlphabet.LENGTH_ALPHABET; key++) {
            String decryptedText = decrypt(encryptedText, key);
            double score = calculateTextQuality(decryptedText);

            if (score > bestScore) {
                bestScore = score;
                bestResult = new BruteForceResult(decryptedText, key, score);
            }
        }

        return bestResult;
    }

    /**
     * Вычисляет комплексную оценку качества расшифрованного текста.
     *
     * @param text текст для оценки
     * @return числовая оценка качества (чем выше, тем лучше)
     */
    private static double calculateTextQuality(String text) {
        String trimmedText = text.trim();
        Matcher matcher = MEANINGFUL_PATTERN.matcher(trimmedText);
        double structureScore = matcher.matches() ? STRUCTURE_WEIGHT : 0.0;
        double frequencyScore = analyzeLetterFrequency(trimmedText);
        double dictionaryScore = analyzeDictionaryMatch(trimmedText);
        double lengthScore = Math.min(trimmedText.replaceAll("[^а-яА-Я]", "").length() / 10.0, 5.0);

        return structureScore + frequencyScore + dictionaryScore + lengthScore;
    }

    /**
     * Анализирует соответствие частот букв эталонным значениям.
     *
     * @param text текст для анализа
     * @return оценка соответствия частот (0.0 - 1.0)
     */
    private static double analyzeLetterFrequency(String text) {
        Map<Character, Integer> letterCount = new HashMap<>();
        int totalLetters = 0;

        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        if (totalLetters == 0) return 0.0;

        int finalTotalLetters = totalLetters;
        double frequencyScore = RUSSIAN_LETTER_FREQUENCY.entrySet().stream()
                .mapToDouble(entry -> {
                    double expected = entry.getValue();
                    double actual = letterCount.getOrDefault(entry.getKey(), 0) / (double) finalTotalLetters;
                    return 1.0 - Math.abs(expected - actual);
                })
                .sum() * FREQUENCY_MULTIPLIER;

        return frequencyScore * FREQUENCY_WEIGHT / FREQUENCY_MULTIPLIER;
    }

    /**
     * Оценивает процент совпадения слов текста со словарем.
     *
     * @param text текст для анализа
     * @return оценка от 0.0 до DICTIONARY_WEIGHT
     */
    private static double analyzeDictionaryMatch(String text) {
        String cleanText = text.replaceAll("[^а-яА-Я\\s]", "").toLowerCase().trim();
        String[] words = cleanText.split("\\s+");

        if (words.length == 0) return 0.0;

        int matches = 0;
        Set<String> shortWords = Set.of("я", "ты", "он", "мы", "вы", "и", "а", "но", "в", "на", "с", "по", "за", "из", "от", "до", "да", "нет");

        for (String word : words) {
            if (word.length() > 2 && Dictionary.COMMON_RUSSIAN_WORDS.contains(word)) {
                matches++;
            } else if (word.length() <= 2 && shortWords.contains(word)) {
                matches++;
            }
        }

        double matchPercentage = (double) matches / words.length;
        return Math.min(matchPercentage * DICTIONARY_WEIGHT * 2, DICTIONARY_WEIGHT);
    }

    /**
     * Содержит результаты расшифровки методом brute-force.
     */
    public static class BruteForceResult {
        private final String decryptedText;
        private final int key;
        private final double confidenceScore;

        /** Создает пустой результат. */
        public BruteForceResult() {
            this("", 0, 0.0);
        }

        /**
         * Создает результат с указанными параметрами.
         *
         * @param decryptedText расшифрованный текст
         * @param key использованный ключ
         * @param confidenceScore оценка достоверности
         */
        public BruteForceResult(String decryptedText, int key, double confidenceScore) {
            this.decryptedText = decryptedText;
            this.key = key;
            this.confidenceScore = confidenceScore;
        }

        public String getDecryptedText() {
            return decryptedText;
        }

        public int getKey() {
            return key;
        }

        public double getConfidenceScore() {
            return confidenceScore;
        }

        @Override
        public String toString() {
            return String.format("Расшифрованный текст (ключ %d, уверенность %.2f%%): %s",
                    key, confidenceScore * 10, decryptedText);
        }
    }
}
