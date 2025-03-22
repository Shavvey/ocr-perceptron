package com.perceptron.nn;

import com.perceptron.test_train.DataFrame;

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
public class NeuralNetwork {
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
     * @param index index of Layer {@link Layer}
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
     * Return number of neurons in given Layer {@link Layer}.
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
     * based on weights, bias, and ActivationFunction {@link ActivationFunction}
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
     * @param df DataFrame we train on
     */
    public void feedback(DataFrame df) {
        // make prediction
        double[] p = prediction(df);
        // get 'true'/expected values
        double[] t = df.getTrueValues();
        // first compute deltas for the output layer (kinda like a 'base' case)
        int idx = 0;
        for (Neuron n : outputLayer.neurons) {
            n.delta = (p[idx] - t[idx]) * n.af.derivativeEval(n.z);
            idx++;
        }

        Iterator<Layer> rev = layers.reversed().iterator();
        // skip output layer (just calculated that in the first step)
        rev.next();
        // iterate backwards over layers using reversed iterator (calc deltas in neurons at Layer level)
        // NOTE: this involves of a lot delegating the problem into smaller sub-problems
        while(rev.hasNext()) {
            Layer l = rev.next();
            l.display();
            l.feedback();
        }
    }




}
