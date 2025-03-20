import com.perceptron.nn.ActivationFunction;
import com.perceptron.nn.Layer;
import com.perceptron.nn.LossFunction;
import com.perceptron.test_train.DataFrame;
import org.junit.jupiter.api.Test;

public class TestLossFunction {
    @Test
    public void testLossFunction() {
        // create a new dataframe with a given label
        DataFrame df = new DataFrame();
        df.setLabel("1");
        Layer output = new Layer(10, null, ActivationFunction.SIGMOID, LossFunction.MSE);
        double[] ground_truth = df.getTrueValues();

    }
}
