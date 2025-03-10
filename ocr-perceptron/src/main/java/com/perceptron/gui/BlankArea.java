package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class BlankArea extends JPanel{
    /**
     * Create BlankArea that keeps track of mouse movement and actions
     */

    private int squareX;
    private int squareY;
 
    // Constructor for building the area. Passes color for background
    public BlankArea(Color color) {
        setBorder(BorderFactory.createDashedBorder(Color.BLACK, 10, 10, 3, true));
        setBackground(color);

        // add mouse listeners to draw
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });
    }
    
    private void moveSquare(int x, int y) {
        // do not redraw an area that is already drawn
        if ((squareX!=x) || (squareY!=y)) {
            squareX=x;
            squareY=y;
            repaint(squareX,squareY,5,5);
        } 
    }
 
    // preferred size of blank area
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
    
    // add graphics where the user has their mouse
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        g.setColor(Color.BLACK);
        g.fillRect(squareX,squareY,5,5);
        // g.setColor(Color.BLACK);
        g.drawRect(squareX,squareY,5,5);
    }
}