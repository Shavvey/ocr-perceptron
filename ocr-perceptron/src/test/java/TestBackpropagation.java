import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

public class TestBackpropagation {
    @Test
    public void testBackpropagation() {
        // input layer can hold input as activations
        Layer input = new Layer(28*28, ActivationFunction.SIGMOID);
        // new cool hidden layer
        Layer hidden = new Layer(30, ActivationFunction.SIGMOID);
        // output holds predictions as activations
        Layer output = new Layer(10, ActivationFunction.SIGMOID);
        // create new nn based on config
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, hidden, output);
        // instantiate resource manager to hand us dataframes (wasteful, but hey it's just a test)
        DataFrame df = ResourceManager.getTrainingData().next();
        double[] p = nn.prediction(df);
        double[] t = df.getTrueValues();
        nn.feedback(t, p);
        nn.learn(0.15);
    }
}
