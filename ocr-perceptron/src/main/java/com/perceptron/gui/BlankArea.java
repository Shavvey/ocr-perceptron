package com.perceptron.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;

public class BlankArea extends JPanel{
    /**
     * Create BlankArea that keeps track of mouse movement and actions
     */
    
    private int width = 400;
    private int height =400;
    private BufferedImage drawingImage; // The image that will store the drawing
    private Graphics2D graphic; // Graphics2D object to draw on the BufferedImage
    public Color background;
 
    // Constructor for building the area. Passes color for background
    public BlankArea(Color color) {
        background=color;
        
        drawingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphic = drawingImage.createGraphics();
        graphic.setColor(background); // Set the background
        graphic.fillRect(0, 0, width, height); // Fill the background

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
        graphic.setColor(Color.BLACK); // Set the drawing color
        graphic.fillRect(x, y, 5, 5); // Draw a small rectangle at the current mouse position
        repaint();
    }
 
    // preferred size of blank area
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }
    
    // add graphics where the user has their mouse
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawImage(drawingImage, 0, 0, null);
    }

    // Save the current drawing to an image file
    public void saveToImage() {
        // Write the image to a file
        try {
            ImageIO.write(drawingImage, "PNG", new File("drawing.png"));
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clear the drawing by resetting the image to a blank state
    public void clearDrawing() {
        // Fill the image with the background (clear the drawing)
        graphic.setColor(background);
        graphic.fillRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
        repaint(); // Repaint the panel to show the cleared image
    }
}