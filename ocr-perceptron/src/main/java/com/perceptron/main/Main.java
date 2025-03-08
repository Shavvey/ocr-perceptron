package com.perceptron.main;

import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;

import java.util.Iterator;

public class Main {
  public static void main(String[] args) {
      for (Iterator<DataFrame> it = ResourceManager.getTestData(); it.hasNext(); ) {
          DataFrame d = it.next();
      }
  }
}
