package com.perceptron.nn;

import java.util.Vector;

public class NeuralNetwork {
    private Layer inputLayer;
    private Layer outputLayer;
    private Vector<Layer> hiddenLayers;

    NeuralNetwork(Vector<Layer> config) {
        this.inputLayer = config.removeFirst();
        this.outputLayer = config.removeLast();
        this.hiddenLayers = config;
    }
}
