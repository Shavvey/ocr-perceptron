package com.perceptron.main;

import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // create different layers, with different layer count and ActivationFunction
        Layer output = new Layer(10, ActivationFunction.SIGMOID);
        Layer hidden1 = new Layer(30, ActivationFunction.SIGMOID);
        Layer input = new Layer(28 * 28, ActivationFunction.SIGMOID);
        // configure the network and create it
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, hidden1, output);
        nn.display();
        nn.train(0.45, 10, 10);
        nn.test();
        Scanner scan = new Scanner(System.in);
        if (scan.nextLine().equals("yes")) {
            nn.serialize("model-1");
        }

    }
}
