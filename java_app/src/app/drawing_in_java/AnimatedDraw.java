package app.drawing_in_java;

import app.edgedetect.Point;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class AnimatedDraw extends JPanel {
    private ArrayList<ArrayList<Point>> points;

    public AnimatedDraw(ArrayList<ArrayList<Point>> points) {
        this.points = points;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        for (ArrayList<Point> group: this.points) {
            for (int i = 0; i < group.size(); i++) {
                if (i == 0) {
                    group.get(i).setDrawColor(0);
                } else {
                    group.get(i).setDrawColor(1);
                }
                Color color = (group.get(i).color == 0) ? Color.BLACK : Color.WHITE;
                g.setColor(color);
                g.fillRect(group.get(i).x, group.get(i).y, 1, 1);
            }
        }
    }
}
