import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


public class TestDataFrameIterator {
    @Test
    public void testIterator() {
        int parsed = 0;
        ResourceManager rm = new ResourceManager();
        Iterator<DataFrame> it = rm.getTrainingData();
        for(int i = 0; i < 5; i++) {
            if(it.hasNext()) {
                DataFrame d = it.next();
                d.display();
            }
            parsed++;
        }
        System.out.println("Total parsed DataFrames: " + parsed);
    }
}
