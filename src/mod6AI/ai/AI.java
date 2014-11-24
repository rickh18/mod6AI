package mod6AI.ai;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Student on 24-11-2014.
 */
public class AI {
    HashMap<String, Double> wordChangeGivenMale;

    public AI() {
        wordChangeGivenMale = new HashMap<>();
    }

    public void train(String in) {
        Collection<String> tokens = Tokenizer.tokenize(in);
        long count = tokens.stream().count();
        // TODO: ...
    }

    /**
     * For each word count the times it occurs.
     * @param words a collection of words.
     * @return a HashMap containing for each word the times it occurs.
     */
    public static Map<String, Integer> getOccurenceCount(Collection<String> words) {
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
