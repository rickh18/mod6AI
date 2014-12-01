package mod6AI.ai;

/**
 * Created by Student on 25-11-2014.
 */
public enum ClassificationType {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        return name().substring(0, 1);
    }
}
