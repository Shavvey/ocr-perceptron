package com.perceptron.test_train;

public class DataFrame {
   private final short[][] data; // 2D array of values the dataset will provide
   private final String label; // a labeled classification of that dataset (should be some number 0-9)
   DataFrame(String label, short[][] data) {
      this.label = label;
      this.data = data;
   }

   public short[][] getData() {
      return data;
   }
   public String getLabel() {
      return label;
   }
}
