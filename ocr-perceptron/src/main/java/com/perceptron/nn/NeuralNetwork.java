package com.perceptron.nn;

import java.util.ArrayList;

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
    public final ArrayList<Layer> layers;

    /**
     * Constructor to make a neural network, based on layer configuration.
     * @param layers list of Layers to include how each {@link Layer} should be
     * configured inside the network
     */
    public NeuralNetwork(ArrayList<Layer> layers) {
        this.layers = layers;
        this.inputLayer = layers.getFirst();
        this.outputLayer = layers.getLast();
        this.numLayers = layers.size();
    }

    /**
     * Display out the config of the neural network (delegated to the Layer Level)
     */
    public void display() {
        for (Layer l : layers) {
            // delegate task to Layers
            l.display();
        }
    }

    /**
     * propagates initial activations of input {@link Layer} to next layers
     * based on weights, bias, and ActivationFunction {@link ActivationFunction}
     * until we reach the output layer.
     */
    public void feedforward() {
        for (int i = 0; i < numLayers - 1; i++) {
            // init two pointers one current Layer, the other next Layer
            Layer current = layers.get(i);
            current.feedforward();

        }
    }

    /**
     * Train network on the batch of training data (stochastic "vanilla" gradient descent)
     */
    public void network_train() {
        // feedforward
        this.feedforward();
        // find errors

    }


}
