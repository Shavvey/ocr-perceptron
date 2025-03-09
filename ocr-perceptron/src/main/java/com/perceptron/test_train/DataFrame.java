package com.perceptron.test_train;

/**
 * Class that encapsulates training and test data for the ocr-perceptron
 * @author Cole Johnson
 */
public class DataFrame {
   final static int FRAME_LENGTH = 28;
   final static int MAX_BRIGHTNESS = 1 << 8;
   private int[][] data; // 2D array of values the dataset will provide
   private String label; // a labeled classification of that dataset (should be some number 0-9)


   /**
    * DataFrame constructor that passes args for each field in the class.
    * @param label label of the frame, should some character that the values represent
    * @param data teh actual 2D array of data that shows each drawn character
    */
   DataFrame(String label, int[][] data) {
      this.label = label;
      this.data = data;
   }

   /**
    * Constructor no arg constructor for `DataFrame` class.
    * Just initializes the data array and the label, and leaves them empty.
    */
   DataFrame() {
      this.label = "";
      this.data = new int[FRAME_LENGTH][FRAME_LENGTH];
   }

   /**
    * Provide string representation of dataframe.
    * @return a string representing the entire array
    * of values inside the frame
    */
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

   /**
    * Uses toString {@link #toString()} method to print out
    * label and data values of the array inside the DataFrame.
    */
   public void display() {
      System.out.println("==DataFrame Content==");
      System.out.printf("Label: %s\n", this.label);
      System.out.println(this);

   }

   /**
    * Returns the data array
    * @return current array of data values
    */
   public int[][] getData() {
      return data;
   }

   /**
    * Set the label of the frame.
    * @return set label of frame
    */
   public String getLabel() {
      return label;
   }

   /**
    * Assigns new array of data to the frame.
    * @param data new data to put in inside of frame
    */
   public void setData(int[][] data) {
      this.data = data;
   }

   /**
    * Return the label of the frame
    * @param label
    */
   public void setLabel(String label) {
      this.label = label;
   }

   /**
    * Wrapper method to index array and set a new value
    * @param x x-index
    * @param y y-index
    * @param value new value to be set at (x, y) index of array
    */
   public void setValue(int x, int y, int value) {
      data[x][y] = value;
   }

   /**
    * Convert DataFrame values to greyscale (using double precision)
    * @return get greyscale values as an array of 2D values
    */
   public double[][] grayscale() {
      double[][] greyscale_data = new double[DataFrame.FRAME_LENGTH][DataFrame.FRAME_LENGTH];
      for (int dy = 0; dy < FRAME_LENGTH; dy++) {
         for (int dx = 0; dx < FRAME_LENGTH; dx++) {
            greyscale_data[dy][dx] = this.data[dy][dy]/MAX_BRIGHTNESS;
         }
      }
      return greyscale_data;
   }

}
