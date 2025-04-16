package com.perceptron.util;

public final class Transpose {
    /**
     * method to transpose some matrix m of lengths (i,j)
     *
     * @param m original matrix
     * @return new transposed matrix of lengths (j,i)
     */
    public static double[][] t(double[][] m) {
        int i = m.length;
        int j = m[0].length;

        double[][] tm = new double[j][i];
        for (int x = 0; x < j; x++) {
            for (int y = 0; y < i; y++) {
                tm[x][y] = m[y][x];
            }
        }
        return tm;
    }
}
