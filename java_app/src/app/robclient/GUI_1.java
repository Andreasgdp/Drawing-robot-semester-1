package app.robclient;

import app.App;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GUI_1 extends JFrame {

    private JPanel rootPanel;
    private JButton runButton;
    private JButton Greyscale;
    private JButton showGreyButton;
    private JButton showButton;
    private JButton showEdgeButton;
    private JFormattedTextField formattedTextField1;
    private JTextArea textArea1;
    static String input = "";

    public GUI_1() {
        // Dette bruger rootdesigneren //
        add(rootPanel);

        setTitle("Printing Robot");
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button.Actions //
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel, "Printing", "Printing Robot", -1);

            }
        });

        formattedTextField1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(rootPanel, "Showing", "Printing Robot", -1);
            }
        });
        formattedTextField1.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                input = input + e;
            }
        });
        showEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel, "Showing Edge", "Printing Robot", -1);
            }
        });
        showGreyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel, "Showing Grey", "Printing Robot", -1);
            }
        });
    }


    // Temporary main //

    public static void main(String[] args) {
        GUI_1 GUI = new GUI_1();
        GUI.setVisible(true);



    }

}
