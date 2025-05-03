package com.perceptron.util;


import java.util.Random;

/**
 * Utility class to produce random values.
 *
 * @author Cole Johnson
 * @version 1.0
 */
public final class Stats {
    // max and min weights any neuron can hold
    public static final double MAX_WEIGHT_VAL = 1F;
    public static final double MIN_WEIGHT_VAL = -1F;
    final static Random rand = new Random();

    /**
     * Get a uniform distribution of doubles between max and min interval.
     * @param min minimum random value
     * @param max maximum random value
     * @return random double value
     */
    public static double randDouble(double min, double max) {
        return rand.nextDouble(min, max);
    }

    public static double getRandNorm() {
        return rand.nextGaussian();
    }

}
