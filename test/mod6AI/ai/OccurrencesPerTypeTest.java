package mod6AI.ai;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OccurrencesPerTypeTest {

    OccurrencesPerType occurrencesPerType00;
    OccurrencesPerType occurrencesPerType36;

    @Before
    public void setUp() throws Exception {
        occurrencesPerType00 = new OccurrencesPerType();
        occurrencesPerType36 = new OccurrencesPerType(3, 6);
    }

    @Test
    public void testGetTotal() throws Exception {
        assertEquals(0, occurrencesPerType00.getTotal());
        assertEquals(9, occurrencesPerType36.getTotal());
    }

    @Test
    public void testByType() throws Exception {
        assertEquals(0, occurrencesPerType00.get(ClassificationType.MALE));
        assertEquals(0, occurrencesPerType00.get(ClassificationType.FEMALE));
        assertEquals(3, occurrencesPerType36.get(ClassificationType.MALE));
        assertEquals(6, occurrencesPerType36.get(ClassificationType.FEMALE));

        occurrencesPerType00.set(ClassificationType.MALE, 11);
        assertEquals(11, occurrencesPerType00.get(ClassificationType.MALE));
        assertEquals(0, occurrencesPerType00.get(ClassificationType.FEMALE));
        occurrencesPerType00.set(ClassificationType.FEMALE, 9);
        assertEquals(11, occurrencesPerType00.get(ClassificationType.MALE));
        assertEquals(9, occurrencesPerType00.get(ClassificationType.FEMALE));

        occurrencesPerType00.add(ClassificationType.MALE, 4);
        assertEquals(15, occurrencesPerType00.get(ClassificationType.MALE));
        assertEquals(9, occurrencesPerType00.get(ClassificationType.FEMALE));
        occurrencesPerType00.add(ClassificationType.FEMALE, 27);
        assertEquals(15, occurrencesPerType00.get(ClassificationType.MALE));
        assertEquals(36, occurrencesPerType00.get(ClassificationType.FEMALE));

        assertEquals(occurrencesPerType00, occurrencesPerType00.add(ClassificationType.MALE, 1));
        assertEquals(occurrencesPerType00, occurrencesPerType00.add(ClassificationType.FEMALE, 1));
    }

    @Test
    public void testMale() throws Exception {
        assertEquals(0, occurrencesPerType00.getMale());
        assertEquals(3, occurrencesPerType36.getMale());

        occurrencesPerType00.setMale(11);
        assertEquals(11, occurrencesPerType00.getMale());
        assertEquals(0, occurrencesPerType00.getFemale());

        occurrencesPerType00.addMale(4);
        assertEquals(15, occurrencesPerType00.getMale());
        assertEquals(0, occurrencesPerType00.getFemale());

        assertEquals(occurrencesPerType00, occurrencesPerType00.addMale(1));
    }

    @Test
    public void testFemale() throws Exception {
        assertEquals(0, occurrencesPerType00.getFemale());
        assertEquals(6, occurrencesPerType36.getFemale());

        occurrencesPerType00.setFemale(11);
        assertEquals(11, occurrencesPerType00.getFemale());
        assertEquals(0, occurrencesPerType00.getMale());

        occurrencesPerType00.addFemale(4);
        assertEquals(15, occurrencesPerType00.getFemale());
        assertEquals(0, occurrencesPerType00.getMale());

        assertEquals(occurrencesPerType00, occurrencesPerType00.addFemale(1));
    }
}