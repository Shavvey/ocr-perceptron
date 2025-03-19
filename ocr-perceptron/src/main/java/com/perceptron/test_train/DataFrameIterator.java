package com.perceptron.test_train;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Abstracts IO process of retrieving test and train data
 * using an Iterator of DataFrames {@link DataFrame}. Iterator
 * functions via a cursor, which will ingest BufferedReader data and
 * return each line into a DataFrame. The motivation for this class
 * was to avoid storing all DataFrames at once, increasing the performance
 * of testing and training.
 * @author: Cole Johnson
 */
public class DataFrameIterator implements Iterator<DataFrame> {
    // constructed regex to parse all the csv content
    private static final String CSV_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    // buffered reader that will parse each csv line
    private final BufferedReader reader;
    // create a cursor over the csv, should be null at first
    private DataFrame cursor;

    /**
     * Constructs an iterator of DataFrames given a file to data (either training or test).
     * @param file file that points to neural network training or test data
     */
    DataFrameIterator(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        advance();
    }

    /**
     * Advances the cursor to create and save a new DataFrame
     */
    private void advance() {
        // get current string content from cursor
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            // throw exception we can't read
            // NOTE: maybe run logger here
            throw new RuntimeException(e);
        }
        // else to try parse the line we get back (use csv parser to collect into data frame)
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(line.split(CSV_REGEX)));
        String label = tokens.removeFirst();
        DataFrame frame = new DataFrame();
        frame.setLabel(label);
        for (int dy = 0; dy < DataFrame.FRAME_LENGTH; dy++) {
            for (int dx = 0; dx < DataFrame.FRAME_LENGTH; dx++) {
                String token = tokens.removeFirst();
                int val = Integer.parseInt(token);
                frame.setValue(dy, dx, (double) val /DataFrame.MAX_BRIGHTNESS);
            }
        }
        // construct dataframe from collected data and label, store to current cursor position
        cursor = frame;
    }

    /**
     * Checks whether data has been exhausted.
     * @return boolean value to determine if no more frames exist
     */
    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    /**
     * Gives back a DataFrame, should be called during iteration.
     * @return next DataFrame inside buffer
     */
    @Override
    public DataFrame next() {
        DataFrame frame = cursor;
        advance();
        return frame;
    }

}
