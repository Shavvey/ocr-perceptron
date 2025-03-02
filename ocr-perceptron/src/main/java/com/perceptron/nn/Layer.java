package com.perceptron.nn;
import java.util.Vector;

public class Layer {
    private final int layerCount;
    private final ActivationFunction af;
    private final LossFunction lf;
    private final Vector<Neuron> neurons;
    Layer(int layerCount, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.layerCount = layerCount;
        this.af = af;
        this.lf = lf;
        this.neurons = new Vector<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            neurons.add(new Neuron(0,0));
        }
    }
}
