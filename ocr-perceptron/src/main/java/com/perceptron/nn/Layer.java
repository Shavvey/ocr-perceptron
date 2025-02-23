package com.perceptron.nn;

public class Layer {
    private int layerCount;
    private ActivationFunction af;
    private LossFunction lf;
    Layer(int layerCount, ActivationFunction af, LossFunction lf) {
        // init neural layer based on layer count, activation, and loss function
        this.layerCount = layerCount;
        this.af = af;
        this.lf = lf;
    }
}
