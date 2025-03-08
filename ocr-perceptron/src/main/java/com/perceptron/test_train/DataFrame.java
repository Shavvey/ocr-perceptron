package com.perceptron.test_train;

public class DataFrame {
   final static int FRAME_LENGTH = 28;
   private int[][] data; // 2D array of values the dataset will provide
   private String label; // a labeled classification of that dataset (should be some number 0-9)

   DataFrame(String label, int[][] data) {
      this.label = label;
      this.data = data;
   }

   DataFrame() {
      this.label = "";
      this.data = new int[FRAME_LENGTH][FRAME_LENGTH];
   }

   // string representation of dataframe content
   public String toString() {
      StringBuilder s = new StringBuilder();
      for (int dy = 0; dy < FRAME_LENGTH; dy++) {
         s.append("{"); // open brackets
         for (int dx = 0; dx < FRAME_LENGTH; dx++) {
            String val = String.format("%3d", data[dy][dx]);
            s.append(val);
            if (dx < FRAME_LENGTH - 1) {
               s.append(",");
            }
         }
         s.append("}\n"); // close brackets
      }
      return s.toString();
   }


   public void display() {
      System.out.println("==DataFrame Content==");
      System.out.printf("Label: %s\n", this.label);
      System.out.println(this);

   }

   public int[][] getData() {
      return data;
   }

   public String getLabel() {
      return label;
   }

   public void setData(int[][] data) {
      this.data = data;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public void setValue(int x, int y, int value) {
      data[x][y] = value;
   }

}
