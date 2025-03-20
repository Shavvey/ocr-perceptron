package com.perceptron.nn;
import java.util.ArrayList;
import java.util.Vector;
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
    final LossFunction lf;
    final ArrayList<Neuron> neurons;
    // should be a linked list
    final Layer next;
    double[] deltaSum;

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
        this.neurons = new ArrayList<>(neuronCount);
        for (int i = 0; i < neuronCount; i++) {
            Neuron n =  new Neuron(this.getNextNeuronCount());
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
        // get all weights of the layer
        double[][] w = this.getWeights();
        // get all activations of layer
        double[] a = this.getActivations();
        // get all biases of *next* layer
        double[] b = this.next.getBias();
        // calculate new layer of activations, for the next layer of neurons
        for (int j = 0; j < this.getNextNeuronCount(); j++) {
            double sum = 0;
            for (int i = 0; i < neuronCount; i++) {
                // calculate weighted sum of activations and weights of current Layer
                sum += w[j][i] * a[i];
            }
            // eval weighted sum under next Layer ActivationFunction
            next.neurons.get(j).activation = next.af.eval(sum + b[j]);
        }
    }

    /**
     * Helper method to show how the Layer is configured
     */
    public void display() {
        System.out.println("Neuron Count: " + neuronCount);
        System.out.println("Next Layer Neuron Count: " + this.getNextNeuronCount());
        System.out.println("Activation Function: " + af.toString());
        System.out.println("Loss Function: " + lf.toString());
    }

    /**
     * Getter for the collection of neurons under the layer
     * @return a vector that contains all layer neurons
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Getter for Layer-level LossFunction {@link LossFunction}
     * @return implemented LossFunction for this Layer
     */
    public LossFunction getLf() {
        return lf;
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
     * compute delta-step during backpropagation
     * @param pa previous activations recorded
     * @return delta that we must accumulate during stochastic gradient descent
     */
    public double[] feedback(double[] pa) {
        double[] delta = new double[neuronCount];
        return delta;
    }
}
