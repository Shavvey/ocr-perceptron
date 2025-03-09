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
    // next layer's neuron count (useful to have this up front)
    int nextNeuronCount;
    // activation function
    final ActivationFunction af;
    // loss function
    final LossFunction lf;
    final Vector<Neuron> neurons;

    /**
     * Main constructor for Layer class
     * @param count count of neurons in <em>this</em> layer
     * @param nextCount count of neuron in <em>next</em> layer
     * @param af ActivationFunction used to determine the activity of next neural layer
     * @param lf LossFunction used to describe the performance/loss of neural layer
     */
    Layer(int count, int nextCount, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = count;
        this.af = af;
        this.lf = lf;
        this.nextNeuronCount = nextCount;
        this.neurons = new Vector<>(neuronCount);
        for (int i = 0; i < neuronCount; i++) {
            Neuron n = new Neuron(nextNeuronCount);
            n.randomizeWeights();
            neurons.add(n);
        }
    }

    /**
     * Helper method to set activations of the neuron in its own layer
     * @param a array of newly computed activations values
     */
    public void setActivations(double[] a) {
        for (int i = 0; i < neuronCount; i++) {
            neurons.get(i).activation = a[i];
        }
    }

    /**
     * create 2D matrix of all the weights on this layer
     * @return weights of layer inside a 2D array
     */
    public double[][] getWeights() {
        double[][] weights = new double[neuronCount][nextNeuronCount];
        for (int j = 0; j < neuronCount; j++) {
            for (int i = 0; i < nextNeuronCount; i++) {
                weights[j][i] = this.neurons.get(j).weights[i];
            }
        }
        return weights;
    }

    /**
     * Uses {@link ActivationFunction} and {@link Neuron} weights and
     * this layers activations to determine what the activations
     * for the next layer should be
     * @return new activations given weights and activations of this layer
     */
    public double[] feedforward() {
        double[] activations = new double[nextNeuronCount];
        for (int j = 0; j < neuronCount; j++) {
            double a = 0.00F; // compute weighted sum
            for (int i = 0; i < nextNeuronCount; i++) {
                Neuron n = this.neurons.get(j);
                double weight = n.weights[i];
                // weighted sum of activations with weights plus a plus
                // activation function will squish resulting sum to (-1,1)
                a = (n.activation * weight) + n.bias;
            }
            activations[j] = af.eval(a);
        }
        return activations;
    }

    public void display() {
        System.out.println("Neuron Count: " + neuronCount);
        System.out.println("Next Layer Neuron Count: " + nextNeuronCount);
        System.out.println("Activation Function: " + af.toString());
        System.out.println("Loss Function: " + lf.toString());
    }

    public Vector<Neuron> getNeurons() {
        return neurons;
    }

    public LossFunction getLf() {
        return lf;
    }

    public ActivationFunction getAf() {
        return af;
    }

    public int getNextNeuronCount() {
        return nextNeuronCount;
    }

    public int getNeuronCount() {
        return neuronCount;
    }
}
