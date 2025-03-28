package com.perceptron.nn;

import com.perceptron.test_train.DataFrame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Neural network class is an abstraction of separate smaller class
 * <ol>
 *     <li> {@link Neuron}: a simplified neuron with an activation value, weight, etc. </li>
 *     <li> {@link Layer}: a collection of neurons equipped with an activation and loss function for training </li>
 * </ol>
 * @author Cole Johnson
 * @version 1.0
 */
public class NeuralNetwork implements Serializable {
    public Layer inputLayer;
    public Layer outputLayer;
    public int numLayers;
    public final ArrayList<Layer> layers;
    public final CostFunction cf;

    /**
     * Constructor to make a neural network, based on layer {@link Layer} configuration.
     * @param layers a list of Layers that dictate structure of neural network
     */
    public NeuralNetwork(CostFunction cf, Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.numLayers = this.layers.size();
        // it's useful to hold references of input and output layers
        this.inputLayer = this.layers.getFirst();
        this.outputLayer = this.layers.getLast();
        this.cf = cf;
        // connect each layer to each other
        for (int i = 0; i < numLayers; i++) {
            Layer l = getLayer(i);
            int prevCount = this.getLayerCount(i - 1);
            int nextCount = this.getLayerCount(i + 1);
            // instantiate all the neurons needed for each layer
            // each outgoing and incoming connections for each neuron
            l.makeNeurons(prevCount, nextCount);
        }
        // make create fully connected layers
        this.connect();

    }

    /**
     * Make full connections using previous and next layer pointers
     */
    public void connect() {
        for (int i = 0; i < numLayers - 1; i++) {
            // lazy approach we get the layer if it exists to provides use with null if not
            Layer current = layers.get(i);
            Layer next = layers.get(i + 1);
            // make outgoing connections for *current* layer
            // and then make connections
            current.connect(next);
        }
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
     * Returns back a layer in neural network
     * @param index index of {@link Layer}
     * @return if index is valid, next back that Layer, otherwise return null
     */
    public Layer getLayer(int index) {
        if(index < 0 || index > numLayers - 1) {
            return null;
        } else {
            return this.layers.get(index);
        }
    }

    /**
     * Return number of neurons in given {@link Layer}.
     * If the Layer is undefined, return zero.
     * @param index index of Layer inside neural network
     * @return the  number of neurons inside the Layer
     */
    public int getLayerCount(int index) {
        Layer l = this.getLayer(index);
        if (l == null) {
            // if layer is undefined, return zero
            return 0;
        } else {
            return l.neuronCount;
        }
    }

    /**
     * propagates initial activations of input {@link Layer} to next layers
     * based on weights, bias, and {@link ActivationFunction}
     * until we reach the output layer.
     */
    public void feedforward() {
        for (int i = 1; i < numLayers; i++) {
            Layer l = layers.get(i);
            l.feedforward();
        }
    }

    /**
     * @param df DataFrame to make prediction
     * @return forward-pass activations of output Layer {@link Layer} which
     * represents a prediction made with current config of neural network
     */
    public double[] prediction(DataFrame df) {
        // hold input inside input layers of neural network
        inputLayer.setActivations(df.flatten());
        // propagate activations forward through current configuration
        this.feedforward();
        // return the activations of output layer
        return outputLayer.getActivations();
    }

    /**
     * Workhorse of training algorithm, compute cost, and feedback accrued 'delta'
     * @param t 'truth'/expected values
     * @param p prediction made of neural network
     */
    public void feedback(double[] t, double[] p) {
        // compute initial deltas at the output layer
        this.outputLayer.setOutputDeltas(t, p);
        // create reverse iterator to feedback to deltas to hidden layers
        Iterator<Layer> il = layers.reversed().iterator();
        il.next();
        while (il.hasNext()) {
            Layer l = il.next();
            if(!l.isInput()) {
                l.feedback();
            }
        }

    }

    /**
     * Adjust weights and biases after accruing deltas in feedback step
     * @param learning_rate user set value, determines how aggressive
     * the adjustment of weights and biases is
     */
    public void learn(double learning_rate) {
        // create reverse iterator to feedback to deltas to hidden layers
        Iterator<Layer> il = layers.reversed().iterator();
        il.next();
        while (il.hasNext()) {
            Layer l = il.next();
            if (!l.isInput()) {
                // learn at layer level
                l.learn(learning_rate);
            }
        }
    }

    /**
     * 
     * @param learning_rate
     */
    public void train(double learning_rate) {

    }
}
