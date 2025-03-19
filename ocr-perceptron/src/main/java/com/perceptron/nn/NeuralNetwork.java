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
    public Layer inputLayer;
    public Layer outputLayer;
    int numLayers;

    /**
     * Constructor to make a neural network, based on layer configuration.
     * @param config includes how each {@link Layer} should be
     * configured inside the network
     */
    public NeuralNetwork(Vector<Layer> config) {
        this.inputLayer = config.firstElement();
        this.outputLayer = config.lastElement();
        this.numLayers = config.size();
    }

    /**
     * Display out the config of the neural network (delegated to the Layer Level)
     */
    public void display() {
        Layer l = inputLayer;
        while (l != null) {
            // print all other layers in linked list
            // delegate to Layer via it's implemented display
            l.display();
            l = l.next;
        }
    }

    /**
     * propagates initial activations of input {@link Layer} to next layers
     * based on weights, bias, and ActivationFunction {@link ActivationFunction}
     * until we reach the output layer.
     */
    public void feedforward() {
        Layer l = inputLayer;
        // loop until we reach output layer
        // (output has no connected neurons, so doesn't need to feedforward)
        while (l.next != null) {
            // delegate task to layer level
            l.feedforward();
            // goto next layer
            l = l.next;

        }
    }


}
