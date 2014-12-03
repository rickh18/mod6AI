package mod6AI.controller;

import mod6AI.ai.AI;
import mod6AI.ai.ClassificationType;
import mod6AI.ai.Tokenizer;
import mod6AI.exceptions.UnsupportedTypeException;
import mod6AI.util.FileMerger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Student on 26-11-2014.
 */
public class Simple {

    public static void test1() {
        AI ai = new AI(1, 0);
        Scanner in = new Scanner(System.in);
        System.out.print("Path directory with male training data: ");
        String maleTrainPath = in.nextLine();
        System.out.print("Path directory with female training data: ");
        String femaleTrainPath = in.nextLine();
        System.out.println("Training...");
        System.out.println(LocalDateTime.now());
        System.out.println(System.currentTimeMillis());

        try {
            ai.train(FileMerger.mergeAllTextFilesInDirectory(maleTrainPath), ClassificationType.MALE);
            ai.train(FileMerger.mergeAllTextFilesInDirectory(femaleTrainPath), ClassificationType.FEMALE);
        } catch (IOException | UnsupportedTypeException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());

        in.nextLine();

        try {
            System.out.println(AI.getOccurrencesCount(Tokenizer.tokenize(FileMerger.mergeAllTextFilesInDirectory(maleTrainPath))));
            System.out.println(AI.getOccurrencesCount(Tokenizer.tokenize(FileMerger.mergeAllTextFilesInDirectory(femaleTrainPath))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testArff() throws FileNotFoundException {
        AI ai = new AI(1, 0, true);

        Scanner in = new Scanner(System.in);
        System.out.print("Path directory with male training data: ");
        String maleTrainPath = in.nextLine();
        System.out.print("Path directory with female training data: ");
        String femaleTrainPath = in.nextLine();

        System.out.println("Reading data...");
        long begin = System.currentTimeMillis();
        try {
            for (File f : FileMerger.listFilesWithExtension(Paths.get(maleTrainPath), "txt")) {
                ai.train(FileMerger.readFile(f), ClassificationType.MALE);
            }
            for (File f : FileMerger.listFilesWithExtension(Paths.get(femaleTrainPath), "txt")) {
                ai.train(FileMerger.readFile(f), ClassificationType.FEMALE);
            }
        } catch (IOException | UnsupportedTypeException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);

        in.nextLine();

        System.out.print("Were do you want the arff data file: ");
        String arffPath = in.nextLine();
        PrintWriter writer = new PrintWriter(arffPath);

        System.out.println("Creating arff data file...");
        begin = System.currentTimeMillis();

        ai.createArffDataFile(writer, "blog");

        end = System.currentTimeMillis();
        System.out.printf("Done. %d", end - begin);
    }

    public static AI trainedAI(int k, int threshold) {
        AI ai = new AI(k, threshold);
        Scanner in = new Scanner(System.in);
        System.out.print("Path directory with male training data: ");
        String maleTrainPath = in.nextLine();
        System.out.print("Path directory with female training data: ");
        String femaleTrainPath = in.nextLine();
        System.out.println("Training...");

        try {
            ai.train(FileMerger.mergeAllTextFilesInDirectory(maleTrainPath), ClassificationType.MALE);
            ai.train(FileMerger.mergeAllTextFilesInDirectory(femaleTrainPath), ClassificationType.FEMALE);
        } catch (IOException | UnsupportedTypeException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        return ai;
    }

    public static void testPerformance() throws IOException{
        AI ai = trainedAI(1, 0);

        Scanner in = new Scanner(System.in);
        System.out.print("Path directory with male test data: ");
        String maleTestPath = in.nextLine();
        System.out.print("Path directory with female test data: ");
        String femaleTestPath = in.nextLine();

        Map<ClassificationType, Long> classifiedMaleTestData;
        Map<ClassificationType, Long> classifiedFemaleTestData;

        double[] ks = {0.1, 0.25, 0.5, 0.75, 0.9, 1, 1.1, 1.25, 1.5, 1.75, 2, 2.5, 3, 5};
        int[] thresholds = {0, 2, 5, 10, 50, 100};
        for (double k : ks) {
            ai.setK(k);
            for (int threshold : thresholds) {
                ai.setThreshold(threshold);
                System.out.printf("Testing with k %f and threshold %d ...", k, threshold);
                System.out.println();
                classifiedMaleTestData = FileMerger.listFilesWithExtension(Paths.get(maleTestPath), "txt")
                        .stream()
                        .map(FileMerger::readFile)
                        .map(ai::classify)
                        .collect(Collectors.groupingBy(o -> o, Collectors.counting()));
                classifiedFemaleTestData = FileMerger.listFilesWithExtension(Paths.get(femaleTestPath), "txt")
                        .stream()
                        .map(FileMerger::readFile)
                        .map(ai::classify)
                        .collect(Collectors.groupingBy(o -> o, Collectors.counting()));

                System.out.println("  M    F   <-- classified as");
                System.out.printf("%4d   %4d  | M%s", classifiedMaleTestData.get(ClassificationType.MALE),
                        classifiedMaleTestData.get(ClassificationType.FEMALE),
                        System.lineSeparator());
                System.out.printf("%4d   %4d  | F%s", classifiedFemaleTestData.get(ClassificationType.MALE),
                        classifiedFemaleTestData.get(ClassificationType.FEMALE),
                        System.lineSeparator());
            }
        }
    }

    public static void main(String[] args) {
//        try {
//            testArff();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            testPerformance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
