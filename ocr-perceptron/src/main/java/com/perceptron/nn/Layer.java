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
        double[][] weights = new double[neuronCount][this.getNeuronCount()];
        // TODO: make a more efficient array copy (will speed up other functions!)
        for (int j = 0; j < neuronCount; j++) {
            if (next.neuronCount >= 0)
                System.arraycopy(this.neurons.get(j).weights, 0, weights[j], 0, next.neuronCount);
        }
        return weights;
    }

    /**
     * Set the bias for the entire Layer of neurons.
     * @param b bias for current Layer
     */
    public void setBias(double b) {
        for (int i = 0; i < neuronCount; i++) {
            // set new bias value
            neurons.get(i).bias = b;
        }
    }

    /**
     * Uses {@link ActivationFunction} and {@link Neuron} weights and
     * this layers activations to determine what the activations
     * for the next layer should be
     */
    public void feedforward() {
        // calculate new layer of activations, for the next layer of neurons
        double[] activations = new double[next.neuronCount];
        for (int j = 0; j < next.neuronCount; j++) {
            double a = 0.00F; // store weighted sum we will calculate
            for (int i = 0; i < neuronCount; i++) {
                // get neuron in layer
                Neuron n = this.neurons.get(i);
                // get weights associated with neuron
                double weight = n.weights[j];
                // weighted sum of activations with weights plus a plus
                // activation function will squish resulting sum to (-1,1)
                a += (n.activation * weight) + n.bias;
            }
            // update next-layer neuron based on weighted sum evaluated to ActivationFunction
            next.neurons.get(j).activation = af.eval(a);
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
    public Vector<Neuron> getNeurons() {
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

    public double[] getActivations() {
        double[] a = new double[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            a[i] = neurons.get(i).activation;
        }
        return a;
    }
}
