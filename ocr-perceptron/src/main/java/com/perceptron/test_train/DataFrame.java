package com.perceptron.test_train;

public class DataFrame {
   final static int FRAME_LENGTH = 28;
   private final int[][] data; // 2D array of values the dataset will provide
   private final String label; // a labeled classification of that dataset (should be some number 0-9)
   DataFrame(String label, int[][] data) {
      this.label = label;
      this.data = data;
   }
   // string representation of dataframe content
   public String toString() {
      StringBuilder s = new StringBuilder();
      for (int dy = 0; dy < FRAME_LENGTH; dy++) {
         s.append("{"); // open brackets
         for (int dx = 0; dx < FRAME_LENGTH; dx++) {
            s.append(String.format("%d", data[dx][dy]));
            if (dx < FRAME_LENGTH - 1) {
               s.append(",");
            }
         }
         s.append("}\n"); // close brackets
      }
      return s.toString();
   }

   public void display() {
      System.out.printf("Label: %s", this.label);
      System.out.println("==DataFrame Content==");
      System.out.println(this);

   }

   public int[][] getData() {
      return data;
   }

   public String getLabel() {
      return label;
   }
}
