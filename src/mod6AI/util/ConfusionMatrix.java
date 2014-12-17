package mod6AI.util;

import mod6AI.ai.ClassificationType;

/**
 * Created by Student on 16-12-2014.
 */
public class ConfusionMatrix {

    /** The number of C1 instances classified as C1 */
    private final long a;
    /** The number of C1 instances classified as C2 */
    private final long b;
    /** The number of C2 instances classified as C1 */
    private final long c;
    /** The number of C2 instances classified as C2 */
    private final long d;

    /**
     * Initializes a new ConfusionMatrix for two classes, C1 and C2.
     * @param a the number of C1 instances classified as C1.
     * @param b the number of C1 instances classified as C2.
     * @param c the number of C2 instances classified as C1.
     * @param d the number of C2 instances classified as C2.
     */
    public ConfusionMatrix(long a, long b, long c, long d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public long getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public long getC() {
        return c;
    }

    public long getD() {
        return d;
    }

    public double accuracy() {
        return (a + d) / (double) (a + b + c + d);
    }

    public double errorRate() {
        return (b + c) / (double) (a + b + c + d);
    }

    public double recallC1() {
        return a / (double) (a + b);
    }

    public double recallC2() {
        return d / (double) (c + d);
    }

    public double precisionC1() {
        return a / (double) (a + c);
    }

    public double precisionC2() {
        return d / (double) (b + d);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%4s %4s <-- classified as", ClassificationType.C1, ClassificationType.C2));
        result.append(System.lineSeparator());
        result.append(String.format("%4d %4d | %s", a, b, ClassificationType.C1));
        result.append(System.lineSeparator());
        result.append(String.format("%4d %4d | %s", c, d, ClassificationType.C2));
        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        result.append(String.format("Accuracy:   %f", accuracy())); result.append(System.lineSeparator());
        result.append(String.format("Error Rate: %f", errorRate())); result.append(System.lineSeparator());
        result.append(String.format("Recall %s: %f", ClassificationType.C1, recallC1()));
        result.append(System.lineSeparator());
        result.append(String.format("Recall %s: %f", ClassificationType.C2, recallC2()));
        result.append(System.lineSeparator());
        result.append(String.format("Precision %s: %f", ClassificationType.C1, precisionC1()));
        result.append(System.lineSeparator());
        result.append(String.format("Precision %s: %f", ClassificationType.C2, precisionC2()));

        return result.toString();
    }
}
