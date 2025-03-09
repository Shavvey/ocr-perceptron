package com.perceptron.nn;
import java.util.Vector;

/**
 * Layers class is the work-horse of the whole neural network. It will encapsulate
 * all the collection of neurons, and be able to capture the main behaviour of
 * the Neural Network.
 * @author: Cole Johnson
 */
public class Layer {
    final int neuronCount;
    int nextLayerLength = 0;
    final ActivationFunction af;
    final LossFunction lf;
    final Vector<Neuron> neurons;
    Layer(int neuronCount, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = neuronCount;
        this.af = af;
        this.lf = lf;
        this.neurons = new Vector<>(neuronCount);
        for (int i = 0; i < neuronCount; i++) {
            neurons.add(new Neuron());
        }
    }

    /**
     * Helper method to set activations of the neuron in its own layer
     * @param a array of newly computed activations values
     */
    public void setActivations(int a[]) {
        for (int i = 0; i < neuronCount; i++) {
            neurons.get(i).activation = a[i];
        }
    }

    /**
     * Uses {@link ActivationFunction} and {@link Neuron} weights and
     * this layers activations to determine what the activations
     * for the next layer should be
     * @return new activations given weights and activations of this layer
     */
    public double[] feedforward() {
    }
}
