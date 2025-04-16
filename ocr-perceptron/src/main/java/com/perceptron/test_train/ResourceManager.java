package com.perceptron.test_train;

import java.io.File;
import java.util.Iterator;

/**
 * Utility class to create manage test and train data,
 * mainly provides other classes with a convenient iteration
 * of {@link DataFrame}s
 *
 * @author: Cole Johnson
 */
public class ResourceManager {

    private final static String TEST_FILE_PATH = "src/main/resources/mnist_test.csv";
    private final static String TRAIN_FILE_PATH = "src/main/resources/mnist_train.csv";
    // test and train data, separated into train and test files
    private static final File testFile = new File(TEST_FILE_PATH);
    private static final File trainFile = new File(TRAIN_FILE_PATH);
    // test and train data sizes
    private static final int testSize = 10000;
    private static final int trainSize = 60000;
    // iterators stored inside resource manager
    DataFrameIterator testIterator;
    DataFrameIterator trainIterator;

    public ResourceManager() {
        // init iterators to begin reading dataframes
        testIterator = new DataFrameIterator(testFile);
        trainIterator = new DataFrameIterator(trainFile);
    }

    /**
     * Reset the iterators back to the start of train and test dataset.
     */
    public void reset() {
        testIterator = new DataFrameIterator(testFile);
        trainIterator = new DataFrameIterator(trainFile);
    }

    /**
     * Return a iterator of training data.
     *
     * @return Contains iterator that can be used to loop through
     * all training data without storing it all at once
     */
    public Iterator<DataFrame> getTrainingData() {
        return trainIterator;
    }

    /**
     * Return a iterator of test data.
     *
     * @return Contains iterator that can be used to loop through
     * all testing data without storing it all at once
     */
    public Iterator<DataFrame> getTestData() {
        return testIterator;
    }

    /**
     * Returns the size of the test set.
     *
     * @return number of {@link DataFrame}s in test set
     */
    public static int getTestSize() {
        return testSize;
    }

    /**
     * Returns the size of the training set
     *
     * @return number of {@link DataFrame}s in training set
     */
    public static int getTrainSize() {
        return trainSize;
    }
}
