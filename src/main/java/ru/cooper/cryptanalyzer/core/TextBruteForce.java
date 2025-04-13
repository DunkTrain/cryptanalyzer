package ru.cooper.cryptanalyzer.core;

import ru.cooper.cryptanalyzer.util.LanguageDictionary;
import ru.cooper.cryptanalyzer.util.LanguageProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Performs brute-force decryption of a Caesar cipher by iterating over all possible keys
 * and evaluating each result using a scoring system based on character frequency,
 * textual structure, dictionary matches, and text length.
 */
public class TextBruteForce {

    private static final double FREQUENCY_MULTIPLIER = 10.0;
    private static final double STRUCTURE_WEIGHT = 3.0;
    private static final double FREQUENCY_WEIGHT = 10.0;
    private static final double DICTIONARY_WEIGHT = 7.0;
    private static final double LENGTH_WEIGHT = 2.0;

    private final List<LanguageProfile> profiles;

    /**
     * Constructs a new {@code TextBruteForce} instance with a list of language profiles.
     *
     * @param profiles list of {@link LanguageProfile} objects to evaluate against
     * @throws IllegalArgumentException if the list is null or empty
     */
    public TextBruteForce(List<LanguageProfile> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            throw new IllegalArgumentException("At least one LanguageProfile must be provided");
        }
        this.profiles = profiles;
    }

    /**
     * Attempts to decrypt the given Caesar-encrypted text by testing all possible keys
     * and evaluating the likelihood of the correct decryption using multiple scoring criteria.
     *
     * @param encryptedText the encrypted input string
     * @return a {@link BruteForceResult} containing the best decryption attempt
     */
    public BruteForceResult bruteForce(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return new BruteForceResult("", 0, 0.0, "Input text is null or empty");
        }

        BruteForceResult bestResult = new BruteForceResult();
        double bestScore = Double.NEGATIVE_INFINITY;

        for (LanguageProfile profile : profiles) {

            if (encryptedText.length() < profile.getMinTextLength()) {
                continue;
            }

            TextDecoder decoder = new TextDecoder(profile.getAlphabet());
            int alphabetLength = profile.getAlphabet().length();

            for (int key = 0; key < alphabetLength; key++) {
                String decryptedText = decoder.decrypt(encryptedText, key);
                double score = calculateTextQuality(decryptedText, profile);

                if (score > bestScore) {
                    bestScore = score;
                    bestResult = new BruteForceResult(
                            decryptedText,
                            key,
                            score,
                            String.format("Language: %s", profile.getAlphabet().getClass().getSimpleName())
                    );
                }
            }
        }

        if (bestResult.getConfidenceScore() < 0.3) {
            bestResult = new BruteForceResult(
                    bestResult.getDecryptedText(),
                    bestResult.getKey(),
                    bestResult.getConfidenceScore(),
                    bestResult.getMessage() + " | Low confidence in the result."
            );
        }

        return bestResult;
    }


    /**
     * Calculates a quality score for a decrypted text based on structural patterns,
     * letter frequencies, dictionary word matches, and length heuristics.
     *
     * @param text    the decrypted text to evaluate
     * @param profile the language profile used for scoring
     * @return a numeric score representing the likelihood of correct decryption
     */
    private double calculateTextQuality(String text, LanguageProfile profile) {
        String trimmedText = text.trim();
        if (trimmedText.length() < profile.getMinTextLength()) {
            return 0.0;
        }

        Matcher meaningfulMatcher = profile.getMeaningfulPattern().matcher(trimmedText);
        double structureScore = meaningfulMatcher.matches() ? STRUCTURE_WEIGHT : 0.0;

        Matcher spacesMatcher = profile.getSpacesPattern().matcher(trimmedText);
        if (spacesMatcher.find()) {
            structureScore += 1.0;
        }

        double frequencyScore = analyzeLetterFrequency(trimmedText, profile);

        double dictionaryScore = analyzeDictionaryMatch(trimmedText, profile.getDictionary());

        int letterCount = 0;
        for (char c : trimmedText.toCharArray()) {
            if (profile.getAlphabet().contains(c)) {
                letterCount++;
            }
        }

        double lengthScore = Math.min(letterCount / 20.0, 5.0) * LENGTH_WEIGHT;

        return structureScore + frequencyScore + dictionaryScore + lengthScore;
    }

    /**
     * Evaluates how closely the letter frequency of the text matches the expected frequency
     * from the language profile.
     *
     * @param text    the decrypted text
     * @param profile the language profile containing expected frequencies
     * @return a score based on frequency similarity
     */
    private double analyzeLetterFrequency(String text, LanguageProfile profile) {
        Map<Character, Integer> letterCount = new HashMap<>();
        int totalLetters = 0;

        for (char c : text.toLowerCase().toCharArray()) {
            if (profile.getAlphabet().contains(c)) {
                letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        if (totalLetters == 0) {
            return 0.0;
        }

        double sumScore = 0.0;
        for (Map.Entry<Character, Double> entry : profile.getLetterFrequency().entrySet()) {
            char letter = entry.getKey();
            double expected = entry.getValue();
            double actual = letterCount.getOrDefault(letter, 0) / (double) totalLetters;
            sumScore += (1.0 - Math.abs(expected - actual));
        }

        double frequencyScore = sumScore * FREQUENCY_MULTIPLIER;
        return frequencyScore * FREQUENCY_WEIGHT / FREQUENCY_MULTIPLIER;
    }

    /**
     * Evaluates the percentage of known words in the text that match entries
     * in the provided language dictionary.
     *
     * @param text      the decrypted text
     * @param dictionary the {@link LanguageDictionary} containing known words
     * @return a score based on dictionary match ratio
     */
    private double analyzeDictionaryMatch(String text, LanguageDictionary dictionary) {
        String cleanText = text.replaceAll("[^\\p{L}\\s]", "").toLowerCase().trim();
        String[] words = cleanText.split("\\s+");
        if (words.length == 0) {
            return 0.0;
        }

        int matches = 0;
        for (String word : words) {
            if (dictionary.contains(word)) {
                matches++;
            }
        }

        double matchPercentage = (double) matches / words.length;
        return Math.min(matchPercentage * DICTIONARY_WEIGHT * 2, DICTIONARY_WEIGHT);
    }

    /**
     * Represents the result of the Caesar cipher brute-force attack.
     */
    public static class BruteForceResult {

        private final String decryptedText;
        private final int key;
        private final double confidenceScore;
        private final String message;

        /**
         * Default constructor with empty result.
         */
        public BruteForceResult() {
            this("", 0, 0.0, "");
        }

        /**
         * Constructs a new result with the given parameters.
         *
         * @param decryptedText   the decrypted text
         * @param key             the decryption key used
         * @param confidenceScore score indicating decryption confidence
         * @param message         additional info or explanation
         */
        public BruteForceResult(String decryptedText, int key, double confidenceScore, String message) {
            this.decryptedText = decryptedText;
            this.key = key;
            this.confidenceScore = confidenceScore;
            this.message = message;
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

        public String getMessage() {
            return message;
        }

        /**
         * Checks if the result contains a non-empty message.
         *
         * @return true if a message is present
         */
        public boolean hasMessage() {
            return message != null && !message.isEmpty();
        }

        @Override
        public String toString() {
            String result = String.format(
                    "Decrypted text (key %d, confidence %.2f%%): %s",
                    key, confidenceScore * 10, decryptedText
            );
            if (hasMessage()) {
                result += "\nMessage: " + message;
            }
            return result;
        }
    }
}
