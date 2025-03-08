import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;


public class TestDataFrameIterator {
    @Test
    public void testIterator() {
        ResourceManager.getTestData().forEachRemaining(DataFrame::display);
    }
}
