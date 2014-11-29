package mod6AI.ai;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Student on 24-11-2014.
 */
public abstract class Tokenizer {

    /**
     * Splits the input String in separate words, cast them to lower and strips all non alphanumerical characters.
     * @param in a String.
     * @return a collection with separate words.
     */
    public static Collection<String> tokenize(String in) {
        Collection<String> out;

        out = Arrays.asList(in.split("\\s")).stream()
                .map(String::toLowerCase)
                .map(Tokenizer::removeAllNoneAlphanumeric)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return out;
    }

    /**
     * Removes all non alphanumeric characters.
     * @param in a String.
     * @return the input String from which all none alphanumeric characters are removed.
     */
    public static String removeAllNoneAlphanumeric(String in) {
        // TODO: maybe keep some special chars under special conditions. For example: ' in don't
        return in.replaceAll("[^a-zA-Z0-9]", "");
    }
}
