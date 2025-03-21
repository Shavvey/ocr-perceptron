import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

public class TestFeedForward {
    @Test
    public void testFeedForward() {
        Layer output = new Layer(2, null, ActivationFunction.SIGMOID);
        Layer input = new Layer(3, output, ActivationFunction.SIGMOID);
        // configure input activations for test
        input.setActivations(new double[]{1, 1, 1});
        // configure input weights for test
        input.setWeights(new double[][]{ {2, 2}, {1, 1}, {1, 1} });
        // print them out to make sure
        input.displayWeights();
        // set new bias on next Layer--the output layer in this case
        output.setBias(new double[]{0,1});
        // save config into neural network
        NeuralNetwork n = new NeuralNetwork(CostFunction.MSE, input, output);
        n.feedforward();
        // print out activations
        n.outputLayer.displayActivations();
        double[] a = n.outputLayer.getActivations();
        ActivationFunction af = input.getAf();
        assert (a[0] == af.eval(4));
        assert (a[1] == af.eval(5));
    }
}
