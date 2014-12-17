package mod6AI.ai;

import java.io.Serializable;

/**
* Created by Student on 30-11-2014.
*/
public class OccurrencesPerType implements Serializable {

    /** The number of occurrences for {@code ClassificationType.C1}. */
    private long c1;
    /** The number of occurrences for {@code ClassificationType.C2}. */
    private long c2;

    /**
     * Initializes a OccurrencesPerType with the given values for both types.
     * @param c1 number of occurrences for {@code ClassificationType.C1}.
     * @param c2 number of occurrences for {@code ClassificationType.C2}.
     */
    public OccurrencesPerType(long c1, long c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * Initializes a OccurrencesPerType with zero occurrences for both types.
     */
    public OccurrencesPerType() {
        this(0, 0);
    }

    /**
     * Gets the total number of occurrences.
     * @return the total number of occurrences.
     */
    public long getTotal() {
        return c1 + c2;
    }

    /**
     * Gets the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @return The number of occurrences for males.
     */
    public Long get(ClassificationType type) {
        switch (type) {
            case C1:
                return getC1();
            case C2:
                return getC2();
            default:
                return null;
        }
    }

    /**
     * Sets the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @param value the number of occurrences.
     */
    public void set(ClassificationType type, long value) {
        switch (type) {
            case C1:
                setC1(value);
                break;
            case C2:
                setC2(value);
                break;
        }
    }

    /**
     * Adds the given value to the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @param value the value to add.
     * @return this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType add(ClassificationType type, long value) {
        switch (type) {
            case C1:
                addC1(value);
                break;
            case C2:
                addC2(value);
                break;
        }

        return this;
    }

    /**
     * Gets the number of occurrences for {@code ClassificationType.C1}.
     * @return The number of occurrences for {@code ClassificationType.C1}.
     */
    public long getC1() {
        return c1;
    }

    /**
     * Sets the number of occurrences for {@code ClassificationType.C1}.
     * @param value the number of occurrences.
     */
    public void setC1(long value) {
        c1 = value;
    }

    /**
     * Adds the given value to the number of occurrences for {@code ClassificationType.C1}.
     * @param value the value to add.
     * @return this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType addC1(long value) {
        this.c1 += value;
        return this;
    }

    /**
     * Gets the number of occurrences for {@code ClassificationType.C2}.
     * @return The number of occurrences for {@code ClassificationType.C2}.
     */
    public long getC2() {
        return c2;
    }

    /**
     * Sets the number of occurrences for {@code ClassificationType.C2}.
     * @param value the number of occurrences.
     */
    public void setC2(long value) {
        c2 = value;
    }

    /**
     * Adds the given value to the number of occurrences for {@code ClassificationType.C2}.
     * @param value the value to add.
     * @return this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType addC2(long value) {
        c2 += value;
        return this;
    }
}
