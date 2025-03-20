package com.perceptron.nn;

/**
 * Function used to describe information loss on the layer/neural level.
 * Different functions cans be used for different layouts of neural networks.
 * @author: Cole Johnson
 */
public enum LossFunction {
    LOSS_LOG("LOSS_LOG"), // log loss error
    MSE("MSE"), // mean square error
    MAE("MAE"); // mean absolute error
    final private String name;

    /**
     * Constructor for each LossFunction implemented
     * @param name name of the LossFunction
     */
    LossFunction(String name) { this.name = name; }

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
    public double eval(double[] p, double[] y) {
        switch (this) {

            case LOSS_LOG ->  {
                double sum = 0;
                double sample;

                for (int i = 0; i < p.length; i++) {
                    if(y[i] == 1) {
                        sample = 1-Math.log10(p[i]);
                    } else {
                        sample = 1-Math.log10(1-p[i]);
                    }
                    sum += sample;
                }

                sum /= y.length;
                sum = 1-sum;

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

            case MSE -> {
                double sum = 0;
                double sample;

                for (int i = 0; i < p.length; i++) {
                    sample = y[i] - p[i];
                    sample = Math.pow(sample, 2);
                    sum += sample;
                }

                sum /= y.length;
                sum /= 2;
                return sum;
            }

            default ->
                throw new RuntimeException("[WARNING]: Unimplemented eval!");
        }
    }
}

