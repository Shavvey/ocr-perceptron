import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class TestCostFunction {
    @Test
    public void testLossFunction() {
        // create a new dataframe with a given label
        DataFrame df = ResourceManager.getTrainingData().next();
        Layer output = new Layer(10, null, ActivationFunction.SIGMOID);
        Layer input = new Layer(28*28, output, ActivationFunction.SIGMOID);
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, output);
        double[] ground_truth = df.getTrueValues();
        double[] prediction = nn.prediction(df);
        double loss = nn.cf.eval(prediction, ground_truth);
        System.out.println(Arrays.toString(prediction));
        System.out.println(loss);

    }
}
