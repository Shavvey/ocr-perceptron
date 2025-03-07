package com.perceptron.test_train;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

public class DataFrameIterator implements Iterator<DataFrame> {
    // constructed regex to parse all the csv content
    private static final String CSV_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    // buffered reader that will parse each csv line
    private final BufferedReader reader;
    // create a cursor over the csv, should be null at first
    private DataFrame cursor = null;

    // construct data frame iterator from a buffered reader
    DataFrameIterator(BufferedReader bf) {
        if (bf == null) throw new NullPointerException("[ERROR]: BufferedReader is null!");
        reader = bf;
    }

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
        // if line gets nothing, cursor will also be nothing
        if (line == null) {
            cursor = null;
        } else {
            // else to try parse the line we get back (use csv parser to collect into data frame)
            String[] tokens = line.split(CSV_REGEX);
            short[][] data = new short[DataFrame.FRAME_LENGTH][DataFrame.FRAME_LENGTH];
            String label = null;
            for (String token : tokens) {
                label = token;
                for (int dy = 0; dy < DataFrame.FRAME_LENGTH; dy++) {
                    for (int dx = 0; dx < DataFrame.FRAME_LENGTH; dx++) {
                        data[dx][dy] = Short.decode(token);
                    }
                }

            }
            // if label can't be decoded correctly, something is very wrong
            if (label == null) throw new RuntimeException("[ERROR]: Parse error, cannot read label!");
            // construct dataframe from collected data and label, store to current cursor position
            cursor = new DataFrame(label, data);
        }
    }


    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    @Override
    public DataFrame next() {
        DataFrame frame = cursor;
        advance();
        return frame;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super DataFrame> action) {
        Iterator.super.forEachRemaining(action);
    }
}
