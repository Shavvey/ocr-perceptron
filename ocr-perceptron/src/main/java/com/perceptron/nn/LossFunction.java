package com.perceptron.nn;

public enum LossFunction {
    LOSS_LOG("LOSS_LOG"),
    MSE("MSE"),
    MSA("MSA");
    final private String name;
    LossFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // TODO: IMPLEMENT eval function, these are just placeholder values
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

