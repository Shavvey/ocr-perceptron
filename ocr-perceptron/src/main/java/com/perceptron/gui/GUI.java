package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.perceptron.nn.Neuron;
import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {

    private static class DrawPanel extends JPanel {
        private static final int DF_DIMENSION = 28;
        final private static float[][] grid = new float[DF_DIMENSION][DF_DIMENSION];
        private static int panelWidth;
        private static int panelHeight;
        private static final Color GRID_COLOR = Color.GRAY;
        private static final Color FILL_COLOR = Color.YELLOW;

        public DrawPanel(int width, int height) {
            setBackground(Color.BLACK);
            panelWidth = width;
            panelHeight = height;
            for (int j = 0; j < DF_DIMENSION; j++) {
                for (int i = 0; i < DF_DIMENSION; i++) {
                    // init grid to zero
                    grid[j][i] = 0.00F;
                }
            }
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = e.getPoint() ;
                    Dimension cellDim = getCellSize();
                    int cellX = p.x / cellDim.width;
                    int cellY = p.y / cellDim.height;
                   // System.out.println("Cell x: " + cellX);
                   // System.out.println("Cell y: " + cellY);
                    grid[cellY][cellX] = 1F;
                    // NOTE: pretty lazy repaint here, we could optimize but probably won't
                    repaint();

                }
            };
            addMouseMotionListener(mouseAdapter);
        }

        public Dimension getCellSize() {
            int height = getHeight();
            int width = getWidth();
            return new Dimension(width / DF_DIMENSION,  height / DF_DIMENSION);
        }

        @Override
        public Dimension getPreferredSize() {
            // maybe refactor this out as a constant later
            return new Dimension(panelWidth,panelHeight);
        }

        private boolean isFilled(int y, int x) {
            return grid[y][x] != 0;
        }

        private void clear() {
            for (int j = 0; j < DF_DIMENSION; j++) {
                for (int i = 0; i < DF_DIMENSION; i++) {
                    // init grid to zero
                    grid[j][i] = 0.00F;
                }
            }
        }

        public void drawGrid(Graphics g) {
            int cellWidth = panelWidth / DF_DIMENSION;
            int cellHeight = panelHeight / DF_DIMENSION;

            for (int j = 0; j < DF_DIMENSION; j++) {
                for (int i = 0; i < DF_DIMENSION; i++) {
                    if (grid[j][i] == 0F) {
                        g.setColor(GRID_COLOR);
                        g.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                    } else {
                        g.setColor(FILL_COLOR);
                        g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // draw grid
            drawGrid(g);

        }
    }

    private static class DrawNetwork extends JPanel{
        private static int panelWidth;
        private static int panelHeight;
        public static int radius = 20;

        public DrawNetwork(int width, int height){ // should pass NeuralNetwork n as an argument
            // NeuralNetwork n = new NeuralNetwork(null, null); // JUST FOR TESTING
            setBackground(Color.WHITE);
            panelWidth = width;
            panelHeight = height;
        }

        private void drawStuff(Graphics g){
            int numLayers = 3; // to come from the Neural Network Object (for testing)
            // int numLayers = n.numLayers;

            // ArrayList<Layer> layers = n.layers;

            // for loop iterates through Layer object for each Neuron and iterates through its weights
            for(int i=0; i<numLayers; i++){
                // Layer l = layers.get(i); // not sure if it should be input or output layer?
                // ArrayList<Neuron> neurons = l.getNeurons();
                // int numNeurons = neurons.size();
                int numNeurons = 10; // for testing
                double xOffset = ((double)i/(numLayers-1))*panelWidth;
                if(i==numLayers-1){
                    xOffset=panelWidth-radius;
                }

                int x=(int)xOffset;

                // iterate through each of the layers Neuron objects
                for(int j=0; j<numNeurons; j++){
                    double yOffset = ((double)j/(numNeurons-1))*panelHeight;
                    if(j==numNeurons-1){
                        yOffset=panelHeight-radius;
                    }

                    int y = (int)yOffset;
                    // Neuron neuron = neurons.get(i);

                    // double[] weights = neuron.getWeights();
                    double[] weights = {1,0.4,1,1,1,.2,1,1,1,1}; // for testing

                    // iterates through the Neurons weights array
                    int toX = (int)(((double)(i+1)/(numLayers-1))*panelWidth);
                    int toYOffset = (int)(((double)1/(numNeurons-1))*panelHeight);

                    int toY = 0;
                    for(int w=0; w<weights.length; w++){
                        // only show significant connections (may get rid of later)
                        System.out.println("w: " + w);
                        if(weights[w]>=0.5 && i!=numLayers-1){
                            drawArrows(x+radius/2, y+radius/2, toX+radius/2, toY+radius/2, g);
                        } 
                        toY += toYOffset;
                    }
                    addNode(x, y, Color.RED, g);
                }
            }
        }

        // adding neurons visual area
        private void addNode(int x, int y, Color color, Graphics graphic) {
            graphic.setColor(color); // Set the drawing color
            graphic.fillOval(x, y, radius, radius); // Draw a circle at given coordinates
        }

        private void drawArrows(int fromX, int fromY, int toX, int toY, Graphics graphic){
            graphic.setColor(Color.BLACK);
            graphic.drawLine(fromX, fromY, toX, toY);
        }

        @Override
        public Dimension getPreferredSize() {
            // maybe refactor this out as a constant later
            return new Dimension(panelWidth,panelHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawStuff(g);
        }
    }

    public GUI() {
        super("OCR-Perceptron");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: implement a way to resize window dynamically
        setResizable(false);
        DrawPanel drawPanel = new DrawPanel(700,700);
        DrawNetwork drawNet = new DrawNetwork(700,700);
        JButton classifyButton = new JButton("Classify");
        JButton clearButton = new JButton("Clear");

        JButton viewNet = new JButton("View Neural Network");
        JButton back_to_drawing = new JButton("Back to Drawing");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(classifyButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(viewNet);

        JPanel drawing = new JPanel();
        JPanel network = new JPanel();

        BoxLayout drawlayout = new BoxLayout(drawing, BoxLayout.Y_AXIS);
        BoxLayout netlayout = new BoxLayout(network, BoxLayout.Y_AXIS);
        drawing.setLayout(drawlayout);
        network.setLayout(netlayout);

        clearButton.addActionListener(e -> {
            drawPanel.clear();
            repaint();
        });

        // adding buttons to switch view
        viewNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.remove(viewNet);
                buttonPanel.remove(clearButton);
                buttonPanel.remove(classifyButton);
                buttonPanel.add(back_to_drawing);
                network.add(drawNet);
                network.add(buttonPanel);
                setContentPane(network);
                pack();
                network.requestFocus();
                repaint();
            }
        });
        back_to_drawing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.add(clearButton);
                buttonPanel.add(classifyButton);
                buttonPanel.remove(back_to_drawing);
                buttonPanel.add(viewNet);
                drawing.add(buttonPanel);
                setContentPane(drawing);
                pack();
                drawing.requestFocus();
                repaint();
            }
        });

        drawing.add(drawPanel);
        drawing.add(buttonPanel);
        setContentPane(drawing);
        pack();
        drawing.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GUI();
            frame.setVisible(true);
        });

    }
}
