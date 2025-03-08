package com.perceptron.main;

import com.perceptron.test_train.DataFrame;
import com.perceptron.test_train.ResourceManager;
import com.sun.security.jgss.GSSUtil;

public class Main {
  public static void main(String[] args) {
    boolean b = ResourceManager.getTestData().hasNext();
    if(b) {
      System.out.println("Data");
    } else {
      System.out.println("No");
    }
  }
}
