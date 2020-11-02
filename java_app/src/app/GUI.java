package app;

import app.edgedetect.EdgeDetector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Printer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,300);
        frame.setVisible(true);

        // MenuBar and Components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        //panel and adding components
        JPanel panel = new JPanel(); // is not visible in outline
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(15); // Accepts up to 10 char
            String imagePath = "download.png";

        JButton send = new JButton("Send");
            send.addActionListener(e -> new EdgeDetector(imagePath));

        JButton reset = new JButton("Reset");
            reset.addActionListener(e -> new EdgeDetector(""));

        panel.add(label); //Components added by Flow layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        //text area
        JTextArea ta = new JTextArea();
        JButton run = new JButton("Run");
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.getContentPane().add(BorderLayout.CENTER, run);
        frame.setVisible(true);

    }
}
