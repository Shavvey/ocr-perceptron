package com.perceptron.nn;

import java.io.Serializable;

/**
 * ActivationFunction is useful at the layer level to calculate
 * the activations of next layer. The function employed "squishes"
 * the real number line to values at [-1,1].
 *
 * @author: Cole Johnson
 */
public enum ActivationFunction implements Serializable {
    SIGMOID("SIGMOID"),
    IDENTITY("IDENTITY"),
    GAUSSIAN("GAUSSIAN"),
    SOFTMAX("SOFTMAX"),
    RELU("ReLU");
    final private String name;

    /**
     * constructs each ActivationFunction
     *
     * @param name name of ActivationFunction implemented
     */
    ActivationFunction(String name) {
        this.name = name;
    }

    /**
     * Getter for name
     *
     * @return name of ActivationFunction
     */
    public String getName() {
        return this.name;
    }

    /**
     * Evaluates a given sum (often the weighted during feedforward)
     * and returns value given ActivationFunction.
     *
     * @param val old-value
     * @return new value under Activation Function
     */
    public double eval(double val) {
        switch (this) {

            // probably won't use this one
            case IDENTITY -> {
                return val;
            }

            case SIGMOID -> {
                return 1 / (1 + Math.exp(-val));
            }

            case GAUSSIAN -> {
                return Math.exp(Math.pow(-val, 2));
            }

            case RELU -> {
                return (val > 0) ? val : 0;
            }

            default -> throw new RuntimeException("[ERROR]: Unimplemented ActivationFunction");
        }
    }

    /**
     * Evaluates the derivative o the ActivationFunction.
     *
     * @param val number evaluated under derivative of ActivationFunction
     * @return eval under derivative of ActivationFunction
     */
    public double derivativeEval(double val) {
        switch (this) {

            case SIGMOID -> {
                // clever derivative of sigmoid
                return eval(val) * (1 - eval(val));
            }

            case IDENTITY -> {
                return 1;
            }

            case GAUSSIAN -> {
                return (-2 * val) * Math.exp(Math.pow(-val, 2));
            }

            case RELU -> {
                return (val > 0) ? 1 : 0;
            }

            default -> throw new RuntimeException("[ERROR]: Unimplemented ActivationFunction");
        }
    }
}
