import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


public class TestDataFrameIterator {
    @Test
    public void testIterator() {
        Iterator<DataFrame> it = ResourceManager.getTrainingData();
        for(int i = 0; i < 20; i++) {
            if(it.hasNext()) {
                DataFrame d = it.next();
                d.display();
            }

        }
    }
}
