package com.perceptron.nn;

/**
 * Function used to describe information loss on the layer/neural level.
 * Different functions cans be used for different layouts of neural networks.
 * @author: Cole Johnson
 */
public enum LossFunction {
    LOSS_LOG("LOSS_LOG"),
    MSE("MSE"),
    MSA("MSA");
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

    // TODO: IMPLEMENT eval function, these are just placeholder values

    /**
     * Evaluation based on the implemented LossFunction.
     * @param value value to be put into the LossFunction
     * @return new value based on LossFunction implementation
     */
    public double eval(double value) {
        switch (this) {
            case LOSS_LOG ->  {
                return 0.2F;
            }
            case MSA ->  {
                return 0.1F;
            }

            case MSE -> {
                return 0.3F;
            }
            default -> {
                System.out.println("[WARNING]: Unimplemented eval!");
                return 0.00F;
            }
        }
    }
}

