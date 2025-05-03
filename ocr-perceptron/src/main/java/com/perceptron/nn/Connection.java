package com.perceptron.nn;

import java.io.Serializable;

/**
 * Models individual connections for each {@link Neuron}
 *
 * @author Cole Johnson
 * @version 1.0
 */
class Connection implements Serializable {
    final Neuron input;
    final Neuron output;
    double weight;
    double dZ;

    /**
     * @param input  incoming neuron
     * @param output outgoing neuron
     * @param weight the weight between these neurons
     */
    Connection(Neuron input, Neuron output, double weight) {
        this.input = input;
        this.output = output;
        this.weight = weight;
    }

    /**
     * learn (adjust weights on connection) after each step
     *
     * @param learningRate controlled by learning rate of network
     */
    void learn(double learningRate) {
        this.weight -= learningRate * this.dZ;
        // reset accrued weight deltas after learning
        this.dZ = 0;
    }

    /**
     * method called during feedback/backpropagation to accrue changes we would
     * like to make to the connection during "mini" batches in training.
     *
     * @param delta add delta values accrued during backpropagation
     */
    void addDelta(double delta) {
        this.dZ += delta * this.input.getActivation();
    }
}