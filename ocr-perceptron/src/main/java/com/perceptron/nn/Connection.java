package com.perceptron.nn;

class Connection {
    Neuron in;
    Neuron out;
    double weight=0;
    double deltaSum=0;
    Connection(Neuron in, Neuron out, double weight){
        this.in = in;
        this.out=out;
        this.weight = weight;
    }
    void learn(double step){
        this.weight -= step * this.deltaSum;
        this.deltaSum=0;
    }
    void addDelta(double delta){
        this.deltaSum += delta * this.in.getValue();
    }
}