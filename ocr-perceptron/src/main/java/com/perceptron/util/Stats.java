package com.perceptron.util;
import static java.lang.Math.exp;

public class Stats {
    // sigmoid function will 'squish' all real numbers into
    // a range between 0 and 1. This is useful when we are
    // forward propagating to find an activation value of the next layer's neurons
    public static double sigmoid(double x) {
        return (1 /  (1 + exp(-x)));
    }

}
