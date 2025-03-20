package com.perceptron.main;


import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;
import com.perceptron.nn.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Main {
  public static void main(String[] args) {
    Layer output = new Layer(10,null, ActivationFunction.SIGMOID, LossFunction.MSE);
    Layer hidden = new Layer(30,output, ActivationFunction.GAUSSIAN, LossFunction.MAE);
    Layer input = new Layer(28*28, hidden, ActivationFunction.SIGMOID, LossFunction.MAE);
    NeuralNetwork n = new NeuralNetwork(new ArrayList<>(Arrays.asList(input, hidden, output)));
    n.display();
  }
}
