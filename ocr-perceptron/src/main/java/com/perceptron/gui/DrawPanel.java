package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPanel extends JPanel {
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

    public void clear() {
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
