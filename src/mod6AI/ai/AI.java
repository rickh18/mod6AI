package mod6AI.ai;

import mod6AI.exceptions.UnsupportedTypeException;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Student on 24-11-2014.
 */
public class AI {

    /**
     * For each word count the times it occurs.
     * @param words a collection of words.
     * @return a HashMap containing for each word the times it occurs.
     */
    public static Map<String, Long> getOccurrencesCount(Collection<String> words) {
        return words.parallelStream().collect(Collectors.groupingBy(o -> o, Collectors.counting()));
    }

    /**
     * Represent a data set.
     * It contains for each word/attribute the number of occurrences.
     */
    public static class DataSet {
        private final Map<String, Long> data;

        /**
         * Initializes a new {@code DataSet} with the given data.
         * @param data the data.
         */
        public DataSet(Map<String, Long> data) {
            this.data = data;
        }

        /**
         * Gets the data, a map with for each word/attribute the number of occurrences.
         * @return the data set.
         */
        public Map<String, Long> getData() {
            return data;
        }
    }

    /**
     * Represent a classified data set.
     * It contains the {@code ClassificationType} of the data set and for each word/attribute the number of occurrences.
     */
    public static class ClassifiedDataSet extends DataSet {
        private final ClassificationType type;

        /**
         * Initializes a new {@code ClassifiedDataSet} with the given data and {@code ClassificationType}.
         * @param data the data.
         * @param type the type of the data set.
         */
        public ClassifiedDataSet(Map<String, Long> data, ClassificationType type) {
            super(data);
            this.type = type;
        }

        /**
         * Gets the {@code ClassificationType} of the data set.
         * @return the {@code ClassificationType}.
         */
        public ClassificationType getType() {
            return type;
        }
    }



    /** The smoothing factor k. */
    private double k;
    /** Threshold value which determines if a word is kept in the vocabulary */
    private int threshold;
    /** Indicates if this AI can create an arff data file. */
    private final boolean arff;

    /**
     * The complete vocabulary of the training sets with for each word the number of occurrences per type.
     */
    private HashMap<String, OccurrencesPerType> vocabulary;
    /**
     * Cached value of the number of words in the vocabulary that occur at least as often as the threshold.
     */
    private long cachedVocabularySizeAboveThreshold;
    /**
     * A map containing the total number of words in the training set per {@code ClassificationType}.
     */
    private HashMap<ClassificationType, Integer> totalNumberOfWordsByType;
    /**
     * Holds all classified data sets with which this AI is trained, when this AI was created with the arff option set.
     * If the arff option was not set this collection will not be populated.
     */
    private ArrayList<DataSet> classifiedDataSets;

    /**
     * Initializes a new AI with given smoothing value.
     * This AI will not be able to create an arff data file.
     * The threshold for words to be considered being in the vocabulary will be initialized at 0.
     * @param k the smoothing value
     */
    public AI(double k) {
        this(k, 0, false);
    }

    /**
     * Initializes a new AI with given smoothing and threshold value.
     * This AI will not be able to create an arff data file.
     * @param k the smoothing value
     * @param threshold the threshold for words to be considered being in the vocabulary
     */
    public AI(double k, int threshold) {
        this(k, threshold, false);
    }

    /**
     * Initializes a new AI with given smoothing and threshold value.
     * @param k the smoothing value
     * @param threshold the threshold for words to be considered being in the vocabulary
     * @param arff when set to true this AI will be able to create an arff data file from the date it is trained with.
     */
    public AI(double k, int threshold, boolean arff) {
        this.k = k;
        this.threshold = threshold;
        this.arff = arff;
        vocabulary = new HashMap<>();
        totalNumberOfWordsByType = new HashMap<>();
        totalNumberOfWordsByType.put(ClassificationType.C1, 0);
        totalNumberOfWordsByType.put(ClassificationType.C2, 0);
        classifiedDataSets = new ArrayList<>();
    }

