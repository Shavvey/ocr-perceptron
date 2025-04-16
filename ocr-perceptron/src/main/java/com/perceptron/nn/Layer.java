package com.perceptron.nn;

import com.perceptron.util.Stats;
import com.perceptron.util.Transpose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Layers class is the work-horse of the whole neural network. It will encapsulate
 * all the collection of neurons, and be able to capture the main behaviour of
 * the Neural Network.
 *
 * @author: Cole Johnson
 */
public class Layer implements Serializable {
    final int neuronCount;
    // activation function
    final ActivationFunction af;
    // loss function
    final ArrayList<Neuron> neurons;

    /**
     * Main constructor for Layer class
     *
     * @param count count of neurons in <em>this</em> layer
     * @param af    ActivationFunction used to determine the activity of next neural layer
     */
    public Layer(int count, ActivationFunction af) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = count;
        this.af = af;
        this.neurons = new ArrayList<>(neuronCount);
        // add each individual neuron inside the layer

    }

    /**
     * Helper method to make each {@link Neuron} in the Layer
     *
     * @param previousCount neuron count of <em>previous</em> Layer
     * @param nextCount     neuron count of <em>next</em> Layer
     */
    public void makeNeurons(int previousCount, int nextCount) {
        for (int i = 0; i < neuronCount; i++) {
            // pass previous layer count, next layer count, and local activation function
            Neuron n = new Neuron(previousCount, nextCount, af);
            neurons.add(n);
        }
    }

    /**
     * Make outgoing connections on this layer and
     * then form the corresponding incoming connections
     * on next layer
     *
     * @param next next Layer inside neural network
     */
    public void connect(Layer next) {
        for (Neuron n : neurons) {
            for (Neuron nn : next.neurons) {
                // obtain some random weight
                double weight = Stats.randDouble(Stats.MIN_WEIGHT_VAL, Stats.MAX_WEIGHT_VAL);
                // init *outgoing* for the neurons on *this* layer
                Connection conn = new Connection(n, nn, weight);
                // add outgoing neural connection
                n.out.add(conn);
                // init *incoming* connections for
                nn.in.add(conn);
            }
        }

    }


    /**
     * Helper method to set activations of the neuron in its own layer
     *
     * @param a array of newly computed activations values
     */
    public void setActivations(double[] a) {
        for (int i = 0; i < neuronCount; i++) {
            Neuron n = neurons.get(i);
            n.setActivation(a[i]);
        }
    }


    /**
     * Set weights for each neuron in the Layer
     *
     * @param w weight matrix to set Neurons
     */
    public void setWeights(double[][] w) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                Neuron n = neurons.get(i);
                n.out.get(j).weight = w[i][j];
            }
        }
    }

    /**
     * create 2D matrix of all the weights on this layer
     *
     * @return weights of layer inside a 2D array
     */
    public double[][] getWeights() {
        int conCount = neurons.getFirst().out.size();
        double[][] w = new double[neuronCount][conCount];
        for (int j = 0; j < neuronCount; j++) {
            for (int i = 0; i < conCount; i++) {
                // just use an array copy dummy
                Neuron n = neurons.get(j);
                w[j][i] = n.out.get(i).weight;
            }
        }
        return w;
    }

    /**
     * display the weights of the current Layer
     */
    public void displayWeights() {
        double[][] w = this.getWeights();
        for (double[] weights : w) {
            System.out.println(Arrays.toString(weights));
        }
    }

    /**
     * display the weights of the current Layer
     */
    public void displayTransposeWeights() {
        double[][] w = this.getTransposedWeights();
        for (double[] weights : w) {
            System.out.println(Arrays.toString(weights));
        }
    }

    /**
     * Create new 2D array that represents the transpose of the weights
     *
     * @return new transposed weight matrix
     */
    public double[][] getTransposedWeights() {
        return Transpose.t(this.getWeights());
    }


    /**
     * Return biases of entire Layer
     *
     * @return array of biases gathered from current Layer of neurons
     */
    public double[] getBias() {
        double[] b = new double[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            b[i] = neurons.get(i).bias;
        }
        return b;
    }

    /**
     * Helper method to return bias array
     */
    public void displayBias() {
        System.out.println(Arrays.toString(this.getBias()));
    }

    /**
     * Set the bias for the entire Layer of neurons.
     *
     * @param b bias for current Layer
     */
    public void setBias(double[] b) {
        for (int i = 0; i < b.length; i++) {
            neurons.get(i).bias = b[i];
        }
    }


    /**
     * Uses {@link ActivationFunction} and {@link Neuron} weights and
     * this layers activations to determine what the activations
     * for the next layer should be
     */
    public void feedforward() {
        for (Neuron n : neurons) {
            n.feedforward();
        }
    }

    /**
     * Helper method to show how the Layer is configured
     */
    public void display() {
        System.out.println("Neuron Count: " + neuronCount);
        System.out.println("Activation Function: " + af.toString());
    }

    /**
     * Getter for the collection of neurons under the layer
     *
     * @return an ArrayList that contains all layer neurons
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }


    /**
     * Getter for Layer-level ActivationFunction {@link ActivationFunction}
     *
     * @return implemented ActivationFunction for this Layer
     */
    public ActivationFunction getAf() {
        return af;
    }


    /**
     * Print out neural activations of Layer
     */
    public void displayActivations() {
        System.out.println(Arrays.toString(this.getActivations()));
    }

    /**
     * Getter to return all the activations of the neurons in this Layer
     *
     * @return array of activations in current Layer
     */
    public double[] getActivations() {
        double[] a = new double[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            a[i] = neurons.get(i).activation;
        }
        return a;
    }

    /**
     * Feedback function is delegated as series of sub-problems on the neural level
     */
    public void feedback() {
        for (Neuron n : neurons) {
            // delegate task to individual neurons
            n.feedback();
        }
    }

    /**
     * Helper function to check if Layer is an output layer.
     * Works by examining if we have any outgoing connections (which
     * an output layer will not have).
     *
     * @return boolean value representing if neuron is in output layer.
     */
    public boolean isInput() {
        return neurons.getFirst().isInput();
    }

    /**
     * Helper function to check if Layer is an input layer.
     * Works by examining if we have any incoming connections (which
     * an output layer will not have).
     *
     * @return boolean value representing if neuron is in output layer.
     */
    public boolean isOutput() {
        return neurons.getFirst().isOutput();
    }

    /**
     * Base case where we calculate deltas of output layer
     *
     * @param t 'truth'/expected values
     * @param p prediction values of network
     */
    public void setOutputDeltas(double[] t, double[] p) {
        // first compute deltas for the output layer (kinda like a 'base' case)
        int idx = 0;
        for (Neuron n : neurons) {
            n.delta = (p[idx] - t[idx]) * n.af.derivativeEval(n.z);
            idx++;
            for (Connection incoming : n.in) {
                incoming.addDelta(n.delta);
            }
            n.deltaSum += n.delta;
            n.deltaStep++;
        }
    }

    /**
     * Adjust weights and biases after accruing deltas in feedback step
     *
     * @param learning_rate user set value, determines how aggressive
     *                      the adjustment of weights and biases is
     */
    public void learn(double learning_rate) {
        for (Neuron n : neurons) {
            n.learn(learning_rate);
        }
    }

}
