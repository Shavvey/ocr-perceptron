package com.perceptron.nn;

import com.perceptron.test_train.DataFrame;

import java.util.ArrayList;
import java.util.Arrays;


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
    public final double LEARNING_RATE = 0.1;
    public final int EPOCHS = 1100;
    public final CostFunction cf;

    /**
     * Constructor to make a neural network, based on layer {@link Layer} configuration.
     * @param layers a list of Layers that dictate structure of neural network
     */
    public NeuralNetwork(CostFunction cf, Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.numLayers = this.layers.size();
        this.inputLayer = this.layers.getFirst();
        this.outputLayer = this.layers.getLast();
        this.cf = cf;
        // make it so we can access references to previous Layers
        // TODO: maybe refactor this, I wouldn't call this good
        makePrevConn();
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
     * propagates initial activations of input {@link Layer} to next layers
     * based on weights, bias, and ActivationFunction {@link ActivationFunction}
     * until we reach the output layer.
     */
    public void feedforward() {
        // NOTE: we ignore the last layer in list because it
        // is the output layer and has no next layer to pass new activations to
        for (int i = 0; i < numLayers - 1; i++) {
            Layer current = layers.get(i);
            current.feedforward();

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
        // make prediction based on current configuration of nn
        double[] p = prediction(df);
        //truth values (what are activations should be given the DataFrame)
        double[] t = df.getTrueValues();
        // compute cost
        double cost = cf.cost(p, df.getTrueValues());
        // first create deltas for output, then use backpropagation to feedback deltas
        int outputLength = outputLayer.neuronCount;
        double[] deltas = new double[outputLength];
        // compute first set of deltas
        for (int i = 0; i < outputLength; i++) {
            deltas[i] = (p[i]  - t[i])*outputLayer.af.derivativeEval(outputLayer.z[i]);
        }
        // backpropagation of result through the hidden layers of network
        for (int i = numLayers - 1; i < 0; --i) {
            Layer hidden = layers.get(i);
            hidden.getDelta();
        }


    }

    /**
     * Connect each Layer to their previous Layer
     */
    public void makePrevConn() {
        for (int i = numLayers - 1; i < 0; --i) {
            Layer current = layers.get(i);
            current.prev = layers.get(i-1);
        }
    }



}
