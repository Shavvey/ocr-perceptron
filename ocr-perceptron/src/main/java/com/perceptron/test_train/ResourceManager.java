package com.perceptron.test_train;

import java.io.File;
// singleton class representing all test and train data frames needed
public final class ResourceManager {

    private final static String TEST_FILE_PATH = "src/main/resources/mnist_test.csv";
    private final static String TRAIN_FILE_PATH = "src/main/resources/mnist_train.csv";
    // test and train data, separated into train and test files
    private static final File testFile = new File(TEST_FILE_PATH);
    private static final File trainFile = new File(TRAIN_FILE_PATH);

    // returns back the iterator for training data frames
    public static DataFrameIterator getTrainingData() {
        return new DataFrameIterator(trainFile);
    }
    // returns back the iterator for test data frames
    public static DataFrameIterator getTestData() {
        return new DataFrameIterator(testFile);
    }
}
