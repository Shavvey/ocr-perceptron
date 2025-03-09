package com.perceptron.nn;
import java.util.Vector;

/**
 * Layers class is the work-horse of the whole neural network. It will encapsulate
 * all the collection of neurons, and be able to capture the main behaviour of
 * the Neural Network.
 * @author: Cole Johnson
 */
public class Layer {
    private final int neuronCount;
    private final ActivationFunction af;
    private final LossFunction lf;
    private final Vector<Neuron> neurons;
    Layer(int neuronCount, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = neuronCount;
        this.af = af;
        this.lf = lf;
        this.neurons = new Vector<>(neuronCount);
        for (int i = 0; i < neuronCount; i++) {
            // TODO: init based on random bias, activation, and weights!
            neurons.add(new Neuron(0,0));
        }
    }
}
