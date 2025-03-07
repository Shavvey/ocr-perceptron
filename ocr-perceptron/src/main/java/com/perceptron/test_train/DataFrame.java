package com.perceptron.test_train;

public class DataFrame {
   private final int[][] data; // 2D array of values the dataset will provide
   private final char label; // a labeled classification of that dataset (should be some number 0-9)
   DataFrame(char label, int[][] data) {
      this.label = label;
      this.data = data;
   }

   public int[][] getData() {
      return data;
   }
   public char getLabel() {
      return label;
   }
}