    /**
     * Gets the size of the total vocabulary.
     * @return the size of the total vocabulary.
     */
    public int getVocabularySize() {
        return vocabulary.size();
    }

    /**
     * Gets the number of words in the vocabulary that occur at least as often as the threshold.
     * @return the size of the vocabulary above the threshold.
     */
    private long getVocabularySizeAboveThreshold() {
        return vocabulary.values().parallelStream().filter(o -> o.getTotal() >= getThreshold()).count();
    }

    /**
     * Gets the cached value for the number of words in the vocabulary that occur at least as often as the threshold.
     * @return the size of the vocabulary above the threshold.
     */
    public long getCachedVocabularySizeAboveThreshold() {
        return cachedVocabularySizeAboveThreshold;
    }

    /**
     * Updates the cached value for the number of words in the vocabulary that occur at least as often as the threshold.
     */
    private void updateCachedVocabularySizeAboveThreshold() {
        cachedVocabularySizeAboveThreshold = getVocabularySizeAboveThreshold();
    }

    /**
     * Gets the total number of words in all documents written by the given type
     * @param type the {@code ClassificationType}
     * @return the total number of words in all documents written by the given type.
     */
    public int getTotalNumberOfWordsByType(ClassificationType type) {
        return totalNumberOfWordsByType.get(type);
    }

    /**
     * Trains the AI for the given type with the given String.
     * @param in a String to train with.
     * @param type the ClassificationType of the training String.
     */
    public synchronized void train(String in, ClassificationType type) {
        Collection<String> tokens = Tokenizer.tokenize(in);

        totalNumberOfWordsByType.put(type, totalNumberOfWordsByType.get(type) + tokens.size());

        Map<String, Long> occurrencesCount = getOccurrencesCount(tokens);
        occurrencesCount.forEach((word, count) ->
                        vocabulary.put(word, vocabulary.getOrDefault(word, new OccurrencesPerType()).add(type, count)));
        if (arff) {
            classifiedDataSets.add(new ClassifiedDataSet(occurrencesCount, type));
        }

        updateCachedVocabularySizeAboveThreshold();
    }

    /**
     * Classifies the input.
     * @param in a String to classify.
     * @return {@code ClassificationType.C1} or {@code ClassificationType.C2}.
     */
    public synchronized ClassificationType classify(String in) {
        Collection<String> tokens = Tokenizer.tokenize(in);

        double chanceM = calculateChanceForType(tokens, ClassificationType.C1);
        double chanceF = calculateChanceForType(tokens, ClassificationType.C2);

        return chanceM > chanceF ? ClassificationType.C1 : ClassificationType.C2;
    }

    /**
     * Calculate the change that the given token collection is a certain type.
     * Calculation is done in ln space.
     * @param tokens the token collection to test.
     * @param type the type to test for.
     * @return the ln(change) that the given token collection indicates the given type.
     */
    private double calculateChanceForType(Collection<String> tokens, ClassificationType type) {
        return tokens.parallelStream()
                        .filter(t -> vocabulary.containsKey(t) && vocabulary.get(t).getTotal() >= getThreshold())
                        .mapToDouble(t -> getChance(t, type)).map(Math::log).sum();
    }

    /**
     * Calculates the chance a word indicates a certain type.
     * @param word the word to test.
     * @param type the type to test for.
     * @return the chance that the given word indicates the given type.
     */
    private double getChance(String word, ClassificationType type) {
        long wordFreq = 0;
        OccurrencesPerType occurrencesPerType = vocabulary.get(word);
        if (occurrencesPerType != null) {
            wordFreq = vocabulary.get(word).get(type);
        }

        return (wordFreq + k) / (getTotalNumberOfWordsByType(type) + k * getCachedVocabularySizeAboveThreshold());
    }

    /**
     * Gets the smoothing factor k..
     * @return the smoothing factor k.
     */
    public double getK() {
        return k;
    }

