package com.perceptron.main;

import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;

import java.util.Iterator;

public class Main {
  public static void main(String[] args) {
      Iterator<DataFrame> it = ResourceManager.getTrainingData();
      for(int i = 0; i < 1; i++) {
          if(it.hasNext()) {
              DataFrame d = it.next();
              d.display();
          }

      }
  }
}
