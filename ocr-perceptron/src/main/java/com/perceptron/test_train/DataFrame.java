package com.perceptron.test_train;


import com.perceptron.nn.CostFunction;

import java.util.Arrays;

/**
 * Class that encapsulates training and test data for the ocr-perceptron.
 *
 * @author Cole Johnson
 */
public class DataFrame {
    final static int FRAME_LENGTH = 28;
    final static int MAX_BRIGHTNESS = 1 << 8;
    private double[][] data; // 2D array of values the dataset will provide
    private String label; // a labeled classification of that dataset (should be some number 0-9)


    /**
     * DataFrame constructor that passes args for each field in the class.
     *
     * @param label label of the frame, should some character that the values represent
     * @param data  teh actual 2D array of data that shows each drawn character
     */
    public DataFrame(String label, double[][] data) {
        this.label = label;
        this.data = data;
    }

    /**
     * Constructor no arg constructor for `DataFrame` class.
     * Just initializes the data array and the label, and leaves them empty.
     */
    public DataFrame() {
        this.label = "";
        this.data = new double[FRAME_LENGTH][FRAME_LENGTH];
    }

    /**
     * Provide string representation of dataframe.
     *
     * @return a string representing the entire array
     * of values inside the frame
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int dy = 0; dy < FRAME_LENGTH; dy++) {
            s.append("{"); // open brackets
            for (int dx = 0; dx < FRAME_LENGTH; dx++) {
                double d = data[dy][dx];
                if (d == 0) {
                    s.append("    ");
                } else {
                    String val = String.format("%.2f", d);
                    s.append(val);
                }
                if (dx < FRAME_LENGTH - 1) {
                    s.append(",");
                }
            }
            s.append("}\n"); // close brackets
        }
        return s.toString();
    }

    /**
     * Uses toString {@link #toString()} method to print out
     * label and data values of the array inside the DataFrame.
     */
    public void display() {
        System.out.println("==DataFrame Content==");
        System.out.printf("Label: %s\n", this.label);
        System.out.println(this);

    }

    /**
     * Returns the data array.
     *
     * @return current array of data values
     */
    public double[][] getData() {
        return data;
    }

    /**
     * Set the label of the frame.
     *
     * @return set label of frame
     */
    public String getLabel() {
        return label;
    }

    /**
     * Assigns new array of data to the frame.
     *
     * @param data new data to put in inside of frame
     */
    public void setData(double[][] data) {
        this.data = data;
    }

    /**
     * Set new label.
     *
     * @param label new labeled dataframe val
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Wrapper method to index array and set a new value.
     *
     * @param x     x-index
     * @param y     y-index
     * @param value new value to be set at (x, y) index of array
     */
    public void setValue(int x, int y, double value) {
        data[x][y] = value;
    }

    /**
     * decode label into an output value our NN needs to learn from
     * (very useful during backpropagation,
     * evaluated by {@link CostFunction}.
     *
     * @return truth values
     */
    public double[] getTrueValues() {
        double[] d = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int val = Integer.parseInt(label);
        d[val] = 1;
        return d;
    }

    /**
     * Row-wise flatten of the original 2D array into a 1D array.
     *
     * @return flatten version of original 2D array of values inside DataFrame
     */
    public double[] flatten() {
        return Arrays.stream(data).flatMapToDouble(Arrays::stream).toArray();
    }


}
