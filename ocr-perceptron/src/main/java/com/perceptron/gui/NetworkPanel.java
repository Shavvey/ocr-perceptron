package com.perceptron.gui;

import com.perceptron.nn.Layer;
import com.perceptron.nn.NeuralNetwork;

import javax.swing.*;
import java.awt.*;

/**
 * Content panel responsible for
 * showing the current {@link NeuralNetwork}, via it's connected
 * weights in each layer.
 *
 * @author: Lauren Giles and Cole Johnson
 */
public class NetworkPanel extends JPanel {
    private static int panelWidth;
    private static int panelHeight;
    public int currentSegment = 0;
    private NeuralNetwork nn;
    public final static int INPUT_LAYERS_SEGMENTS = 28;
    public final static int NEURONS_PER_SEGMENT = 28;
    private final static Color NEURON_COLOR = Color.BLUE;
    private final static Color WEIGHT_COLOR = Color.BLACK;

    /**
     * Simple constructor for {@link NetworkPanel}.
     *
     * @param width  width of panel
     * @param height height of panel
     */
    public NetworkPanel(int width, int height) {
        setBackground(Color.WHITE);
        panelWidth = width;
        panelHeight = height;
    }

    /**
     * Update the model being used by {@link NetworkPanel}.
     * Called by {@link GUI} during model selection by user
     *
     * @param nn {@link NeuralNetwork} now being used by {@link GUI}
     */
    public void setModel(NeuralNetwork nn) {
        this.nn = nn;
        repaint();
    }

    /**
     * Main method to draw the {@link NeuralNetwork}.
     *
     * @param g {@link Graphics} context
     */
    private void drawNetwork(Graphics g) {
        drawInputSegment(g);
        // draw weights
        for (int j = 1; j < nn.numLayers; j++) {
            drawWeights(g, j);
        }
        for (int i = 1; i < nn.numLayers; i++) {
            Layer l = nn.getLayer(i);
            int offset = getOffset(i);
            drawLayer(g, l, offset);
        }
    }

    /**
     * Get the x-offset of a neuron given its layer index, used for drawing methods.
     *
     * @param layerIndex position in network, first layer to last output layer [0, n-1]
     * @return x-offset
     */
    private int getOffset(int layerIndex) {
        // compute offset depending on what layer we are on
        double widthFraction = (double) layerIndex / (nn.numLayers - 1);
        return (int) (panelWidth * widthFraction);
    }

    /**
     * Draw input segment of the network.
     *
     * @param g {@link Graphics} context
     */
    private void drawInputSegment(Graphics g) {
        g.setColor(NEURON_COLOR);
        int width = panelWidth / NEURONS_PER_SEGMENT;
        int height = panelHeight / NEURONS_PER_SEGMENT;
        for (int i = 0; i < NEURONS_PER_SEGMENT; i++) {
            g.fillOval(0, height * i, width, height);
        }
    }

    /**
     * Draw each respective layer of the network
     *
     * @param g      {@link Graphics} context
     * @param l      {@link Layer} current layer
     * @param offset x-offset used to draw the layer
     */
    private void drawLayer(Graphics g, Layer l, int offset) {
        g.setColor(NEURON_COLOR);
        Dimension neuronSize = getNeuronSize(l);
        int numNeurons = l.getNumNeurons();
        for (int i = 0; i < numNeurons; i++) {
            g.fillOval(offset - neuronSize.width, neuronSize.height * i, neuronSize.width, neuronSize.height);
        }
    }

    /**
     * Draw each neuron's weights.
     *
     * @param g   {@link Graphics} context
     * @param idx layer index
     */
    private void drawWeights(Graphics g, int idx) {
        Layer currentLayer = nn.getLayer(idx);
        Layer prevLayer = nn.getLayer(idx - 1);
        // get y dimensions
        Dimension currNeuronSize = getNeuronSize(currentLayer);
        Dimension prevNeuronSize = getNeuronSize(prevLayer);
        // get x dimensions
        int currentOffset = getOffset(idx);
        int prevOffset = getOffset(idx - 1);
        // set colors and begin drawing
        g.setColor(WEIGHT_COLOR);
        double[][] w = getLayersWeights(idx);
        int xEnd;
        if (idx == 1) {
            // if first hidden layer, don't shift by previous offset
            xEnd = prevNeuronSize.width;
        } else {
            xEnd = prevOffset;
        }
        // iterate across number of neurons
        for (int j = 0; j < w.length; j++) {
            // iterate across number of incoming connections
            // TODO: use weight vals change color or something
            for (int i = 0; i < w[0].length; i++) {
                g.drawLine(currentOffset - currNeuronSize.width, currNeuronSize.height * j + currNeuronSize.height / 2,
                        xEnd, prevNeuronSize.height * i + prevNeuronSize.height / 2);
            }
        }
    }

    /**
     * Get the neurons dimensions in terms of screen width and height
     *
     * @param l layer the neuron occupies
     * @return {@link Dimension} of neuron
     */
    private Dimension getNeuronSize(Layer l) {
        if (l.isInput()) {
            int width = panelWidth / NEURONS_PER_SEGMENT;
            int height = panelHeight / NEURONS_PER_SEGMENT;
            return new Dimension(width, height);
        } else {
            int numNeurons = l.getNumNeurons();
            int width = panelWidth / numNeurons;
            int height = panelHeight / numNeurons;
            return new Dimension(width, height);
        }
    }

    /**
     * Get a neurons weights, depending on layer index.
     *
     * @param idx layer index
     * @return weights stored in 2D array
     */
    private double[][] getLayersWeights(int idx) {
        Layer current = nn.getLayer(idx);
        Layer prev = nn.getLayer(idx - 1);
        if (prev.isInput()) {
            int numNeurons = current.getNumNeurons();
            double[][] w = new double[numNeurons][NEURONS_PER_SEGMENT];
            // get actual weights extracted from current input segment
            double[][] aw = current.getWeights();
            for (int j = 0; j < numNeurons; ++j) {
                for (int i = 0; i < NEURONS_PER_SEGMENT; ++i) {
                    int offset = currentSegment * NEURONS_PER_SEGMENT;
                    w[j][i] = aw[j][i + offset];
                }
            }
            return w;
        } else {
            return current.getWeights();
        }
    }

    /**
     * Getter for panel's preferred size.
     *
     * @return preferred {@link Dimension}
     */
    @Override
    public Dimension getPreferredSize() {
        // maybe refactor this out as a constant later
        return new Dimension(panelWidth, panelHeight);
    }

    /**
     * Tells swing how to paint the {@link NetworkPanel}.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nn != null) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawNetwork(g2D);
        }
    }
}