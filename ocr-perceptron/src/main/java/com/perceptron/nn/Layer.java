package com.perceptron.nn;
import com.perceptron.util.Transpose;

import java.util.ArrayList;
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
    // should be a linked list
    final Layer next;
    Layer prev;
    // weighted sum of previous activations stored
    double[] z;
    // accrued 'delta'--basically changes we would like to make to the network
    double[] deltaSum;

    /**
     * Main constructor for Layer class
     * @param count count of neurons in <em>this</em> layer
     * @param next reference to <em>next</em> layer
     * @param af ActivationFunction used to determine the activity of next neural layer
     */
    public Layer(int count, Layer next, ActivationFunction af) {
        // init neural layer based on layer count, activation, and loss function
        this.neuronCount = count;
        this.af = af;
        this.next = next;
        this.neurons = new ArrayList<>(neuronCount);
        // add each individual neuron inside the layer
        for (int i = 0; i < neuronCount; i++) {
            Neuron n =  new Neuron(this.getNextNeuronCount());
            n.randomizeWeights(neuronCount);
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
     * Set weights for each neuron in the Layer
     * @param w weight matrix to set Neurons
     */
    public void setWeights(double[][] w) {
        for (int i = 0; i < neuronCount; i++) {
            Neuron n = neurons.get(i);
            if (next.neuronCount >= 0)
                System.arraycopy(w[i], 0, n.weights, 0, next.neuronCount);
        }
    }

    /**
     * create 2D matrix of all the weights on this layer
     * @return weights of layer inside a 2D array
     */
    public double[][] getWeights() {
        double[][] w = new double[this.getNextNeuronCount()][neuronCount];
        // TODO: make a more efficient array copy (will speed up other functions!)
        // an more efficient implementation would be a mem copy and transpose!
        for (int j = 0; j < this.getNextNeuronCount(); j++) {
            for (int i = 0; i < this.neuronCount; i++) {
                w[j][i] = neurons.get(i).weights[j];
            }
        }
        return w;
    }

    /**
     * Create new 2D array that represents the transpose of the weights
     * @return new transposed weight matrix
     */
    public double[][] getTransposedWeights() {
        return Transpose.t(this.getWeights());
    }

    /**
     * Quickly display what the weights matrix looks like, for testing purposes
     */
    public void displayWeights() {
        double[][] w = this.getWeights();
        StringBuilder sb = new StringBuilder();
        for (double[] doubles : w) {
            String s = IntStream.range(0, w[0].length)
                    .mapToObj( i -> doubles[i] + " ")
                    .collect(Collectors.joining("", "{ ", "}"));
            sb.append(s).append('\n');
        }
        System.out.println(sb);
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
        final int nextNeuronCount = this.getNextNeuronCount();
        // get all weights of the layer
        double[][] w = this.getWeights();
        // get all activations of layer
        double[] a = this.getActivations();
        // get all biases of *next* layer
        double[] b = this.next.getBias();
        // store weighted sum
        double[] z = new double[nextNeuronCount];
        // calculate new layer of activations, for the next layer of neurons
        for (int j = 0; j < nextNeuronCount; j++) {
            double sum = b[j]; // init sum by bias
            for (int i = 0; i < neuronCount; i++) {
                // calculate weighted sum of activations and weights of current Layer
                sum += w[j][i] * a[i];
            }
            // store weighted sum (useful for backpropagation later)
            z[j] = sum;
            // eval weighted sum under next Layer ActivationFunction
            next.neurons.get(j).activation = next.af.eval(sum);
        }
        // store the new weighted sums (we'll use these during backpropagation)
        this.z = z;
    }

    /**
     * Helper method to show how the Layer is configured
     */
    public void display() {
        System.out.println("Neuron Count: " + neuronCount);
        System.out.println("Next Layer Neuron Count: " + this.getNextNeuronCount());
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
     * Getter to return next Layer's neuron count
     * @return The number of Neurons in the next layer
     * (returns zero, if the next Layer is not defined).
     */
    public int getNextNeuronCount() {
        return (next == null) ? 0 : this.next.neuronCount;
    }

    /**
     * Getter for the number of neurons in Layer.
     * @return count of Neurons in current Layer
     */
    public int getNeuronCount() {
        return neuronCount;
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


    /**
     * compute delta-step during backpropagation, called for each <em>hidden</em> Layer
     * @param prev_deltas values obtained from
     * @return delta that we must accumulate during stochastic gradient descent
     */
    public double[] getDelta(double[] prev_deltas) {
        double[] a = this.getActivations();
        double[] deltas = new double[neuronCount];
        // very similar to feedforward
        double[][] wt = this.getTransposedWeights();
        for (int j = 0; j < wt.length; j++) {
            // init new delta
            deltas[j] = 0;
            for (int i = 0; i < wt[0].length; i++) {
                // compute weighted sum

            }
        }
        return deltas;

    }

    /**
     * Check if current Layer is output (needed when computing the loss)
     * @return boolean value representing whether Layer is output or not
     */
    public boolean isOutput() {
        return next == null;
    }

    /**
     * During training, we accrue deltas that represent changes to the network we would like to make.
     * @return an array of delta, one for each neuron inside the Layer
     */
    public double[] getDelta()  {
        return new double[]{0};
    }
}
