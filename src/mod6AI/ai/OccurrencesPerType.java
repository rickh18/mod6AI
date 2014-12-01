package mod6AI.ai;

import mod6AI.exceptions.UnsupportedTypeException;

/**
* Created by Student on 30-11-2014.
*/
public class OccurrencesPerType {

    /** The number of occurrences for males. */
    private long male;
    /** The number of occurrences for females. */
    private long female;

    /**
     * Initializes a OccurrencesPerType with the given values for male and female.
     * @param male number of occurrences for males.
     * @param female number of occurrences for females.
     */
    public OccurrencesPerType(long male, long female) {
        this.male = male;
        this.female = female;
    }

    /**
     * Initializes a OccurrencesPerType with zero occurrences for male and female.
     */
    public OccurrencesPerType() {
        this(0, 0);
    }

    /**
     * Gets the total number of occurrences.
     * @return the total number of occurrences.
     */
    public long getTotal() {
        return male + female;
    }

    /**
     * Gets the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @return The number of occurrences for males.
     */
    public long get(ClassificationType type) throws UnsupportedTypeException {
        switch (type) {
            case MALE:
                return getMale();
            case FEMALE:
                return getFemale();
            default:
                throw new UnsupportedTypeException();
        }
    }

    /**
     * Sets the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @param value the number of occurrences.
     */
    public void set(ClassificationType type, long value) throws UnsupportedTypeException {
        switch (type) {
            case MALE:
                setMale(value);
                break;
            case FEMALE:
                setFemale(value);
                break;
            default:
                throw new UnsupportedTypeException();
        }
    }

    /**
     * Adds the given value to the number of occurrences for the given {@code ClassificationType}.
     * @param type the {@code ClassificationType}.
     * @param value the value to add.
     * @return a reference to this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType add(ClassificationType type, long value) throws UnsupportedTypeException {
        switch (type) {
            case MALE:
                addMale(value);
                break;
            case FEMALE:
                addFemale(value);
                break;
            default:
                throw new UnsupportedTypeException();
        }

        return this;
    }

    /**
     * Gets the number of occurrences for males.
     * @return The number of occurrences for males.
     */
    public long getMale() {
        return male;
    }

    /**
     * Sets the number of occurrences for males.
     * @param male the number of occurrences.
     */
    public void setMale(long male) {
        this.male = male;
    }

    /**
     * Adds the given value to the number of occurrences for males.
     * @param value the value to add.
     * @return a reference to this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType addMale(long value) {
        this.male += value;
        return this;
    }

    /**
     * Gets the number of occurrences for females.
     * @return The number of occurrences for females.
     */
    public long getFemale() {
        return female;
    }

    /**
     * Sets the number of occurrences for males.
     * @param female the number of occurrences.
     */
    public void setFemale(long female) {
        this.female = female;
    }

    /**
     * Adds the given value to the number of occurrences for females.
     * @param value the value to add.
     * @return a reference to this {@code OccurrencesPerType}.
     */
    public OccurrencesPerType addFemale(long value) {
        this.female += value;
        return this;
    }
}
