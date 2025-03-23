import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import org.junit.jupiter.api.Test;

public class TestIOLayer {
    @Test
    public void testIOLayer() {
        Layer output = new Layer(2, ActivationFunction.SIGMOID);
        Layer input = new Layer(3, ActivationFunction.SIGMOID);
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, output);
        Layer i = nn.inputLayer;
        Layer o = nn.outputLayer;
        assert(i.isInput());
        assert(o.isOutput());
    }
}
