package com.perceptron.nn;

public enum ActivationFunction {
  SIGMOID("SIGMOID"),
  IDENTITY("IDENTITY"),
  GAUSSIAN("GAUSSIAN");
  final private String name;
  ActivationFunction(String name) { this.name = name; }
}
