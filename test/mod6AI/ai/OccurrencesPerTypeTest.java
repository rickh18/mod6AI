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
        assertEquals(0, (long) occurrencesPerType00.get(ClassificationType.C1));
        assertEquals(0, (long) occurrencesPerType00.get(ClassificationType.C2));
        assertEquals(3, (long) occurrencesPerType36.get(ClassificationType.C1));
        assertEquals(6, (long) occurrencesPerType36.get(ClassificationType.C2));

        occurrencesPerType00.set(ClassificationType.C1, 11);
        assertEquals(11, (long) occurrencesPerType00.get(ClassificationType.C1));
        assertEquals(0, (long) occurrencesPerType00.get(ClassificationType.C2));
        occurrencesPerType00.set(ClassificationType.C2, 9);
        assertEquals(11, (long) occurrencesPerType00.get(ClassificationType.C1));
        assertEquals(9, (long) occurrencesPerType00.get(ClassificationType.C2));

        occurrencesPerType00.add(ClassificationType.C1, 4);
        assertEquals(15, (long) occurrencesPerType00.get(ClassificationType.C1));
        assertEquals(9, (long) occurrencesPerType00.get(ClassificationType.C2));
        occurrencesPerType00.add(ClassificationType.C2, 27);
        assertEquals(15, (long) occurrencesPerType00.get(ClassificationType.C1));
        assertEquals(36, (long) occurrencesPerType00.get(ClassificationType.C2));

        assertEquals(occurrencesPerType00, occurrencesPerType00.add(ClassificationType.C1, 1));
        assertEquals(occurrencesPerType00, occurrencesPerType00.add(ClassificationType.C2, 1));
    }

    @Test
    public void testMale() throws Exception {
        assertEquals(0, occurrencesPerType00.getC1());
        assertEquals(3, occurrencesPerType36.getC1());

        occurrencesPerType00.setC1(11);
        assertEquals(11, occurrencesPerType00.getC1());
        assertEquals(0, occurrencesPerType00.getC2());

        occurrencesPerType00.addC1(4);
        assertEquals(15, occurrencesPerType00.getC1());
        assertEquals(0, occurrencesPerType00.getC2());

        assertEquals(occurrencesPerType00, occurrencesPerType00.addC1(1));
    }

    @Test
    public void testFemale() throws Exception {
        assertEquals(0, occurrencesPerType00.getC2());
        assertEquals(6, occurrencesPerType36.getC2());

        occurrencesPerType00.setC2(11);
        assertEquals(11, occurrencesPerType00.getC2());
        assertEquals(0, occurrencesPerType00.getC1());

        occurrencesPerType00.addC2(4);
        assertEquals(15, occurrencesPerType00.getC2());
        assertEquals(0, occurrencesPerType00.getC1());

        assertEquals(occurrencesPerType00, occurrencesPerType00.addC2(1));
    }
}