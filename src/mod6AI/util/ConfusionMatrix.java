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
    /** A title for the matrix. */
    private String title;

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

    /**
     * Initializes a new ConfusionMatrix with a title for two classes, C1 and C2.
     * @param a the number of C1 instances classified as C1.
     * @param b the number of C1 instances classified as C2.
     * @param c the number of C2 instances classified as C1.
     * @param d the number of C2 instances classified as C2.
     * @param title a title for the matrix.
     */
    public ConfusionMatrix(long a, long b, long c, long d, String title) {
        this(a, b, c, d);
        this.title = title;
    }

    /**
     * Gets the number of C1 instances classified as C1.
     * @return the number of C1 instances classified as C1.
     */
    public long getA() {
        return a;
    }

    /**
     * Gets the number of C1 instances classified as C2.
     * @return the number of C1 instances classified as C2.
     */
    public long getB() {
        return b;
    }

    /**
     * Gets the number of C2 instances classified as C1.
     * @return the number of C2 instances classified as C1.
     */
    public long getC() {
        return c;
    }

    /**
     * Gets the number of C2 instances classified as C2.
     * @return the number of C2 instances classified as C2.
     */
    public long getD() {
        return d;
    }

    /**
     * Calculates the accuracy of the classification represented in this matrix.
     * @return the accuracy.
     */
    public double accuracy() {
        return (a + d) / (double) (a + b + c + d);
    }

    /**
     * Calculates the error rate of the classification represented in this matrix.
     * @return the error rate.
     */
    public double errorRate() {
        return (b + c) / (double) (a + b + c + d);
    }

    /**
     * Calculates the recall for the first class of the classification represented in this matrix.
     * @return the recall for the first class.
     */
    public double recallC1() {
        return a / (double) (a + b);
    }

    /**
     * Calculates the recall for the second class of the classification represented in this matrix.
     * @return the recall for the second class.
     */
    public double recallC2() {
        return d / (double) (c + d);
    }

    /**
     * Calculates the precision for the first class of the classification represented in this matrix.
     * @return the precision for the first class.
     */
    public double precisionC1() {
        return a / (double) (a + c);
    }

    /**
     * Calculates the precision for the second class of the classification represented in this matrix.
     * @return the precision for the second class.
     */
    public double precisionC2() {
        return d / (double) (b + d);
    }

    /**
     * Gives a plaintext representation of this confusion matrix together with all the performance values.
     * @return a plaintext representation of this confusion matrix together with all the performance values.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (title != null) {
            result.append(title);
            result.append(System.lineSeparator());
        }
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
