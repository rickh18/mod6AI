package mod6AI.ai;

/**
 * Created by Student on 25-11-2014.
 */
public enum ClassificationType {
    C1("C1"),
    C2("C2");

    private String name;

    private ClassificationType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
