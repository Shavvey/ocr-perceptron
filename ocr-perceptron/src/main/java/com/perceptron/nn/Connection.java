package com.perceptron.nn;
/**
 * models individual connections for each {@link Neuron}
 * @author Cole Johnson
 * @version 1.0
 */
class Connection {
    final Neuron input;
    final Neuron output;
    double weight;
    double deltaSum;

    /**
     * @param input incoming neuron
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
     * @param step controlled by learning rate of network
     */
    void learn(double step){
        this.weight -= step * this.deltaSum;
        this.deltaSum = 0;
    }

    /**
     * method called during feedback/backpropagation to accrue changes we would
     * like to make to the connection during "mini" batches in training.
     * @param delta add delta values accrued during backpropagation
     */
    void addDelta(double delta){
        this.deltaSum += delta * this.input.getActivation();
    }
}