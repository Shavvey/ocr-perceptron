package com.perceptron.util;

import java.util.ArrayList;

/**
 * Helper class to implement a circular list.
 * In the project, this is used to gather cost
 * associated during backpropagation to ensure
 * that the cost is being minimized during training
 *
 * @author Cole Johnson
 */
public class CostList {
    private double[] list;
    private final int capacity;

    /**
     * Create new Circular List
     *
     * @param capacity how many elements the list will contain
     */
    public CostList(int capacity) {
        if (capacity == 0) throw new IllegalArgumentException("[ERROR]: Cannot have zero capacity list!");
        list = new double[capacity];
        this.capacity = capacity;
    }

    /**
     * Gets a certain item from list
     *
     * @param index index of item
     * @return element inside list
     */
    public double get(int index) {
        // handle over-index by wrapping around in circular fashion
        int idx = index % capacity;
        return list[idx];

    }

    /**
     * Put new element into list
     *
     * @param index   index of item
     * @param value   value to be placed inside list
     */
    public void put(int index, double value) {
        int idx = index % capacity;
        list[idx] = value;
    }

}
