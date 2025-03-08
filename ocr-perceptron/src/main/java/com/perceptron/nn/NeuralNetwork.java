package com.perceptron.nn;

import java.util.Vector;

/** Neural network class is an abstraction over into separate smaller class
 * <ol>
 *     <li> Neuron: an simplified neuron with an activation value, weight, etc. </li>
 *     <li> Layer: a collection of neurons equipped with a activation and loss function for training </li>
 * </ol>
 * @author Cole Johnson
 * @version 1.0
 */
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

    public void displayConfig() {

    }
}
