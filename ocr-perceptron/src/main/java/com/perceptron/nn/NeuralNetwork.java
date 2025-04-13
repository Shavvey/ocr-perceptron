package com.perceptron.nn;

import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;

import java.io.*;
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
     * Constructor to make a neural network, based on layer {@link Layer} configuration
     * passed through the constructor, done in a sequential manner.
     * @param layers a list of Layers that dictate structure of neural network
     * throws an {@link IllegalArgumentException} if the network is composed
     * of less than two layers. Should be passed sequentially {input, hidden(s), output}
     */
    public NeuralNetwork(CostFunction cf, Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.numLayers = this.layers.size();
        if (numLayers < 2) {
            throw new IllegalArgumentException("[ERROR]: Need at least two layers!");
        }
        // it's useful to hold references of input and output layers
        this.inputLayer = this.layers.getFirst(); // first element should be input
        this.outputLayer = this.layers.getLast(); // last element should be output
        // cost function that is minimized during training
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
        // loop through each layer
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
    public double[] getPredictionVector(DataFrame df) {
        // hold input inside input layers of neural network
        inputLayer.setActivations(df.flatten());
        // propagate activations forward through current configuration
        this.feedforward();
        // return the activations of output layer
        return outputLayer.getActivations();
    }

    /**
     * Workhorse of training algorithm, compute cost, and feedback accrued 'delta'
     * @param df {@link DataFrame} back-propagated deltas based on the {@link DataFrame}
     */
    public void feedback(DataFrame df) {
        double[] t = df.getTrueValues();
        double[] p = outputLayer.getActivations();
        // compute initial deltas at the output layer
        this.outputLayer.setOutputDeltas(t, p);
        // iterate through all layers **except the input layer** to accrue our set of deltas
        // during each feedback step
        for (int i = numLayers - 2; i > 0; i--) {
            Layer l = getLayer(i);
            l.feedback();
        }

    }

    /**
     * Adjust weights and biases after accruing deltas in feedback step
     * @param learning_rate user set value, determines how aggressive
     * the adjustment of weights and biases is
     */
    public void learn(double learning_rate) {
        // iterate through all layers **except the input layer**, adjust all
        // the weights and biases on each neuron inside the layers
        for (int i = 1; i < numLayers; i++) {
            this.getLayer(i).learn(learning_rate);
        }
    }

    /**
     * Main method during training of the neural network
     * @param learning_rate adjust how 'aggressively' we adjust the weights
     * @param epochs number of complete passes through the data
     * @param batchSize number of labeled images inside each batch
     * and biases inside the network (useful hyper-parameter in gradient descent)
     */
    public void train(double learning_rate, int epochs, int batchSize) {
        ResourceManager rm = new ResourceManager();
        int trainSize = ResourceManager.getTrainSize();
        if (trainSize % batchSize != 0) {
            throw new IllegalArgumentException("[ERROR]: Batch size needs to be divisible by the length of the training set" + trainSize);
        }
        // first, use the resource manager to start pulling data frame
        Iterator<DataFrame> it = rm.getTrainingData();
        int parsed = 0;
        for (int e = 0; e < epochs; e++) {
            while (it.hasNext()) {
                for (int b = 0; b < batchSize; b++) {
                    DataFrame df = it.next();
                    // make a prediction
                    double[] d = this.getPredictionVector(df);
                    // feedback values
                    this.feedback(df);
                    parsed++;
                }
                this.learn(learning_rate); // learn off of accrued deltas
            }
            rm.reset();
        }
        System.out.println("Total frames parsed: " + parsed);


    }

    /**
     * Simple getter method that calls {@link CostFunction implementation}.
     * @param df {@link DataFrame} sample to compute cost of (approximates learning of labeled item)
     * @return the cost of given instance of labeled {@link DataFrame}.
     */
    public double getCost(DataFrame df) {
        double[] p = this.outputLayer.getActivations();
        double[] t = df.getTrueValues();
        return this.cf.cost(p, t);
    }

    /**
     * Make prediction of a given {@link DataFrame}.
     * @param df dataframe network predicts off of
     * @return predicted label [0-9], picked based on highest activations
     */
    public int makePrediction(DataFrame df) {
        int prediction = 0;
        double max = -1000F;
        double[] p = getPredictionVector(df);
        for (int i = 0; i < p.length; i++) {
            if (max < p[i]) {
                max = p[i];
                prediction = i;
            }
        }
        return prediction;
    }

    /**
     * Test the network using the {@link ResourceManager}'s training data.
     * Prints out the number of correct and incorrect predictions.
     */
    public void test() {
        ResourceManager rm = new ResourceManager();
        int misses = 0;
        int hits = 0;
        Iterator<DataFrame> it = rm.getTestData();
        // iterate through entire testing training set
        while (it.hasNext()) {
            DataFrame df = it.next();
            int prediction = makePrediction(df);
            int label = Integer.parseInt(df.getLabel());
            if (prediction == label) {
                hits++;
            } else {
                misses++;
            }
        }
        System.out.println("Incorrect predictions:" + misses);
        System.out.println("Correct predictions:" + hits);
    }

    /**
     * Serializes model to a .ser file
     * @param name name of the new saved model
     * throws an {@link IOException} if the model given can't be  serialized.
     */
    public void serialize(String name) {
        final String PATH = "src/main/resources/models/";
        try {
            FileOutputStream fileOutput = new FileOutputStream(PATH + name + ".ser");
            ObjectOutputStream outStream = new ObjectOutputStream(fileOutput);
            outStream.writeObject(this);
            outStream.close();
            fileOutput.close();
        } catch (IOException e) {
            System.err.println("[ERROR]: Could not serialize object!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes model from a .ser file
     * throws an {@link IOException} if file doesn't exist or can't be deserialized.
     * @param name name of the saved model inside `src/main/resources/models/`
     * @return the new deserialized {@link NeuralNetwork}
     */
    public static NeuralNetwork deserialize(String name) {
        final String PATH = "src/main/resources/models/";
        NeuralNetwork nn = null;
        try {
            FileInputStream fileInput = new FileInputStream(PATH + name + ".ser");
            ObjectInputStream input = new ObjectInputStream(fileInput);
            nn = (NeuralNetwork) input.readObject();

        } catch (IOException e) {
            System.err.println("[ERROR]: Could not deserialize object!");
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR]: Could not find object!");
            throw new RuntimeException(e);
        }

        return nn;
    }

}
