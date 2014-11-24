package mod6AI.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Student on 24-11-2014.
 */
public class Util {

    /**
     * Removes all non alphanumeric characters.
     * @param in a String.
     * @return the input String from which all none alphanumeric characters are removed.
     */
    public static String removeAllNoneAlphanumeric(String in) {
        return in.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Converts all words to lowercase.
     * @param in a collection of words.
     * @return a collection of words in lowercase.
     */
    public static Collection<String> toLower(Collection<String> in) {
        Collection<String> out = new ArrayList<String>();
        for (String s : in) {
            out.add(s.toLowerCase());
        }

        return out;
    }

    /**
     * For each word count the times it occurs.
     * @param in a collection of words.
     * @return a HashMap containing for each word the times it occurs.
     */
    public static HashMap<String, Integer> getOccurenceCount(Collection<String> in) {
        HashMap<String, Integer> out = new HashMap<String, Integer>();
        HashSet<String> set = new HashSet<String>();

        set.addAll(in);

        for (String s1 : set) {
            int count = 0;
            for (String s2 : in) {
                if (s2.equals(s1)) {
                    count++;
                }
            }
            out.put(s1, count);
        }

        return out;
    }
}
