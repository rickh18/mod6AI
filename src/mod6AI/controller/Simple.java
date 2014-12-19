package mod6AI.controller;

import mod6AI.ai.AI;
import mod6AI.ai.ClassificationType;
import mod6AI.ai.Tokenizer;
import mod6AI.util.ConfusionMatrix;
import mod6AI.util.FileMerger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains some methods for some performance testing and arff data file generation.
 * Created by Student on 26-11-2014.
 */
public class Simple {

    public static void test1() {
        AI ai = new AI(1, 0);
        Scanner in = new Scanner(System.in);
        System.out.printf("Path directory with %s training data: ", ClassificationType.C1);
        String c1TrainPath = in.nextLine();
        System.out.printf("Path directory with %s training data: ", ClassificationType.C2);
        String c2TrainPath = in.nextLine();
        System.out.println("Training...");
        System.out.println(LocalDateTime.now());
        System.out.println(System.currentTimeMillis());

        try {
            ai.train(FileMerger.mergeAllTextFilesInDirectory(c1TrainPath), ClassificationType.C1);
            ai.train(FileMerger.mergeAllTextFilesInDirectory(c2TrainPath), ClassificationType.C2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());

        in.nextLine();

        try {
            System.out.println(AI.getOccurrencesCount(Tokenizer.tokenize(FileMerger.mergeAllTextFilesInDirectory(c1TrainPath))));
            System.out.println(AI.getOccurrencesCount(Tokenizer.tokenize(FileMerger.mergeAllTextFilesInDirectory(c2TrainPath))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createArffs() throws FileNotFoundException {
        AI ai = new AI(1, 0, true);

        Scanner in = new Scanner(System.in);
        System.out.printf("Path directory with %s training data: ", ClassificationType.C1);
        String c1TrainPath = in.nextLine();
        System.out.printf("Path directory with %s training data: ", ClassificationType.C2);
        String c2TrainPath = in.nextLine();

        System.out.println("Reading data...");
        long begin = System.currentTimeMillis();
        try {
            for (File f : FileMerger.listFilesWithExtension(Paths.get(c1TrainPath), "txt")) {
                ai.train(FileMerger.readFile(f), ClassificationType.C1);
            }
            for (File f : FileMerger.listFilesWithExtension(Paths.get(c2TrainPath), "txt")) {
                ai.train(FileMerger.readFile(f), ClassificationType.C2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);
        System.out.println();

        System.out.print("Were do you want the arff data file: ");
        String arffPath = in.nextLine();
        PrintWriter writer = new PrintWriter(arffPath);

        System.out.println("Creating arff data file from training data set...");
        begin = System.currentTimeMillis();

        ai.createArffDataFile(writer, "blog_train");

        end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);
        System.out.println();


        System.out.printf("Path directory with %s test data: ", ClassificationType.C1);
        String c1TestPath = in.nextLine();
        System.out.printf("Path directory with %s test data: ", ClassificationType.C2);
        String c2TestPath = in.nextLine();

        System.out.println("Reading data...");
        begin = System.currentTimeMillis();

        ArrayList<AI.DataSet> dataSets = new ArrayList<>();
        try {
            FileMerger.listFilesWithExtension(Paths.get(c1TestPath), "txt")
                            .parallelStream()
                            .map(FileMerger::readFile)
                            .map(Tokenizer::tokenize)
                            .map(AI::getOccurrencesCount)
                            .map(d -> new AI.ClassifiedDataSet(d, ClassificationType.C1))
                            .forEach(dataSets::add);
            FileMerger.listFilesWithExtension(Paths.get(c2TestPath), "txt")
                    .parallelStream()
                    .map(FileMerger::readFile)
                    .map(Tokenizer::tokenize)
                    .map(AI::getOccurrencesCount)
                    .map(d -> new AI.ClassifiedDataSet(d, ClassificationType.C2))
                    .forEach(dataSets::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);
        System.out.println();

        System.out.print("Were do you want the arff data file: ");
        arffPath = in.nextLine();
        writer = new PrintWriter(arffPath);

        System.out.println("Creating arff data file from test data set...");
        begin = System.currentTimeMillis();

        ai.createArffDataFile(dataSets, writer, "blog_test");

        end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);
        System.out.println();
    }

    public static AI trainedAI(int k, int threshold) {
        AI ai = new AI(k, threshold);
        Scanner in = new Scanner(System.in);
        System.out.printf("Path directory with %s training data: ", ClassificationType.C1);
        String c1TrainPath = in.nextLine();
        System.out.printf("Path directory with %s training data: ", ClassificationType.C2);
        String c2TrainPath = in.nextLine();
        System.out.println("Training...");

        try {
            ai.train(FileMerger.mergeAllTextFilesInDirectory(c1TrainPath), ClassificationType.C1);
            ai.train(FileMerger.mergeAllTextFilesInDirectory(c2TrainPath), ClassificationType.C2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        return ai;
    }

    public static void testPerformance() throws IOException{
        AI ai = trainedAI(1, 0);

        Scanner in = new Scanner(System.in);
        System.out.printf("Path directory with %s test data: ", ClassificationType.C1);
        String c1TestPath = in.nextLine();
        System.out.printf("Path directory with %s test data: ", ClassificationType.C2);
        String c2TestPath = in.nextLine();

        Map<ClassificationType, Long> classifiedC1TestData;
        Map<ClassificationType, Long> classifiedC2TestData;
        HashSet<ConfusionMatrix> confusionMatrices = new HashSet<>();

        double[] ks = {0.1, 0.25, 0.5, 0.75, 0.9, 1, 1.1, 1.25, 1.5, 1.75, 2, 2.5, 3, 5};
        int[] thresholds = {0, 2, 5, 10, 25, 50, 75, 100, 150};
        for (double k : ks) {
            ai.setK(k);
            for (int threshold : thresholds) {
                ai.setThreshold(threshold);
                System.out.println(String.format("Testing with k %1.2f and threshold %d ...", k, threshold));

                classifiedC1TestData = FileMerger.listFilesWithExtension(Paths.get(c1TestPath), "txt")
                        .stream()
                        .map(FileMerger::readFile)
                        .map(ai::classify)
                        .collect(Collectors.groupingBy(o -> o, Collectors.counting()));
                classifiedC2TestData = FileMerger.listFilesWithExtension(Paths.get(c2TestPath), "txt")
                        .stream()
                        .map(FileMerger::readFile)
                        .map(ai::classify)
                        .collect(Collectors.groupingBy(o -> o, Collectors.counting()));

                ConfusionMatrix confusionMatrix = new ConfusionMatrix(
                        classifiedC1TestData.getOrDefault(ClassificationType.C1, 0L),
                        classifiedC1TestData.getOrDefault(ClassificationType.C2, 0L),
                        classifiedC2TestData.getOrDefault(ClassificationType.C1, 0L),
                        classifiedC2TestData.getOrDefault(ClassificationType.C2, 0L),
                        String.format("k: %1.2f; threshold: %d", k, threshold));

                confusionMatrices.add(confusionMatrix);

                System.out.println(confusionMatrix);
                System.out.println("--------------------------------------------");
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("********************************************");
        System.out.println("Best and worst overview:");
        System.out.println();

        List<ConfusionMatrix> topAccuracy = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.accuracy(), cm2.accuracy())).collect(Collectors.toList());
        List<ConfusionMatrix> topErrorRate = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.errorRate(), cm2.errorRate())).collect(Collectors.toList());
        List<ConfusionMatrix> topRecallC1 = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.recallC1(), cm2.recallC1())).collect(Collectors.toList());
        List<ConfusionMatrix> topRecallC2 = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.recallC2(), cm2.recallC2())).collect(Collectors.toList());
        List<ConfusionMatrix> topPrecisionC1 = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.precisionC1(), cm2.precisionC1())).collect(Collectors.toList());
        List<ConfusionMatrix> topPrecisionC2 = confusionMatrices.stream().sorted((cm1, cm2) -> Double.compare(cm1.precisionC2(), cm2.precisionC2())).collect(Collectors.toList());

        System.out.println("*** Accuracy ***");
        System.out.println(" * lowest *");
        System.out.println(topAccuracy.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topAccuracy.get(topAccuracy.size() -1));
        System.out.println();
        System.out.println();

        System.out.println("*** Error Rate ***");
        System.out.println(" * lowest *");
        System.out.println(topErrorRate.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topErrorRate.get(topErrorRate.size() -1));
        System.out.println();
        System.out.println();

        System.out.println(String.format("*** Recall %s ***", ClassificationType.C1));
        System.out.println(" * lowest *");
        System.out.println(topRecallC1.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topRecallC1.get(topRecallC1.size() -1));
        System.out.println();
        System.out.println();

        System.out.println(String.format("*** Recall %s ***", ClassificationType.C2));
        System.out.println(" * lowest *");
        System.out.println(topRecallC2.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topRecallC2.get(topRecallC2.size() -1));
        System.out.println();
        System.out.println();

        System.out.println(String.format("*** Precision %s ***", ClassificationType.C1));
        System.out.println(" * lowest *");
        System.out.println(topPrecisionC1.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topPrecisionC1.get(topPrecisionC1.size() -1));
        System.out.println();
        System.out.println();

        System.out.println(String.format("*** Precision %s ***", ClassificationType.C2));
        System.out.println(" * lowest *");
        System.out.println(topPrecisionC2.get(0));
        System.out.println();
        System.out.println(" * highest *");
        System.out.println(topPrecisionC2.get(topPrecisionC2.size() -1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Name for C1: ");
        ClassificationType.C1.setName(in.nextLine());
        System.out.print("Name for C2: ");
        ClassificationType.C2.setName(in.nextLine());

//        try {
//            createArffs();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            testPerformance();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        AI ai = trainedAI(1,0);
//        System.out.print("Where to save: ");
//        String file = in.nextLine();
//        try {
//            ai.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AI ai2 = new AI(1);
//        boolean success = false;
//        try {
//            success = ai2.load(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (success) {
//            System.out.println(ai.classify("Test"));
//            System.out.println(ai2.classify("Test"));
//
//            if (ai.getTotalNumberOfWordsByType(ClassificationType.C1) == ai2.getTotalNumberOfWordsByType(ClassificationType.C1)
//                    && ai.getTotalNumberOfWordsByType(ClassificationType.C2) == ai2.getTotalNumberOfWordsByType(ClassificationType.C2)) {
//                System.out.println(":)");
//            } else {
//                System.out.println(":(");
//            }
//        }
    }

}
