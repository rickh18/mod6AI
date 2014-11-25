package mod6AI.ai;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Student on 24-11-2014.
 */
public class AI {

    private HashMap<ClassificationType, HashMap<String, Integer>> wordFreqPerType;
    private int k;

    /**
     * Initializes a new AI with given smoothing value.
     * @param k the smoothing value
     */
    public AI(int k) {
        this.k = k;
        wordFreqPerType = new HashMap<>();
        wordFreqPerType.put(ClassificationType.MALE, new HashMap<>());
        wordFreqPerType.put(ClassificationType.FEMALE, new HashMap<>());
    }

    /**
     * (Re)trains the AI for the given type with the given String.
     * @param in a String to train with.
     * @param type the ClassificationType of the training String.
     */
    public void train(String in, ClassificationType type) {
        Collection<String> tokens = Tokenizer.tokenize(in);

        Map<String, Integer> wordFreq = getOccurrencesCount(tokens);
        for (String word : wordFreq.keySet()) {
            Integer currentFreq = wordFreqPerType.get(type).get(word);
            if (currentFreq == null) {
                currentFreq = 0;
            }
            wordFreqPerType.get(type).put(word, currentFreq + wordFreq.get(word));
        }
    }

    private int getVocabularySize() {
        HashSet<String> vocabulary = new HashSet<>();
        vocabulary.addAll(wordFreqPerType.get(ClassificationType.MALE).keySet());
        vocabulary.addAll(wordFreqPerType.get(ClassificationType.FEMALE).keySet());
        return vocabulary.size();
    }

    private int getVocabularySizeOfType(ClassificationType type) {
        return wordFreqPerType.get(type).keySet().size();
    }

    /**
     * Classifies the input.
     * @param in a String to classify.
     * @return {@code ClassificationType.MALE} or {@code ClassificationType.FEMALE}.
     */
    public ClassificationType classify(String in) {
        Collection<String> tokens = Tokenizer.tokenize(in);
        double chanceM = chanceForType(tokens, ClassificationType.MALE);
        double chanceF = chanceForType(tokens, ClassificationType.FEMALE);

        return chanceM > chanceF ? ClassificationType.MALE : ClassificationType.FEMALE;
    }

    private double chanceForType(Collection<String> tokens, ClassificationType type) {
        double chance = tokens.stream().mapToDouble(t -> getChance(t, type)).map(Math::log).sum();

        return Math.pow(Math.E, chance);
    }

    /**
     * Calculates the chance a word indicates a certain type.
     * @param word the word to test.
     * @param type the type to test for.
     * @return the chance that the given word indicates the given type.
     */
    private double getChance(String word, ClassificationType type) {
        Integer wordFreq = wordFreqPerType.get(type).get(word);
        if (wordFreq == null) {
            wordFreq = 0;
        }

        return (wordFreq + k) / (getVocabularySizeOfType(type) + k * getVocabularySize());
    }

    /**
     * For each word count the times it occurs.
     * @param words a collection of words.
     * @return a HashMap containing for each word the times it occurs.
     */
    public static Map<String, Integer> getOccurrencesCount(Collection<String> words) {
        HashMap<String, Integer> out = new HashMap<>();
        HashSet<String> uniqueWords = new HashSet<>();

        uniqueWords.addAll(words);

        for (String uWord : uniqueWords) {
            int count = 0;
            for (String word : words) {
                if (word.equals(uWord)) {
                    count++;
                }
            }
            out.put(uWord, count);
        }

        return out;
    }
}
