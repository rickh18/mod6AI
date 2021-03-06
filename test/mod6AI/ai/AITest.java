package mod6AI.ai;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Map;

import static org.junit.Assert.*;

public class AITest {

    private AI aiWithK1;
    private static final String TEST1 = "test";
    private static final String MALE = "Male";
    private static final String FEMALE = "Female";
    private static final String MALE_TEST = "Testing. 1, 2, 3. Male test. This is a test.";
    private static final String FEMALE_TEST = "Testing. 1, 2, 3. Female test. This is a test.";

    @Before
    public void setUp() throws Exception {
        aiWithK1 = new AI(1);
    }

    @Test
    public void testGetVocabularySizeAndGetTotalNumberOfWordsByType() throws Exception {
        assertEquals(0, aiWithK1.getVocabularySize());
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C1));
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C2));
        aiWithK1.train(TEST1, ClassificationType.C1);
        assertEquals(1, aiWithK1.getVocabularySize());
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C1));
        assertEquals(0, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C2));
        aiWithK1.train(TEST1, ClassificationType.C2);
        assertEquals(1, aiWithK1.getVocabularySize());
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C1));
        assertEquals(1, aiWithK1.getTotalNumberOfWordsByType(ClassificationType.C2));
    }

    @Test
    public void testTrainAndClassify() throws Exception {
        aiWithK1.train(MALE, ClassificationType.C1);
        aiWithK1.train(FEMALE, ClassificationType.C2);
        assertEquals(ClassificationType.C1, aiWithK1.classify(MALE));
        assertEquals(ClassificationType.C2, aiWithK1.classify(FEMALE));
        assertEquals(ClassificationType.C1, aiWithK1.classify(MALE_TEST));
        assertEquals(ClassificationType.C2, aiWithK1.classify(FEMALE_TEST));

        aiWithK1.setThreshold(2);
        aiWithK1.train(MALE_TEST, ClassificationType.C1);
        aiWithK1.train(FEMALE_TEST, ClassificationType.C2);
        assertEquals(ClassificationType.C1, aiWithK1.classify(MALE));
        assertEquals(ClassificationType.C2, aiWithK1.classify(FEMALE));
        assertEquals(ClassificationType.C1, aiWithK1.classify(MALE_TEST));
        assertEquals(ClassificationType.C2, aiWithK1.classify(FEMALE_TEST));
    }

    @Test
    public void testGetOccurrencesCount() throws Exception {
        Map<String, Long> occurrences = AI.getOccurrencesCount(
                Tokenizer.tokenize("I don't like Java.\nHowever Java 8 has some pretty nice features. :)"));

        assertEquals(1, occurrences.get("i").longValue());
        assertEquals(1, occurrences.get(Tokenizer.removeAllNoneAlphanumeric("don't")).longValue());
        assertEquals(1, occurrences.get("like").longValue());
        assertEquals(2, occurrences.get("java").longValue());
        assertEquals(1, occurrences.get("however").longValue());
        assertEquals(1, occurrences.get("8").longValue());
        assertEquals(1, occurrences.get("has").longValue());
        assertEquals(1, occurrences.get("some").longValue());
        assertEquals(1, occurrences.get("pretty").longValue());
        assertEquals(1, occurrences.get("nice").longValue());
        assertEquals(1, occurrences.get("features").longValue());
        assertEquals(11, occurrences.size());
    }

    @Test
    public void testCanCreateArffDataFile() throws Exception {
        assertFalse(aiWithK1.canCreateArffDataFile());
        AI ai2 = new AI(1,1, true);
        AI ai3 = new AI(1,1, false);
        assertTrue(ai2.canCreateArffDataFile());
        assertFalse(ai3.canCreateArffDataFile());

        ai2.train(TEST1, ClassificationType.C1);
        ai2.train(TEST1, ClassificationType.C2);

        ai2.createArffDataFile(new PrintWriter(System.out), "test");
    }
}