package com.perceptron.test_train;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
// singleton class representing all test and train data frames needed
public class ResourceManager {
    public final static String TEST_FILE_PATH = "src/main/resources/mnist_test.csv";
    public final static String TRAIN_FILE_PATH = "src/main/resources/mnist_train.csv";
    // test and train input stream need is to be constructed and then used to build
    static private BufferedReader testInput;
    static private BufferedReader trainInput;
    // test and train dataframe, built from original input stream
    static private ArrayList<DataFrame> testData;
    static private ArrayList<DataFrame> trainData;
    public ResourceManager(String testPath, String trainPath) {
        // try to make buffered readers for each test and train dataset
        try {
            testInput = new BufferedReader(new FileReader(testPath));
            trainInput = new BufferedReader(new FileReader(trainPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String line = testInput.readLine();
            System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
