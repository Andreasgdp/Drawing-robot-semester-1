package app;

import app.edgedetect.EdgeDetector;
import app.edgedetect.Point;
import app.robclient.RobotClient;

import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class GUIApp extends JFrame {
    // The path of the image has to start w. "../images/" as it is the relative path from the file app/edgedetect/Picture.java.
    private final String imgPath = "../images/";
    private final String fileNameStr = "black.jpg";
    private String imagePath = imgPath + fileNameStr;
    private JButton simulateButton;
    private JPanel panelMain;
    private JComboBox runBox;
    private JComboBox simBox;
    private JButton runButton;
    private JComboBox imageBox;
    private EdgeDetector eDetect;
    private final RobotClient client;

    GUIApp() {
        super("My fancy semester project");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        eDetect = new EdgeDetector(imagePath);
        client = new RobotClient("192.168.0.20", 12345);


        // try {
        //     client.connect();
        // } catch (Exception e) {
        //     System.out.println("Cannot connect to PLC. ERR: " + e);
        // }

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sim = (String) simBox.getSelectedItem();
                int identifier = Integer.parseInt(String.valueOf(Objects.requireNonNull(sim).charAt(0)));

                if (identifier == 1) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getCoordinates();
                    App.showImage(eDetect, cords);
                } else if (identifier == 2) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    App.showImage(eDetect, cords);
                } else if (identifier == 3) {
                    ArrayList<ArrayList<Point>> cords = eDetect.getGreyLineCoordinates();
                    App.showGereyLineImage(eDetect, cords);
                } else if (identifier == 4) {
                    ArrayList<ArrayList<Point>> cords = eDetect.getGreyLineCoordinates();
                    App.animateImageAnimated(eDetect, cords);
                } else if (identifier == 5) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    App.showImageAnimated(eDetect, cords, false);
                } else if (identifier == 6) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    App.showImageAnimated(eDetect, cords, true);
                } else if (identifier == 7) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords1 = eDetect.getCoordinates();
                    App.showImage(eDetect, cords1);
                    ArrayList<ArrayList<Point>> cords2 = eDetect.getGreyLineCoordinates();
                    App.showGereyLineImage(eDetect, cords2);
                    App.animateImageAnimated(eDetect, cords2);
                    ArrayList<ArrayList<ArrayList<Integer>>> cords3 = eDetect.getEdgeCords();
                    App.showImage(eDetect, cords3);
                    ArrayList<Point> cords4 = eDetect.getSortedCords();
                    App.showImageAnimated(eDetect, cords4, false);
                    App.showImageAnimated(eDetect, cords4, true);
                }


            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String run = (String) runBox.getSelectedItem();

                int identifier = Integer.parseInt(String.valueOf(Objects.requireNonNull(run).charAt(0)));

                if (identifier == 1) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getCoordinates();
                    App.runTest(client, cords);
                } else if (identifier == 2) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    App.runTest(client, cords);
                } else if (identifier == 3) {
                    ArrayList<ArrayList<Point>> cords = eDetect.getGreyLineCoordinates();
                    App.runGreyLineTest(client, cords);
                } else if (identifier == 4) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    App.runSortTest(client, cords);
                }
            }
        });
        imageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = (String) imageBox.getSelectedItem();
                imagePath = imgPath + fileName;
                eDetect.loadNewImage(imagePath);
            }
        });
    }

    public static void main(String[] args) {
        GUIApp GUI = new GUIApp();
        GUI.setVisible(true);
        GUI.setSize(700, 400);
        GUI.setLocationRelativeTo(null);

    }
}
