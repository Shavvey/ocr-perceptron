package com.perceptron.nn;

import java.util.Vector;

/**
 * Neural network class is an abstraction of separate smaller class
 * <ol>
 *     <li> {@link Neuron}: an simplified neuron with an activation value, weight, etc. </li>
 *     <li> {@link Layer}: a collection of neurons equipped with a activation and loss function for training </li>
 * </ol>
 * @author Cole Johnson
 * @version 1.0
 */
public class NeuralNetwork {
    Layer inputLayer;
    Layer outputLayer;
    Vector<Layer> layers;
    int numLayers;

    /**
     * Constructor to make a neural network, based on layer configuration.
     * @param config includes how each {@link Layer} should be
     * configured inside the network
     */
    NeuralNetwork(Vector<Layer> config) {
        this.inputLayer = config.firstElement();
        this.outputLayer = config.lastElement();
        this.numLayers = config.size();
    }

    public void displayConfig() {

    }
}
