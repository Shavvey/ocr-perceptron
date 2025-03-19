package com.perceptron.nn;

/**
 * ActivationFunction is useful at the layer level to calculate
 * the activations of next layer. The function employed "squishes"
 * the real number line to values at [-1,1].
 * @author: Cole Johnson
 */
public enum ActivationFunction {
  SIGMOID("SIGMOID"),
  IDENTITY("IDENTITY"),
  GAUSSIAN("GAUSSIAN");
  final private String name;
  ActivationFunction(String name) { this.name = name; }

  /**
   * Getter for name
   * @return name of ActivationFunction
   */
  public String getName() {
    return this.name;
  }

  /**
   * Evaluates a given sum (often the weighted during feedforward)
   * and returns value given ActivationFunction.
   * @param val old-value
   * @return new value under Activation Function
   */
  public double eval(double val) {
    switch (this) {
      case IDENTITY -> {
        return val;
      }
      case SIGMOID -> {
        return 1 / (1 + Math.exp(-val));
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
