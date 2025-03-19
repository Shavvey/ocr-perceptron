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
    // activation function
    final ActivationFunction af;
    // loss function
    final LossFunction lf;
    final Vector<Neuron> neurons;
    // should be a linked list
    final Layer next;

    /**
     * Main constructor for Layer class
     * @param count count of neurons in <em>this</em> layer
     * @param next reference to <em>next</em> layer
     * @param af ActivationFunction used to determine the activity of next neural layer
     * @param lf LossFunction used to describe the performance/loss of neural layer
     */
    public Layer(int count, Layer next, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = count;
        this.af = af;
        this.lf = lf;
        this.next = next;
        this.neurons = new Vector<>(this.getNextNeuronCount());
        for (int i = 0; i < neuronCount; i++) {
            Neuron n = new Neuron(this.getNextNeuronCount());
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
        double[][] weights = new double[neuronCount][this.getNeuronCount()];
        for (int j = 0; j < neuronCount; j++) {
            for (int i = 0; i < next.neuronCount; i++) {
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
        // calculate new layer of activations, for the next layer of neurons
        double[] activations = new double[next.neuronCount];
        for (int j = 0; j < neuronCount; j++) {
            double a = 0.00F; // compute weighted sum
            for (int i = 0; i < next.neuronCount; i++) {
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

    /**
     * Helper method to show how the Layer is configured
     */
    public void display() {
        System.out.println("Neuron Count: " + neuronCount);
        System.out.println("Next Layer Neuron Count: " + next.neuronCount);
        System.out.println("Activation Function: " + af.toString());
        System.out.println("Loss Function: " + lf.toString());
    }

    /**
     * Getter for the collection of neurons under the layer
     * @return a vector that contains all layer neurons
     */
    public Vector<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Getter for Layer-level LossFunction {@link LossFunction}
     * @return
     */
    public LossFunction getLf() {
        return lf;
    }

    /**
     * Getter for Layer-level ActivationFunction {@link ActivationFunction}
     * @return
     */
    public ActivationFunction getAf() {
        return af;
    }

    /**
     * Getter to return next Layer's neuron count
     * @return The number of Neurons in the next layer
     * (returns zero, if the next Layer is not defined).
     */
    public int getNextNeuronCount() {
        return (next == null) ? 0 : this.next.neuronCount;
    }

    public int getNeuronCount() {
        return neuronCount;
    }
}
