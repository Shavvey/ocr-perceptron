package com.perceptron.util;

import java.util.Random;


public final class Stats {
    // max and min weights any neuron can hold
    public static final double MAX_WEIGHT_VAL = 1F;
    public static final double MIN_WEIGHT_VAL = -1F;
    final static Random rand = new Random();

    public static double randDouble(double min, double max) {
        return rand.nextDouble(min, max);
    }

}
