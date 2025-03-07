package com.perceptron.test_train;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.function.Consumer;

public class DataFrameIterator implements Iterator<DataFrame> {
    // constructed regex to parse all the csv content
    private static final String CSV_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    // treating frame length as a constant, for now
    private static final int FRAME_LENGTH = 28;
    // buffered reader that will parse each csv line
    private final BufferedReader reader;
    private DataFrame cursor;

    // construct data frame iterator from a buffered reader
    DataFrameIterator(BufferedReader bf) {
        if (bf == null) throw new RuntimeException("[ERROR]: BufferedReader is null!");
        reader = bf;
    }

    private void advance() {
        // get current string content from cursor
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] tokens = line.split(CSV_REGEX);
        short[][] data = new short[FRAME_LENGTH][FRAME_LENGTH];
        String label = null;
        for (String token : tokens) {
            label = token;
            for (int dy= 0; dy < FRAME_LENGTH; dy++) {
                for (int dx = 0; dx < FRAME_LENGTH; dx++) {
                    data[dx][dy] = Short.decode(token);
                }
            }

        }
        if (label == null) throw new RuntimeException("[ERROR]: Parse error, cannot read label!");
        cursor = new DataFrame(label, data);
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
