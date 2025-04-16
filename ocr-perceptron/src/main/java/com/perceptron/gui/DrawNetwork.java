package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;

import com.perceptron.nn.Neuron;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class DrawNetwork extends JPanel {
    private static int panelWidth;
    private static int panelHeight;
    public static int radius = 20;

    public DrawNetwork(int width, int height) { // should pass NeuralNetwork n as an argument
        // NeuralNetwork n = new NeuralNetwork(null, null); // JUST FOR TESTING
        setBackground(Color.WHITE);
        panelWidth = width;
        panelHeight = height;
    }

    private void drawStuff(Graphics g) {
        int numLayers = 3; // to come from the Neural Network Object (for testing)
        // int numLayers = n.numLayers;

        // ArrayList<Layer> layers = n.layers;

        // for loop iterates through Layer object for each Neuron and iterates through its weights
        for (int i = 0; i < numLayers; i++) {
            // Layer l = layers.get(i); // not sure if it should be input or output layer?
            // ArrayList<Neuron> neurons = l.getNeurons();
            // int numNeurons = neurons.size();
            int numNeurons = 10; // for testing

            // for large numbers of neurons, recalculate the radius
            if (numNeurons > 30) {
                radius = panelHeight / numNeurons;
            }

            double xOffset = ((double) i / (numLayers - 1)) * panelWidth; // calculate where each layer should be drawn
            if (i == numLayers - 1) {
                xOffset = panelWidth - radius;
            }

            int x = (int) xOffset;
            int red = (int) (Math.random() * 256); // 0 to 255
            int green = (int) (Math.random() * 256); // 0 to 255
            int blue = (int) (Math.random() * 256); // 0 to 255

            Color layorColor = new Color(red, green, blue);

            // iterate through each of the layers Neuron objects
            for (int j = 0; j < numNeurons; j++) {
                double yOffset = ((double) j / (numNeurons - 1)) * panelHeight; // calculate where the node should be drawn down the layer
                if (j == numNeurons - 1) {
                    yOffset = panelHeight - radius;
                }

                int y = (int) yOffset;

                // Neuron neuron = neurons.get(i);

                // double[] weights = neuron.getWeights();
                double[] weights = {1, 0.4, 1, 1, 1, .2, 1, 1, 1, 1}; // for testing

                int toX = (int) (((double) (i + 1) / (numLayers - 1)) * panelWidth); // calculate where the arrow should be drawn

                //in order for the line to look correct for the last layer, recalculate toX
                if (i == numLayers - 2) {
                    toX -= radius;
                }

                int toYOffset = (int) (((double) 1 / (numNeurons - 1)) * panelHeight); //calculate spacing between each node

                int toY = 0; // start at y=0

                // iterates through the Neurons weights array
                for (int w = 0; w < weights.length; w++) {
                    // in order for the line to look correct for the last node in each layer, recalculate toY
                    if (w == weights.length - 1) {
                        toY -= radius;
                    }
                    // only show significant connections (may get rid of later)
                    if (weights[w] >= 0.5 && i != numLayers - 1) {
                        drawArrows(x + radius / 2, y + radius / 2, toX + radius / 2, toY + radius / 2, g);
                    }
                    toY += toYOffset;
                }
                addNode(x, y, layorColor, g); // draw the nodes
            }
        }
    }

    // adding neurons to visual area
    private void addNode(int x, int y, Color color, Graphics graphic) {
        graphic.setColor(color); // Set the drawing color
        graphic.fillOval(x, y, radius, radius); // Draw a circle at given coordinates
    }

    // drawing lines between connected nodes
    private void drawArrows(int fromX, int fromY, int toX, int toY, Graphics graphic) {
        graphic.setColor(Color.BLACK);
        graphic.drawLine(fromX, fromY, toX, toY);
    }

    @Override
    public Dimension getPreferredSize() {
        // maybe refactor this out as a constant later
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStuff(g);
    }
}