package com.perceptron.nn;
import com.perceptron.util.Stats;
import com.perceptron.util.Transpose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    final ArrayList<Neuron> neurons;

    /**
     * Main constructor for Layer class
     * @param count count of neurons in <em>this</em> layer
     * @param af ActivationFunction used to determine the activity of next neural layer
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
     * @param previousCount neuron count of <em>previous</em> Layer
     * @param nextCount neuron count of <em>next</em> Layer
     */
    public void makeNeurons(int previousCount, int nextCount) {
        for (int i = 0; i < neuronCount; i++) {
            Neuron n  = new Neuron(previousCount, nextCount);
            neurons.add(n);
        }
    }

    /**
     * Make outgoing connections on this layer and
     * then form the corresponding incoming connections
     * on next layer
     * @param next next Layer inside neural network
     */
    public void connect(Layer next) {
        for (Neuron n : neurons) {
            for (Neuron nn : next.neurons) {
                // obtain some random weight
                double weight = Stats.randDouble(0, 1);
                // init *outgoing* for the neurons on *this* layer
                Connection outgoing = new Connection(n,nn, weight);
                // add outgoing neural connection
                n.out.add(outgoing);
                // init *incoming* connections for
                Connection incoming = new Connection(nn, n, weight);
                // add to incoming connections
                nn.in.add(incoming);
            }
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
     * Set weights for each neuron in the Layer
     * @param w weight matrix to set Neurons
     */
    public void setWeights(double[][] w) {
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[0].length; j++) {
                neurons.get(i).out.get(j).weight = w[i][j];
            }
        }
    }

    /**
     * create 2D matrix of all the weights on this layer
     * @return weights of layer inside a 2D array
     */
    public double[][] getWeights() {
        int conCount = neurons.getFirst().out.size();
        double[][] w = new double[neuronCount][conCount];
        for (int j = 0; j < neuronCount; j++) {
            for (int i = 0; i < conCount; i++) {
                // just use an array copy dummy
                w[j][i] = neurons.get(j).out.get(i).weight;
            }
        }
        return w;
    }

    public void displayWeights() {
        double[][] w = this.getWeights();
        System.out.println(Arrays.toString(w));
    }

    /**
     * Create new 2D array that represents the transpose of the weights
     * @return new transposed weight matrix
     */
    public double[][] getTransposedWeights() {
        return Transpose.t(this.getWeights());
    }


    /**
     * Return biases of entire Layer
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
     * Set the bias for the entire Layer of neurons.
     * @param b bias for current Layer
     */
    public void setBias(double[] b) {
        for (int i = 0; i < b.length ; i++) {
            neurons.get(i).bias = b[i];
        }
    }


    /**
     * Uses {@link ActivationFunction} and {@link Neuron} weights and
     * this layers activations to determine what the activations
     * for the next layer should be
     */
    public void feedforward() {

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
     * @return an ArrayList that contains all layer neurons
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }


    /**
     * Getter for Layer-level ActivationFunction {@link ActivationFunction}
     * @return implemented ActivationFunction for this Layer
     */
    public ActivationFunction getAf() {
        return af;
    }


    /**
     * Build out string of activation values via {@link StringBuilder}.
     * @return string representation of Layer activations
     */
    private String activationsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < neuronCount; i++) {
            double a = neurons.get(i).activation;
            sb.append(a).append(" ");
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Print out neural activations of Layer
     */
    public void displayActivations() {
        System.out.println(this.activationsToString());
    }

    /**
     * Getter to return all the activations of the neurons in this Layer
     * @return array of activations in current Layer
     */
    public double[] getActivations() {
        double[] a = new double[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            a[i] = neurons.get(i).activation;
        }
        return a;
    }

}
