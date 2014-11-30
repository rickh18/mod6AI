package mod6AI.ai;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Student on 24-11-2014.
 */
public class AI {

    /**
     * A map containing for each {@code ClassificationType} a map containing for each word the frequency.
     */
    private HashMap<ClassificationType, HashMap<String, Long>> wordFreqPerType;
    private HashMap<ClassificationType, Integer> totalNumberOfWordsByType;
    /** The smoothing factor k. */
    private int k;
    /** The size of the total vocabulary. */
    private int vocabularySize;

    /**
     * Initializes a new AI with given smoothing value.
     * @param k the smoothing value
     */
    public AI(int k) {
        this.k = k;
        vocabularySize = 0;
        wordFreqPerType = new HashMap<>();
        wordFreqPerType.put(ClassificationType.MALE, new HashMap<>());
        wordFreqPerType.put(ClassificationType.FEMALE, new HashMap<>());
        totalNumberOfWordsByType = new HashMap<>();
        totalNumberOfWordsByType.put(ClassificationType.MALE, 0);
        totalNumberOfWordsByType.put(ClassificationType.FEMALE, 0);
    }

    /**
     * Gets the size of the total vocabulary.
     * @return the size of the total vocabulary.
     */
    public int getVocabularySize() {
        return vocabularySize;
    }

    /**
     * Gets the total number of words in all documents written by the given type
     * @param type the {@code ClassificationType}
     * @return the total number of words in all documents written by the given type.
     */
    public int getTotalNumberOfWordsByType(ClassificationType type) {
        return totalNumberOfWordsByType.get(type);
    }

    /**
     * Trains the AI for the given type with the given String.
     * @param in a String to train with.
     * @param type the ClassificationType of the training String.
     */
    public synchronized void train(String in, ClassificationType type) {
        Collection<String> tokens = Tokenizer.tokenize(in);

        totalNumberOfWordsByType.put(type, totalNumberOfWordsByType.get(type) + tokens.size());

        Map<String, Long> wordFreq = getOccurrencesCount(tokens);
        Map<String, Long>  wordFreqCurType = wordFreqPerType.get(type);
        wordFreq.forEach((word, count) ->  wordFreqCurType.put(word, wordFreqCurType.getOrDefault(word, 0L) +count));

        HashSet<String> vocabulary = new HashSet<>();
        vocabulary.addAll(wordFreqPerType.get(ClassificationType.MALE).keySet());
        vocabulary.addAll(wordFreqPerType.get(ClassificationType.FEMALE).keySet());
        vocabularySize = vocabulary.size();
    }

    /**
     * Classifies the input.
     * @param in a String to classify.
     * @return {@code ClassificationType.MALE} or {@code ClassificationType.FEMALE}.
     */
    public synchronized ClassificationType classify(String in) {
        Collection<String> tokens = Tokenizer.tokenize(in);

        double chanceM = calculateChanceForType(tokens, ClassificationType.MALE);
        double chanceF = calculateChanceForType(tokens, ClassificationType.FEMALE);

        return chanceM > chanceF ? ClassificationType.MALE : ClassificationType.FEMALE;
    }

    /**
     * Calculate the change that the given token collection is a certain type.
     * Calculation is done in ln space.
     * @param tokens the token collection to test.
     * @param type the type to test for.
     * @return the change that the given token collection indicates the given type.
     */
    private double calculateChanceForType(Collection<String> tokens, ClassificationType type) {
        double chance = tokens.parallelStream().mapToDouble(t -> getChance(t, type)).map(Math::log).sum();

        return Math.exp(chance);
    }

    /**
     * Calculates the chance a word indicates a certain type.
     * @param word the word to test.
     * @param type the type to test for.
     * @return the chance that the given word indicates the given type.
     */
    private double getChance(String word, ClassificationType type) {
        Long wordFreq = wordFreqPerType.get(type).get(word);
        if (wordFreq == null) {
            wordFreq = 0L;
        }

        return (wordFreq + k) / (double) (getTotalNumberOfWordsByType(type) + k * getVocabularySize());
    }

    /**
     * For each word count the times it occurs.
     * @param words a collection of words.
     * @return a HashMap containing for each word the times it occurs.
     */
    public static Map<String, Long> getOccurrencesCount(Collection<String> words) {
        return words.parallelStream().collect(Collectors.groupingBy(o -> o, Collectors.counting()));
    }
}
