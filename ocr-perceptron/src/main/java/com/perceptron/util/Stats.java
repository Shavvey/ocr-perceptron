package com.perceptron.util;
import java.util.Random;


public final class Stats {
    final static Random rand = new Random();

    public static double randDouble(double min, double max) {
        return rand.nextDouble(min, max);
    }

}
