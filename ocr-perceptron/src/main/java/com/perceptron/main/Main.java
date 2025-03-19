package com.perceptron.main;


import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;

import java.util.Vector;

public class Main {
  public static void main(String[] args) {
    Layer output = new Layer(10,null, ActivationFunction.GAUSSIAN, LossFunction.MSE);
    Layer hidden = new Layer(30, output, ActivationFunction.GAUSSIAN, LossFunction.MAE);
    Layer input = new Layer(28*28, hidden, ActivationFunction.GAUSSIAN, LossFunction.MAE);
    Vector<Layer> l = new Vector<>();
    l.add(input);
    l.add(hidden);
    l.add(output);
  }
}
