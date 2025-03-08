package com.perceptron.test_train;

import java.io.File;
import java.util.Iterator;

/**
 * Utility class to create manage test and train data,
 * mainly provides other classes with a convenient iteration
 * of DataFrames {@link DataFrame}
 * @author: Cole Johnson
 */
public final class ResourceManager {

    private final static String TEST_FILE_PATH = "src/main/resources/mnist_test.csv";
    private final static String TRAIN_FILE_PATH = "src/main/resources/mnist_train.csv";
    // test and train data, separated into train and test files
    private static final File testFile = new File(TEST_FILE_PATH);
    private static final File trainFile = new File(TRAIN_FILE_PATH);

    /**
     * Return a iterator of training data.
     * @return Contains iterator that can be used to loop through
     * all training data without storing it all at once
     */
    public static Iterator<DataFrame> getTrainingData() {
        return new DataFrameIterator(trainFile);
    }
    /**
     * Return a iterator of test data.
     * @return Contains iterator that can be used to loop through
     * all testing data without storing it all at once
     */
    public static Iterator<DataFrame> getTestData() {
        return new DataFrameIterator(testFile);
    }
}