    /**
     * Sets the threshold value.
     * @param k the new smoothing factor k..
     */
    public synchronized void setK(double k) {
        this.k = k;
    }

    /**
     * Gets the number of times a word should at least occur to be considered as being in the vocabulary.
     * @return the threshold value.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold value.
     * @param threshold the new threshold value.
     */
    public synchronized void setThreshold(int threshold) {
        this.threshold = threshold;
        updateCachedVocabularySizeAboveThreshold();
    }

    /**
     * Indicates if this AI can create an arff data file from the data it was trained with.
     * @return {@code true} when this AI was created with the arff option set; otherwise {@code false}.
     */
    public boolean canCreateArffDataFile() {
        return arff;
    }

    /**
     * Creates a arff data file from the training sets and writes it to the given writer.
     * @param writer the {@code Writer} to write to.
     * @param name the name of the relation.
     * @return {@code true} if creation was successful;
     * otherwise {@code false}, this will be the case when this AI was not created with the arff option set.
     */
    public boolean createArffDataFile(Writer writer, String name) {
        if (!arff) {
            return false;
        }

        return createArffDataFile(classifiedDataSets, writer, name);
    }

    /**
     * Creates a arff data file with the current vocabulary and the given unclassified data
     * and writes it to the given writer.
     * @param data the unclassified data.
     * @param writer the {@code Writer} to write to.
     * @param name the name of the relation.
     * @return {@code true} if creation was successful; otherwise {@code false}.
     */
    public boolean createArffDataFileFromOtherDataSet(Collection<String> data, Writer writer, String name) {
        if (writer == null || name == null) {
            return false;
        }

        ArrayList<DataSet> dataSets = new ArrayList<>();
        data.parallelStream()
                .map(Tokenizer::tokenize)
                .map(AI::getOccurrencesCount)
                .map(DataSet::new)
                .forEach(dataSets::add);

        return createArffDataFile(dataSets, writer, name);
    }

    /**
     * Creates a arff data file with the current vocabulary and the given data and writes it to the given writer.
     * @param data the data, a list with unclassified {@code DataSet} and/or classified {@code ClassifiedDataSet}.
     * @param writer the {@code Writer} to write to.
     * @param name the name of the relation.
     * @return {@code true} if creation was successful; otherwise {@code false}.
     */
    public boolean createArffDataFile(ArrayList<DataSet> data, Writer writer, String name) {
        if (writer == null || name == null) {
            return false;
        }

        PrintWriter printWriter = new PrintWriter(writer, true);

        List<String> attributes = vocabulary.keySet().parallelStream()
                .filter(o -> vocabulary.get(o).getTotal() >= getThreshold())
                .sorted().collect(Collectors.toList());

        printWriter.printf("@RELATION '%s'", name).println();
        printWriter.println();
        attributes.forEach(a -> printWriter.printf("@ATTRIBUTE %s NUMERIC", a).println());
        printWriter.printf("@ATTRIBUTE @@class@@ {%s,%s}", ClassificationType.C2, ClassificationType.C1).println();
        printWriter.println();
        printWriter.println("@DATA");
        for (DataSet ds : data) {
            StringBuilder buf = new StringBuilder();
            buf.append("{");
            for (Map.Entry<String, Long> entry : ds.getData().entrySet().stream()
                                                                        .filter(e -> attributes.contains(e.getKey()))
                                                                        .sorted((e1, e2) ->
                                                                                e1.getKey().compareTo(e2.getKey()))
                                                                        .collect(Collectors.toList())) {
                buf.append(attributes.indexOf(entry.getKey()));
                buf.append(" ");
                buf.append(entry.getValue());
                buf.append(",");
            }
            if (ds instanceof ClassifiedDataSet) {
                buf.append(attributes.size());
                buf.append(" ");
                buf.append(((ClassifiedDataSet) ds).getType().toString());
            } else {
                buf.deleteCharAt(buf.length() -1);
            }
            buf.append("}");

            printWriter.println(buf);
        }

        return true;
    }
}
