package com.perceptron.gui;

public class Node {
    public int[] weights;
    public int id;
    public int x_pos, y_pos;

    public Node(int[] w, int i, int x, int y){
        this.weights=w;
        this.id=i;
        this.x_pos=x;
        this.y_pos=y;
    }
}
