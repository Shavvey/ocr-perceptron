package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;

public class GUI{
    /**
     * Create the GUI and show it.
     * Includes BlankArea that tracks mouse movement and actions
     * Has a header and submit button
     */

    public static void addComponents(Container pane){
        JPanel header = new JPanel();
        JLabel title = new JLabel("<html><h1 width=100%>Optical Character Recognition</h1></html>");
        // header.setFont(header.getFont().deriveFont(64.0F));

        header.setMaximumSize(new Dimension(10000, 75));

        Color primary_color = new Color(135, 167, 222);
        Color background = new Color(255, 232, 184);

        header.setBackground(primary_color);
        header.add(title);
        
        pane.add(header);

        BlankArea blankArea = new BlankArea(background);
        blankArea.setMaximumSize(new Dimension(500,500));
        pane.add(blankArea);

        // JTextArea info = new JTextArea("Please draw a letter in the box below");
        // pane.add(info);

        JButton submit = new JButton("Submit");
        submit.setBackground(primary_color);
        submit.setMinimumSize(new Dimension(100,50));
        submit.setFont(submit.getFont().deriveFont(14.0F));
        pane.add(submit);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Optical Character Recognition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel newContentPane = new JPanel();
        // SpringLayout layout = new SpringLayout();
        BoxLayout layout = new BoxLayout(newContentPane, BoxLayout.Y_AXIS);
        newContentPane.setLayout(layout);
        newContentPane.setOpaque(true); //content panes must be opaque
        
        frame.setContentPane(newContentPane);

        addComponents(frame.getContentPane());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}