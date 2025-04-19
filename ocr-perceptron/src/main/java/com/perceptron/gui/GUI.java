package com.perceptron.gui;

import com.perceptron.nn.NeuralNetwork;
import com.perceptron.test_train.DataFrame;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static javax.swing.BoxLayout.X_AXIS;

public class GUI extends JFrame {
    private final static Dimension DEFAULT_GUI_DIM = new Dimension(700,700);
    NeuralNetwork nn;
    final JPanel drawingPanel;
    final JPanel  networkPanel;
    private static final Font DEFAULT_FONT = new Font("Monaco", Font.PLAIN, 14);
    // create new drop-down for serialized models
    final private JComboBox<String> modelList = new JComboBox<>(getModelOptions());

    public GUI() {
        super("OCR-Perceptron");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: implement a way to resize window dynamically
        setResizable(false);
        // set up are two panels, drawing and view panel
        drawingPanel = makeDrawPanel(DEFAULT_GUI_DIM.width, DEFAULT_GUI_DIM.height);
        networkPanel = makeNetworkPanel(DEFAULT_GUI_DIM.width, DEFAULT_GUI_DIM.height);
        // set the drawing panel as the display/content one initially
        setContentPane(drawingPanel);
        pack();
    }

    private JPanel makeDrawPanel(int width, int height) {;
        DrawPanel drawPanel = new DrawPanel(width, height);
        JPanel parent = new JPanel();

        // configure the layout
        BoxLayout drawLayout = new BoxLayout(parent, BoxLayout.Y_AXIS);
        parent.setLayout(drawLayout);
        // add prediction box
        JPanel prediction = new JPanel();
        JLabel text = new JLabel("<html><h3>The prediction is:</h3></html>");
        prediction.add(text);

        //make the buttons necessary
        JButton classifyButton = new JButton("Classify");
        JButton clearButton = new JButton("Clear");
        JButton viewNet = new JButton("View Neural Network");

        // configure button action listeners
        viewNet.addActionListener(_ -> {
            // switch content to network panel quickly
            this.setContentPane(networkPanel);
            pack();
        });

        classifyButton.addActionListener(_ ->{
            if (nn != null) {
                DataFrame df = drawPanel.exportDataFrame();
                int p = nn.makePrediction(df);
                String predictedLabel = Integer.toString(p);
                text.setText("<html><h3>The prediction is: " + predictedLabel + "</h3></html>");

            }
        });

        clearButton.addActionListener(_ -> {
            drawPanel.clear();
            repaint();
        });

        // configure the buttons for drawing panel
        JPanel drawButtons = new JPanel();
        drawButtons.add(classifyButton);
        drawButtons.add(clearButton);
        drawButtons.add(viewNet);

        // add all JPanel elements
        parent.add(drawPanel);
        parent.add(prediction);
        parent.add(modelList, X_AXIS);
        parent.add(drawButtons);

        // return back configured draw panel
        return parent;
    }

    private JPanel makeNetworkPanel(int width, int height) {

        JPanel parent = new JPanel();
        NetworkPanel networkPanel = new NetworkPanel(width, height);

        // configure action listener for model listing and serializer
        modelList.addActionListener(_ -> {
            String modelName = (String) modelList.getSelectedItem();
            if (modelName != null && !modelName.equals("null")) {
                modelName = modelName.substring(0, modelName.lastIndexOf("."));
                nn = NeuralNetwork.deserialize(modelName);
                networkPanel.setModel(nn);
            }
        });
        // configure the layout
        BoxLayout networkLayout = new BoxLayout(parent, BoxLayout.Y_AXIS);
        parent.setLayout(networkLayout);

        // make the buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Drawing");
        buttonPanel.add(backButton);

        //configure action listeners
        backButton.addActionListener(_ -> {
            setContentPane(drawingPanel);
            pack();
        });

        // add JPanel elements
        parent.add(networkPanel);
        parent.add(buttonPanel);
        return parent;

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
