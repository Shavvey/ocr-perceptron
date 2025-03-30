package com.perceptron.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Network extends JPanel{
    /**
     * Create Network image that shows a visual representation of the neural network
     */
    public int radius = 20;
    public int start_x = 20;
    public int start_y = 20;
    public int vertical_space = 30;
    private BufferedImage drawingImage; // The image that will store the drawing
    private Graphics2D graphic; // Graphics2D object to draw on the BufferedImage
    public Node[] nodes;
 
    // Constructor for building the area. Passes width and height attributes of the image and number of nodes in each column
    public Network(int pageWidth, int pageHeight, int numnodes1, int numnodes2, int numnodes3) {
        // create the area for the network visual
        drawingImage = new BufferedImage(pageWidth, pageHeight, BufferedImage.TYPE_INT_RGB);
        graphic = drawingImage.createGraphics();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, pageWidth, pageHeight);

        // start adding nodes in the area
        int x = start_x;
        int y = start_y;
        int starting_id = 1000;

        // first column
        for(int i=1; i<=numnodes1; i++){
            addNode(x, y, Color.RED, starting_id);
            starting_id++;
            y+=vertical_space;
        }

        // second column
        y = start_y;
        x = pageWidth/2;
        for(int j=1; j<=numnodes2; j++){
            addNode(x, y, Color.BLUE, starting_id);
            starting_id++;
            y+=vertical_space;
        }

        // third column
        y = start_y;
        x = pageWidth-start_x-radius;
        for(int j=1; j<=numnodes2; j++){
            addNode(x, y, Color.GREEN, starting_id);
            starting_id++;
            y+=vertical_space;
        }

        // draw the connecting arrows of each node
        drawArrows();
    }
    
    // function for adding elements to the list of nodes
    public Node[] addElement(Node[] nodeArray, Node newNode) {
        if(nodeArray == null){
            nodeArray = new Node[0];
        }
        // Create a new array with one extra slot
        Node[] newArray = new Node[nodeArray.length + 1];
        
        // Copy existing elements to the new array
        for (int i = 0; i < nodeArray.length; i++) {
            newArray[i] = nodeArray[i];
        }
        
        // Add the new node to the end of the new array
        newArray[nodeArray.length] = newNode;
        
        // Return the new array with the added node
        return newArray;
    }
    
    // creating node objects and adding them to the list and visual area
    private void addNode(int x, int y, Color color, int id) {
        int[] w = {1001,1003}; // weights used as placeholders
        Node node = new Node(w, id, x, y);
        nodes = addElement(nodes, node); // add the element
        graphic.setColor(color); // Set the drawing color
        graphic.fillOval(x, y, radius, radius); // Draw a circle at given coordinates
        repaint();
    }

    // function for iterating through each node and drawing the correct arrow
    private void drawArrows(){
        for(int n=0; n<nodes.length; n++){
            Node from = nodes[n];
            for(int i=0; i<from.weights.length; i++){
                for(int j=0; j<nodes.length; j++){
                    if(nodes[j].id == from.weights[i]){
                        Node to = nodes[j];
                        graphic.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                        graphic.setColor(Color.BLACK);
                        graphic.drawLine(from.x_pos+radius/2, from.y_pos+radius/2, to.x_pos, to.y_pos);
                    }
                }
            }
        }
    }
    
    // add graphics to the image
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawImage(drawingImage, 0, 0, null);
    }

    // the following is used for testing only the network visual
    // private Container createAndShowGUI(Container frame) {
    //     //Create and set up the window.
    //     // JFrame frame = new JFrame("Optical Character Recognition");
    //     // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //     // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //     // frame.setMinimumSize(new Dimension(500,500));
    //     // frame.setVisible(true);

    //     JPanel newContentPane = new Network(frame.getWidth()/2, frame.getHeight()/2);
    //     // SpringLayout layout = new SpringLayout();
    //     BoxLayout layout = new BoxLayout(newContentPane, BoxLayout.Y_AXIS);
    //     newContentPane.setLayout(layout);
    //     newContentPane.setOpaque(true); //content panes must be opaque
    //     newContentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
    //     // frame.setContentPane(newContentPane);
    //     return newContentPane;
    // }

    // public static void main(String[] args) {
    //     //Schedule a job for the event-dispatching thread:
    //     //creating and showing this application's GUI.
    //     javax.swing.SwingUtilities.invokeLater(new Runnable() {
    //         public void run() {
    //             createAndShowGUI();
    //         }
    //     });
    // }
}