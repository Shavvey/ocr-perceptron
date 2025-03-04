package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
 
public class BlankArea extends JPanel implements MouseListener {
    /**
     * Create BlankArea that keeps track of mouse movement and actions
     */

    Dimension maxSize = new Dimension(300, 300);
    JTextArea textArea;
    static final String NEWLINE = System.getProperty("line.separator");
 
    // Constructor for building the area. Passes color for background
    public BlankArea(Color color) {
        super(new GridLayout(0,1));
        JComponent blankArea = new JLabel();
        add(blankArea);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        add(scrollPane);
         
        //Register for mouse events on blankArea
        blankArea.addMouseListener(this);
        blankArea.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 10, 10, 3, true));
        setBackground(color);
        addMouseListener(this);
    }
 
    public void setSize(int width, int height) {
        maxSize = new Dimension(width,height);
    }
 
    public Dimension getPreferredSize() {
        return maxSize;
    }

    // Print user inputs from mouse events in the text area
    void eventOutput(String eventDescription, MouseEvent e) {
        textArea.append(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "." + NEWLINE);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
     
    public void mousePressed(MouseEvent e) {
        eventOutput("Mouse pressed (# of clicks: "
                + e.getClickCount() + ")", e);
    }
     
    public void mouseReleased(MouseEvent e) {
        eventOutput("Mouse released (# of clicks: "
                + e.getClickCount() + ")", e);
    }
     
    public void mouseEntered(MouseEvent e) {
        eventOutput("Mouse entered", e);
    }
     
    public void mouseExited(MouseEvent e) {
        eventOutput("Mouse exited", e);
    }
     
    public void mouseClicked(MouseEvent e) {
        eventOutput("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);
    }
}