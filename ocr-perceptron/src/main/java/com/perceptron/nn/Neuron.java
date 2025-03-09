package com.perceptron.nn;

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
    private double activation; // 'brightness' of the activated neuron, based on `ActivationFunction`
    double[] weights; // collection of connected weight to the previous layer
    double bias;
    Neuron(double activation,  double bias) {
        this.activation = activation;
        this.bias = bias;
    }


}
