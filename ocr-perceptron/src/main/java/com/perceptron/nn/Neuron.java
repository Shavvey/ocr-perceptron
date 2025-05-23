package com.perceptron.nn;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * Neuron the main unit of the {@link NeuralNetwork}
 * contains the basic information we need during test, training
 * and operation: weights, activation, and bias.
 *
 * @author: Cole Johnson
 */
public class Neuron implements Serializable {
    // 'brightness' of the activated neuron, based on `ActivationFunction`
    double activation;
    // weighted sum
    double z;
    // collection of connected weights to the next layer
    final ArrayList<Connection> in;
    final ArrayList<Connection> out;
    double bias;
    // activation function needed for backpropagation and feedforward
    final ActivationFunction af;
    double dB;
    double delta;
    int deltaStep;

    /**
     * Create a new Neuron.
     *
     * @param prevCount previous Layer neuron count
     * @param nextCount next Layer neuron count
     */
    Neuron(int prevCount, int nextCount, ActivationFunction af) {
        // init activation to zero
        this.activation = 0;
        // init bias to zero
        this.bias = 0;
        in = new ArrayList<>(prevCount);
        out = new ArrayList<>(nextCount);
        this.af = af;
    }


    /**
     * Getter for the activation value.
     *
     * @return the current activation value for Neuron
     */
    public double getActivation() {
        return activation;
    }

    /**
     * Setting for the activation value of the Neuron.
     *
     * @param a new activation for the Neuron
     */
    public void setActivation(double a) {
        this.activation = a;
    }

    /**
     * Return all outgoing connections of Neuron.
     *
     * @return array of weights to each outgoing neuron
     */
    public double[] getWeights() {
        final int numConn = out.size(); // find number of outgoing connections
        double[] w = new double[numConn];
        for (int i = 0; i < numConn; i++) {
            w[i] = this.out.get(i).weight;
        }
        return w;
    }

    /**
     * Every Neuron iterates through all incoming weights and
     * previous activations, through {@link Connection}, with an input bias
     * to obtain weighted sum 'z'. Then we apply the non-linear
     * {@link ActivationFunction} to get a new activation for the Neuron.
     */
    public void feedforward() {
        double sum = bias;
        for (Connection incoming : in) {
            double activation = incoming.input.activation;
            double weight = incoming.weight;
            sum += activation * weight;
        }
        this.z = sum;
        this.activation = af.eval(sum);
    }

    /**
     * Feedback at the neuron level. Determine the "delta", which
     * represent the changes we would like to make to the network
     * for each training sample we encounter.
     */
    public void feedback() {
        double sum = 0;
        double dZ = af.derivativeEval(z);
        for (Connection outgoing : out) {
            sum += (outgoing.output.getDelta() * outgoing.weight);
        }
        this.delta = sum * dZ;
        // add formed deltas to our incoming weights
        for (Connection incoming : in) {
            // accrue deltas for *incoming weights*
            incoming.addDelta(this.delta);
        }
        // sum
        dB += delta;
        // increment number of delta steps
        deltaStep++;

    }

    /**
     * Adjust weights and biases after accruing deltas in feedback step
     *
     * @param learningRate user set value, determines how aggressive
     *                     the adjustment of weights and biases is
     */
    public void learn(double learningRate) {
        // adjust the bias the of the neuron based on accrued deltas
        // which were calculated during backpropagation
        this.bias -= learningRate * dB;
        for (Connection c : in) {
            // adjust weights of all incoming connections
            c.learn(learningRate);
        }
        // reset accrued deltas
        dB = 0;
        delta = 0;
        deltaStep = 0;

    }

    /**
     * Helper function that is used to set the delta
     *
     * @return delta value of neuron
     */
    public double getDelta() {
        return delta;
    }

    /**
     * Helper function to check if Neuron is a part of input layer.
     * Works by examining if we have any incoming connections (which
     * an output layer will not have).
     *
     * @return boolean value representing if neuron is in input layer.
     */
    public boolean isInput() {
        return in.isEmpty();
    }

    /**
     * Helper function to check if Neuron is a part of output layer.
     * Works by examining if we have any outgoing connections (which
     * an output layer will not have).
     *
     * @return boolean value representing if neuron is in output layer.
     */
    public boolean isOutput() {
        return out.isEmpty();
    }


}
