import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

public class TestMakeNN {
    @Test
    public void testMakeNN() {
        Layer output = new Layer(10, ActivationFunction.SIGMOID);
        Layer hidden = new Layer(30, ActivationFunction.SIGMOID);
        Layer input = new Layer(28*28, ActivationFunction.SIGMOID);
        NeuralNetwork n = new NeuralNetwork(CostFunction.MSE, input, hidden, output);
        n.display();
    }
}
