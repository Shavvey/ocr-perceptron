import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class TestFeedForward {
    @Test
    public void testFeedForward() {
        Layer output = new Layer(10,null, ActivationFunction.SIGMOID, LossFunction.MSE);
        Layer input = new Layer(10, output, ActivationFunction.SIGMOID, LossFunction.MAE);
        Vector<Layer> l = new Vector<>();
        l.add(input);
        l.add(output);
        NeuralNetwork n = new NeuralNetwork(l);
        // add some predictable weights so we can test feed forward, then use an assert to check

    }
}
