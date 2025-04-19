package com.perceptron.gui;

import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;

import javax.swing.*;

import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static javax.swing.BoxLayout.*;

public class GUI extends JFrame {
    NeuralNetwork nn;
    private static final Font DEFAULT_FONT = new Font("Monaco", Font.PLAIN, 14);
    public GUI() {
        super("OCR-Perceptron");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: implement a way to resize window dynamically
        setResizable(false);
        DrawPanel drawPanel = new DrawPanel(700, 700);
        DrawNetwork drawNet = new DrawNetwork(700, 700);
        JButton classifyButton = new JButton("Classify");
        JButton clearButton = new JButton("Clear");

        JButton viewNet = new JButton("View Neural Network");
        JButton viewDrawing = new JButton("Back to Drawing");

        // create new drop-down for serialized models
        JComboBox<String> modelList = new JComboBox<>(getModelOptions());


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(classifyButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(viewNet);

        JPanel drawing = new JPanel();
        JPanel network = new JPanel();

        JPanel prediction = new JPanel();
        JLabel text = new JLabel("<html><h3>The prediction is:</h3></html>");
        prediction.add(text);


        BoxLayout drawLayout = new BoxLayout(drawing, BoxLayout.Y_AXIS);
        BoxLayout netLayout = new BoxLayout(network, BoxLayout.Y_AXIS);
        drawing.setLayout(drawLayout);
        network.setLayout(netLayout);

        clearButton.addActionListener(_ -> {
            drawPanel.clear();
            repaint();
        });

        classifyButton.addActionListener(_ ->{
            if (nn != null) {
                DataFrame df = drawPanel.exportDataFrame();
                int p = nn.makePrediction(df);
                String predictedLabel = Integer.toString(p);
                text.setText("<html><h3>The prediction is: " + predictedLabel + "</h3></html>");

            }
        });

        modelList.addActionListener(_ -> {
            String modelName = (String) modelList.getSelectedItem();
            if (modelName != null && !modelName.equals("null")) {
                modelName = modelName.substring(0, modelName.lastIndexOf("."));
                nn = NeuralNetwork.deserialize(modelName);
                drawNet.setModel(nn);
                nn.display();
            }

        });

        // adding buttons to switch view
        viewNet.addActionListener(_ -> {
            buttonPanel.remove(viewNet);
            buttonPanel.remove(clearButton);
            buttonPanel.remove(classifyButton);
            buttonPanel.add(viewDrawing);
            network.add(drawNet);
            network.add(buttonPanel);
            network.add(prediction);
            setContentPane(network);
            pack();
            network.requestFocus();
            repaint();
        });

        viewDrawing.addActionListener(_ -> {
            buttonPanel.add(clearButton);
            buttonPanel.add(classifyButton);
            buttonPanel.remove(viewDrawing);
            buttonPanel.add(viewNet);
            drawing.add(buttonPanel);
            drawing.add(prediction);
            setContentPane(drawing);
            pack();
            drawing.requestFocus();
            repaint();
        });
        drawPanel.setBorder(new BasicBorders.MarginBorder());
        drawing.add(drawPanel);
        drawing.add(buttonPanel);
        drawing.add(prediction);
        drawing.add(modelList, X_AXIS);
        setContentPane(drawing);
        pack();
        drawing.requestFocus();
    }

    public String[] getModelOptions() {
        ArrayList<String> files = new ArrayList<>();
        // default option (no model is currently loaded)
        files.add("null");
        Path dir = Path.of("src/main/resources/models");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                files.add(file.getFileName().toString());
            }
        } catch (IOException | DirectoryIteratorException ex) {
            // TODO: make a better exception handler
            throw new RuntimeException(ex);
        }
        String[] choices = new String[files.size()];
        files.toArray(choices);
        return choices;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GUI();
            frame.setVisible(true);
        });

    }
}
