import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class TestFeedForward {
    @Test
    public void testFeedForward() {
        Layer output = new Layer(2,null, ActivationFunction.SIGMOID, LossFunction.MSE);
        Layer input = new Layer(3, output, ActivationFunction.SIGMOID, LossFunction.MAE);
        input.setActivations(new double[]{1, 1, 1});
        input.setWeights(new double[][]{ {1, 1}, {1, 1}, {1, 1} });
        output.setBias(new double[]{1,1});
        Vector<Layer> l = new Vector<>();
        l.add(input);
        l.add(output);
        NeuralNetwork n = new NeuralNetwork(l);
        // add some predictable weights so we can test feed forward, then use an assert to check
        n.feedforward();
        // print out activations
        n.outputLayer.displayActivations();
        double[] a = n.outputLayer.getActivations();
        ActivationFunction af = input.getAf();
        for (double v : a) {
            assert (v == af.eval(4));
        }


    }
}
