package com.perceptron.nn;

import java.util.Vector;

public class NeuralNetwork {
    private Layer inputLayer;
    private Layer outputLayer;
    private Vector<Layer> hiddenLayers;
    // neural network should take a vector of configured layers
    // we need to enforce two conditions:
    // first layer must be an input layer
    // last layer must be an output layer
    NeuralNetwork(Vector<Layer> config) {
        this.inputLayer = config.removeFirst();
        this.outputLayer = config.removeLast();
        this.hiddenLayers = config; // add the rest of the hidden layers
    }
}
