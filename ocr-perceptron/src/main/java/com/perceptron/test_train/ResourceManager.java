package com.perceptron.test_train;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
// singleton class representing all test and train data frames needed
public final class ResourceManager {

    public final static String TEST_FILE_PATH = "src/main/resources/mnist_test.csv";
    public final static String TRAIN_FILE_PATH = "src/main/resources/mnist_train.csv";;
    public ResourceManager() {
        // try to make buffered readers for each test and train dataset
        // test and train input stream need is to be constructed and then used to build
        try {
            final BufferedReader testInput = new BufferedReader(new FileReader(TEST_FILE_PATH));
            final BufferedReader trainInput = new BufferedReader(new FileReader(TRAIN_FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
