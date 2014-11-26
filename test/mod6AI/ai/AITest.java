package mod6AI.ai;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class AITest {

    private AI aiWithK1;
    private static final String TEST1 = "test";
    private static final String MALE = "Male";
    private static final String FEMALE = "Female";

    @Before
    public void setUp() throws Exception {
        aiWithK1 = new AI(1);
    }

    @Test
    public void testGetVocabularySizeAndGetTotalNumberOfWordsByType() throws Exception {
        assertEquals(0, aiWithK1.getVocabularySize());
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.MALE));
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.FEMALE));
        aiWithK1.train(TEST1, ClassificationType.MALE);
        assertEquals(1, aiWithK1.getVocabularySize());
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.MALE));
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.FEMALE));
        aiWithK1.train(TEST1, ClassificationType.FEMALE);
        assertEquals(1, aiWithK1.getVocabularySize());
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.MALE));
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.FEMALE));
    }

    @Test
    public void testTrainAndClassify() throws Exception {
        aiWithK1.train(MALE, ClassificationType.MALE);
        aiWithK1.train(FEMALE, ClassificationType.FEMALE);
        assertEquals(ClassificationType.MALE, aiWithK1.classify(MALE));
        assertEquals(ClassificationType.FEMALE, aiWithK1.classify(FEMALE));
    }

    @Test
    public void testGetOccurrencesCount() throws Exception {
        Map<String, Integer> occurrences = AI.getOccurrencesCount(
                Tokenizer.tokenize("I don't like Java.\nHowever Java 8 has some pretty nice features. :)"));

        assertEquals(1, (int) occurrences.get("i"));
        assertEquals(1, (int) occurrences.get(Tokenizer.removeAllNoneAlphanumeric("don't")));
        assertEquals(1, (int) occurrences.get("like"));
        assertEquals(2, (int) occurrences.get("java"));
        assertEquals(1, (int) occurrences.get("however"));
        assertEquals(1, (int) occurrences.get("8"));
        assertEquals(1, (int) occurrences.get("has"));
        assertEquals(1, (int) occurrences.get("some"));
        assertEquals(1, (int) occurrences.get("pretty"));
        assertEquals(1, (int) occurrences.get("nice"));
        assertEquals(1, (int) occurrences.get("features"));
        assertEquals(11, occurrences.size());
    }
}