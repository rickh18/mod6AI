package mod6AI.ai;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Student on 24-11-2014.
 */
public class AI {

    HashMap<ClassificationType, HashMap<String, Double>> wordChangeGivenType;

    public AI() {
        wordChangeGivenType = new HashMap<>();
        wordChangeGivenType.put(ClassificationType.MALE, new HashMap<>());
        wordChangeGivenType.put(ClassificationType.FEMALE, new HashMap<>());
    }

    /**
     * (Re)trains the AI for the given type with the given String.
     * @param in a String to train with.
     * @param type the ClassificationType of the training String.
     */
    public void train(String in, ClassificationType type) {
        Collection<String> tokens = Tokenizer.tokenize(in);
        double count = tokens.stream().count();
        Map<String, Integer> wordFreq = getOccurrencesCount(tokens);
        for (String word : wordFreq.keySet()) {
            Double chance = wordFreq.get(word) / count;
            wordChangeGivenType.get(type).put(word, chance);
        }
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
