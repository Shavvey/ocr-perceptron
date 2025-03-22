import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

public class TestFeedForward {
    @Test
    public void testFeedForward() {
        Layer output = new Layer(2, ActivationFunction.SIGMOID);
        Layer input = new Layer(3, ActivationFunction.SIGMOID);
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, output);
        Layer i = nn.inputLayer;
        i.setActivations(new double[]{1,1,1});
        // see if the activations are here
        i.displayActivations();
        // now set the weights
    }
}
