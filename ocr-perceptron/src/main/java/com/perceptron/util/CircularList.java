package com.perceptron.util;

import java.util.ArrayList;

/**
 * Helper class to implement a circular list.
 * In the project, this is used to gather cost
 * associated during backpropagation to ensure
 * that the cost is being minimized during training
 * @author Cole Johnson
 * @param <T> elements contained in CircularList
 */
public class CircularList<T> {
    ArrayList<T> list;
    private final int capacity;

    /**
     * Create new Circular List
     * @param capacity how many elements the list will contain
     */
    CircularList(int capacity) {
        if (capacity == 0) throw new IllegalArgumentException("[ERROR]: Cannot have zero capacity list!");
        list = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    /**
     * Gets a certain item from list
     * @param index index of item
     * @return element inside list
     */
    public T get(int index) {
        // handle over-index by wrapping around in circular fashion
        int idx  = index % capacity;
        return list.get(idx);

    }

    /**
     * Put new element into list
     * @param index index of item
     * @param element item to be placed inside list
     */
    public void put(int index, T element) {
        int idx = index % capacity;
        list.set(idx, element);
    }

}
