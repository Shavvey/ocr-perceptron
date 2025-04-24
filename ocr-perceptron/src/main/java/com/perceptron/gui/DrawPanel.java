package com.perceptron.gui;

import com.perceptron.test_train.DataFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Content panel responsible for
 * allowing the user draw characters and
 * make prediction based on the loaded model.
 *
 * @author: Lauren Giles and Cole Johnson
 */
public class DrawPanel extends JPanel {
    private static final int DF_DIMENSION = 28;
    final private double[][] grid = new double[DF_DIMENSION][DF_DIMENSION];
    private final int panelWidth;
    private final int panelHeight;
    private static final Color GRID_COLOR = Color.GRAY;
    private static final Color FILL_COLOR = Color.YELLOW;

    /**
     * Simple constructor for {@link DrawPanel} of mouse-input and the panel itself.
     *
     * @param width  width of panel
     * @param height height of panel
     */
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
                Point p = e.getPoint();
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

    /**
     * Getter for the cell dimensions in terms of panel size.
     *
     * @return {@link Dimension} of the cells inside the panel
     */
    public Dimension getCellSize() {
        int height = getHeight();
        int width = getWidth();
        return new Dimension(width / DF_DIMENSION, height / DF_DIMENSION);
    }

    /**
     * Getter for preferred size of panel.
     *
     * @return {@link Dimension} of the preferred panel size
     */
    @Override
    public Dimension getPreferredSize() {
        // maybe refactor this out as a constant later
        return new Dimension(panelWidth, panelHeight);
    }

    /**
     * Boolean to check see if cell is filled.
     *
     * @param y y position
     * @param x x position
     * @return boolean to see if grid is a non-zero value
     */
    private boolean isFilled(int y, int x) {
        return grid[y][x] != 0;
    }

    public void clear() {
        for (int j = 0; j < DF_DIMENSION; j++) {
            for (int i = 0; i < DF_DIMENSION; i++) {
                // init grid to zero
                grid[j][i] = 0.00F;
            }
        }
    }

    /**
     * Main draw method. Draws grid onto the {@link DrawPanel}.
     *
     * @param g {@link Graphics} context
     */
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

    /**
     * Create {@link DataFrame} from drawn user-input on the grid.
     *
     * @return a new unlabeled {@link DataFrame} that contains user's drawn input
     */
    public DataFrame exportDataFrame() {
        return new DataFrame(null, grid.clone());
    }

    /**
     * Tells swing how to draw this component.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw grid
        drawGrid(g);

    }
}
