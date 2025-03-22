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
        Layer o = nn.outputLayer;
        i.setActivations(new double[]{1, 1, 1});
        System.out.println("==ACTIVATIONS==");
        // see if the activations are here
        o.displayActivations();
        i.displayActivations();
        // now set the weights
        i.setWeights(new double[][]{{2, 2}, {1,1}, {1,1}});
        // print them
        System.out.println("==WEIGHTS==");
        i.displayWeights();
        i.displayTransposeWeights();
        System.out.println("==BIAS==");
        o.setBias(new double[]{1,0});
        o.displayBias();
        // test feedforward
        nn.feedforward();
        System.out.println("==NEW ACTIVATIONS==");
        double[] a = output.getActivations();
        output.displayActivations();
        ActivationFunction af = output.getAf();
        assert(a[0] == af.eval(5));
        assert(a[1] == af.eval(4));

    }
}
