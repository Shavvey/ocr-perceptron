package com.perceptron.nn;

import com.perceptron.util.Stats;


/**
 * Neuron the main unit of the {@link NeuralNetwork}
 * contains the basic information we need during test, training
 * and operation: weights, activation, and bias
 * @author: Cole Johnson
 */
public class Neuron {
    // max and min weights any neuron can hold
    private static final double MAX_WEIGHT_VAL = 1F;
    private static final double MIN_WEIGHT_VAL = -1F;
    // 'brightness' of the activated neuron, based on `ActivationFunction`
    double activation;
    // collection of connected weight to the next layer
    double[] weights;
    double bias;
    double deltaSum;
    double delta;

    /**
     * Constructor that just initializes activation and bias to zero
     * (Weights must be dealt with later
     */
    Neuron(int weightCount) {
        this.activation = 0;
        this.bias = 0;
        weights = new double[weightCount];
        this.delta = 0.00F;
        this.deltaSum = 0.00F;
    }

    void randomizeWeights() {
        for (int i = 0; i < weights.length; i++) {
            // random value between -1 and 1
            weights[i] = Stats.randDouble(Neuron.MIN_WEIGHT_VAL, Neuron.MAX_WEIGHT_VAL);
        }
    }


}
