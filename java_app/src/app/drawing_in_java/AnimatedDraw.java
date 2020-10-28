package app.drawing_in_java;

import app.edgedetect.Point;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class AnimatedDraw extends JPanel {
    private ArrayList<Point> points;

    public AnimatedDraw(ArrayList<Point> points) {
        this.points = points;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.setColor(Color.BLACK);
        for (Point point : this.points) {
            g.fillRect(point.x, point.y, 1, 1);
        }
    }
}
