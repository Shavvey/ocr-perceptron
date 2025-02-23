package com.perceptron.nn;

public class Neuron {
    // max and min weights any neuron can hold
    static final double MAX_WEIGHT_VAL = 1F;
    static final double MIN_WEIGHT_VAL = -1F;
    double activation; // 'brightness' of the activated neuron, based on `ActivationFunction`
    double weight;
    double bias;
    Neuron(double activation, double weight, double bias) {
        this.activation = activation;
        this.weight = weight;
        this.bias = bias;
    }

}
