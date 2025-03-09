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

    /**
     * Constructor for each Neuron
     * @param activation represents the "brightness" of neuron when it reacts to input/stimuli
     * @param bias add bias to the activation of the neuron
     */
    Neuron(double activation,  double bias) {
        this.activation = activation;
        this.bias = bias;
    }

    /**
     * Constructor that just initializes activation and bias to zero
     * (Weights must be dealt with later
     */
    Neuron() {
        this.activation = 0;
        this.bias = 0;
    }

    void randomizeWeights(int nextLayerCount) {
        weights = new double[nextLayerCount];
        for (int i = 0; i < nextLayerCount; i++) {
            weights[i] = Stats.randDouble(Neuron.MIN_WEIGHT_VAL, Neuron.MAX_WEIGHT_VAL);

        }
    }


}
