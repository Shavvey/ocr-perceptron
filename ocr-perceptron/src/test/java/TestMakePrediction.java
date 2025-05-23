import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.CostFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestMakePrediction {
    @Test
    public void testMakePrediction() {
        ResourceManager rm = new ResourceManager();
        // configure network to take dataframe
        CostFunction cf = CostFunction.MSE;
        // input layer can hold input as activations
        Layer input = new Layer(28*28, ActivationFunction.SIGMOID);
        // new cool hidden layer
        Layer hidden = new Layer(30, ActivationFunction.SIGMOID);
        // output holds predictions as activations
        Layer output = new Layer(10, ActivationFunction.SIGMOID);
        // create new nn based on config
        NeuralNetwork nn = new NeuralNetwork(CostFunction.MSE, input, hidden, output);
        // instantiate resource manager to hand us a dataframes (wasteful, but hey it's just a test)
        DataFrame df = rm.getTrainingData().next();
        // now make very wrong prediction based on initial nn config
        double[] p = nn.getPredictionVector(df);
        System.out.println("==PREDICTION==");
        System.out.println(Arrays.toString(p));
        System.out.println("==COST==");
        double cost = nn.cf.cost(p, df.getTrueValues());
        System.out.println(cost);

    }
}
