import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestSerialization {
    @Test
    public void testSerialization() {
        // input layer can hold input as activations
        Layer input = new Layer(28*28, ActivationFunction.SIGMOID);
        // new cool hidden layer
        Layer hidden = new Layer(30, ActivationFunction.SIGMOID);
        // output holds predictions as activations
        Layer output = new Layer(10, ActivationFunction.SIGMOID);
        // create new nn based on config
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, hidden, output);
        // instantiate resource manager to hand us a dataframes (wasteful, but hey it's just a test)
        DataFrame df = ResourceManager.getTrainingData().next();
        // now make very wrong prediction based on initial nn config
        double[] p = nn.prediction(df);
        System.out.println(Arrays.toString(p));
        // serialize nn, then make prediction again,
        // if serialization interface works it should be the same
        nn.serialize("example-model");
        NeuralNetwork nnClone = NeuralNetwork.deserialize("example-model");
        double[] p2 = nnClone.prediction(df);
        System.out.println(Arrays.toString(p2));
        // assert that clone and original nn should make the same prediction
        for (int i = 0; i < p.length; i++) {
            assert(p[i] == p2[i]);
        }
    }
}
