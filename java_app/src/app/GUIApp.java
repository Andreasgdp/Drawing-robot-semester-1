package app;

import app.drawing_in_java.*;
import app.edgedetect.EdgeDetector;
import app.edgedetect.Point;
import app.robclient.RobotClient;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIApp extends JFrame{
    private JButton runButton;
    private JTextField fileName;
    private JButton updateButton;
    private JPanel panelMain;

    // The path of the image has to start w. "../images/" as it is the relative path from the file app/edgedetect/Picture.java.
    private final String imgPath = "../images/";
    private final String fileNameStr = "black.jpg";
    private final String imagePath = imgPath + fileNameStr;
    private EdgeDetector eDetect = new EdgeDetector(imagePath);
    private RobotClient client = new RobotClient("192.168.0.20", 12345);

    GUIApp() {
        super("My fancy semester project");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        try {
            client.connect();
        } catch (Exception e) {
            System.out.println("Cannot connect to PLC. ERR: " + e);
        }

        eDetect = new EdgeDetector(imagePath);
        client = new RobotClient("192.168.0.20", 12345);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eDetect.loadNewImage(fileName.getText());
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Point> cords = eDetect.getSortedCords();
                showImageAnimated(eDetect, cords, false);
            }
        });
    }

    public static void main(String[] args) {
        GUIApp GUI = new GUIApp();
        GUI.setVisible(true);

    }

    private static void showImageAnimated(EdgeDetector eDetect, ArrayList<Point> cords, boolean test) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();
        if (test) {
            new AnimatedDrawTest(cords, width, height);
        } else {
            new AnimatedDraw(cords, width, height);
        }
    }
}
