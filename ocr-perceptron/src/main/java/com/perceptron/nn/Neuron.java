package com.perceptron.nn;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * Neuron the main unit of the {@link NeuralNetwork}
 * contains the basic information we need during test, training
 * and operation: weights, activation, and bias
 * @author: Cole Johnson
 */
public class Neuron implements Serializable {
    // 'brightness' of the activated neuron, based on `ActivationFunction`
    double activation;
    // weighted sum
    double z;
    // collection of connected weight to the next layer
    final ArrayList<Connection> in;
    final ArrayList<Connection> out;
    double bias;
    // activation function needed for backpropagation and feedforward
    final ActivationFunction af;
    double deltaSum;
    double delta;

    /**
     * Create a new Neuron
     * @param prevCount previous Layer neuron count
     * @param nextCount next Layer neuron count
     */
    Neuron(int prevCount,  int nextCount, ActivationFunction af) {
        // init activation to zero
        this.activation = 0;
        // init bias to zero
        this.bias = 0;
        in = new ArrayList<>(prevCount);
        out = new ArrayList<>(nextCount);
        this.af = af;
    }



    /**
     * Getter for activation value
     * @return the current activation value for Neuron
     */
    public double getActivation() {
        return activation;
    }

    public void setActivation(double a) {
        this.activation = a;
    }

    /**
     * Return all outgoing connections of Neuron
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
     * every Neuron iterates through all incoming
     */
    public void feedforward() {
        double sum = bias;
        for (Connection incoming : in) {
            double activation = incoming.input.activation;
            double weight = incoming.weight;
            sum += activation*weight;
        }
        this.z = sum;
        this.activation = af.eval(sum);
    }



}
