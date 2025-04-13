package com.perceptron.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

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

        JPanel prediction = new JPanel();
        JLabel text = new JLabel("<html><h3>The prediction is:</h3></html>");
        prediction.add(text);
        

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
                network.add(prediction);
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
                drawing.add(prediction);
                setContentPane(drawing);
                pack();
                drawing.requestFocus();
                repaint();
            }
        });

        drawing.add(drawPanel);
        drawing.add(buttonPanel);
        drawing.add(prediction);
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
