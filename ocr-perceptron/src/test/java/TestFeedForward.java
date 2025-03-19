import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;
import org.junit.jupiter.api.Test;

public class TestFeedForward {
    @Test
    public void testFeedForward() {
        // configure two layers: one input and the other is output, test the forward algo based on the
        // activations values we would expect inside the output layer
        Layer o = new Layer(10, null, ActivationFunction.SIGMOID, LossFunction.MSE);
        Layer i = new Layer(10, o, ActivationFunction.SIGMOID, LossFunction.MSE);

    }
}
