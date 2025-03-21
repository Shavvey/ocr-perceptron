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
    double[] deltaSum;

    /**
     * Constructor that just initializes activation and bias to zero
     * (Weights must be dealt with later
     */
    Neuron(int weightCount) {
        // init activation to zero
        this.activation = 0;
        // init bias to zero
        this.bias = 0;
        weights = new double[weightCount];
        // init deltas (changes accrued during backpropagation)
        deltaSum = new double[weightCount];
    }

    /**
     * constructs a set of random weights for a Neuron
     */
    void randomizeWeights(int neuronCount) {
        for (int i = 0; i < weights.length; i++) {
            // random value between -1 and 1
            // NOTE: if we init random weights it should at least have a mean centered at zero!
            weights[i] = Stats.randDouble(0, 1);
        }
    }

    /**
     * Helper function to calc deltas
     * @param deltas deltas accrued from previous Layer
     */
    public void addDelta(double[] deltas) {
        for (int i = 0; i < deltaSum.length; i++) {
            deltaSum[i] += deltas[i];
        }
    }

    /**
     * Getter for activation value
     * @return the current activation value for Neuron
     */
    public double getValue() {
        return activation;
    }



}
