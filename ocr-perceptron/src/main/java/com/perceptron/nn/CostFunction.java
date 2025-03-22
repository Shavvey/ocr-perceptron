package com.perceptron.nn;

import java.io.Serializable;

/**
 * Function used to describe information loss on the layer/neural level.
 * Different functions cans be used for different layouts of neural networks.
 * @author: Cole Johnson
 */
public enum CostFunction implements Serializable {
    // mean-square error
    MSE("MSE"),
    // mean absolute error
    MAE("MAE");
    final private String name;

    /**
     * Constructor for each LossFunction implemented
     * @param name name of the LossFunction
     */
    CostFunction(String name) { this.name = name; }

    /**
     * Return back name of LossFunction used.
     * @return name of LossFunction
     */
    public String getName() {
        return name;
    }


    /**
     * Evaluate loss under LossFunction
     * @param p prediction values
     * @param y ground "truth" values (comes from DataFrame label)
     * @return "Loss" at a given {@link Layer}
     */
    public double cost(double[] p, double[] y) {
        switch (this) {

            case MSE -> {
                double sum = 0;
                double sample;

                for (int i = 0; i < p.length; i++) {
                    sample = y[i] - p[i];
                    sample = Math.pow(sample, 2);
                    sum += sample;
                }

                sum /= 2 * y.length;
                return sum;
            }

            case MAE ->  {
                double sum = 0;
                double sample;

                for (int i = 0; i < p.length; i++) {
                    sample = y[i] - p[i];
                    sum += sample;
                }

                sum /= y.length;
                return sum;
            }

            default ->
                throw new RuntimeException("[WARNING]: Unimplemented eval!");
        }
    }
}

