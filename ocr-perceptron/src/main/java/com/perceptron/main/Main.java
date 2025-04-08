package com.perceptron.main;


import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;

import java.util.Iterator;

public class Main {
  public static void main(String[] args) {
    Layer output = new Layer(10, ActivationFunction.SIGMOID);
    Layer hidden = new Layer(30, ActivationFunction.SIGMOID);
    Layer input = new Layer(28*28, ActivationFunction.SIGMOID);
    // configure the network and create it
    NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, hidden, output);
    nn.display();
    int length = 0;
    ResourceManager rm = new ResourceManager();
    Iterator<DataFrame> it =  rm.getTestData();
    while (it.hasNext()) {
      DataFrame df = it.next();
      length++;
      System.out.println("Successfully read DataFrame: " + length);
    }
    System.out.println("Training data length:" + length);
  }
}
