package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    /**
     * Create the GUI and show it.
     * Includes BlankArea that tracks mouse movement and actions
     * Has a header, clear button, and submit button
     */

    // adds components to main panel of Frame
    public static void addComponents(JFrame frame) {
        Container pane = frame.getContentPane();
        // colors used throughout
        Color primary_color = new Color(135, 167, 222);
        Color background = new Color(255, 232, 184);

        JPanel header = new JPanel();
        JLabel title = new JLabel("<html><h1>Optical Character Recognition</h1></html>");

        header.setMaximumSize(new Dimension(10000, 100));

        header.setBackground(primary_color);
        header.add(title);

        JPanel info = new JPanel();
        JLabel text = new JLabel("<html><h3>Please draw a letter in the following box</h3></html>");
        info.setMaximumSize(new Dimension(900, 75));
        info.add(text);
        // text.setAlignmentX(Component.CENTER_ALIGNMENT);

        // cancvas for user to draw in
        JPanel area = new JPanel();
        area.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 10, 10, 3, true));
        area.setBackground(background);
        BlankArea blankArea = new BlankArea(background);
        area.setMaximumSize(new Dimension(420,420));
        // blankArea.setMaximumSize(new Dimension(500,300));

        area.add(blankArea);

        // includes buttons
        JPanel footer = new JPanel();

        JButton submit = new JButton("Submit");
        submit.setBackground(Color.green);
        submit.setMinimumSize(new Dimension(100,50));
        submit.setFont(submit.getFont().deriveFont(14.0F));

        // Add ActionListener to the button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked
                // JOptionPane.showMessageDialog(pane, "Ready to submit!");
                blankArea.saveToImage();
            }
        });

        // erase prvious drawings in the panel
        JButton clear = new JButton("Clear");
        clear.setBackground(Color.red);
        clear.setMinimumSize(new Dimension(100,50));
        clear.setFont(submit.getFont().deriveFont(14.0F));

        // Add ActionListener to the button
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clears all drawings in the blankarea
                blankArea.clearDrawing();
            }
        });

        footer.add(clear);
        pane.add(Box.createRigidArea(new Dimension(10,0)));
        footer.add(submit);

        JButton go_to_network = new JButton("View Neural Network");

        JButton back_to_home = new JButton("Back to Drawing");

        // this button will take you to the neural network visual
        go_to_network.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.removeAll(); // reset screen
                pane.add(header); // add heading
                pane.add(new Network(500, 500, 10,10,10), BorderLayout.CENTER); // add visual
                JPanel buttons = new JPanel();
                buttons.add(back_to_home);
                pane.add(buttons); // add the back button
                frame.revalidate();
                frame.repaint();
            }
        });

        // button will take you back to the drawing interface
        back_to_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.removeAll(); // reset frame

                // add all original elements
                pane.add(header);
                pane.add(info);
                pane.add(Box.createRigidArea(new Dimension(0,25)));
                pane.add(area);
                pane.add(Box.createRigidArea(new Dimension(0,25)));
                pane.add(footer);
                pane.add(Box.createRigidArea(new Dimension(0,25)));
                frame.revalidate();
                frame.repaint();
            }
        });


        footer.add(go_to_network);
        
        // set up layout
        pane.add(header);
        pane.add(info);
        pane.add(Box.createRigidArea(new Dimension(0,25)));
        pane.add(area);
        pane.add(Box.createRigidArea(new Dimension(0,25)));
        pane.add(footer);
        pane.add(Box.createRigidArea(new Dimension(0,25)));
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
        newContentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        frame.setContentPane(newContentPane);

        addComponents(frame);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}