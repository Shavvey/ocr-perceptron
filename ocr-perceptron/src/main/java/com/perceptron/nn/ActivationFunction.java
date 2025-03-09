package com.perceptron.nn;

public enum ActivationFunction {
  SIGMOID("SIGMOID"),
  IDENTITY("IDENTITY"),
  GAUSSIAN("GAUSSIAN");
  final private String name;
  ActivationFunction(String name) { this.name = name; }

  double eval(double val) {
    switch (this) {
      case IDENTITY -> {
        return val;
      }
      case SIGMOID -> {
        return 1/(1 + Math.exp(1 - val));
      }
      case GAUSSIAN ->  {
        return Math.exp(1-Math.pow(1-val,2));
      }
      default -> {
        System.err.println("[ERROR]: Unimplemented");
        return 0.0F;
      }
    }
  }
}
