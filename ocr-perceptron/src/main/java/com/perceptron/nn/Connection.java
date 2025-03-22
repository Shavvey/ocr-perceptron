package com.perceptron.nn;

class Connection {
    final Neuron input;
    final Neuron output;
    double weight;
    double deltaSum;
    Connection(Neuron input, Neuron output, double weight) {
        this.input = input;
        this.output = output;
        this.weight = weight;
    }
    void learn(double step){
        this.weight -= step * this.deltaSum;
        this.deltaSum = 0;
    }
    void addDelta(double delta){
        this.deltaSum += delta * this.input.getActivation();
    }
}