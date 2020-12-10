package app.drawing_in_java;

import app.edgedetect.Point;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DrawArraylistArrayListPoint extends JPanel  {
    ArrayList<ArrayList<Point>> arrayList;

    public DrawArraylistArrayListPoint(ArrayList<ArrayList<Point>> arrayList) {
        this.arrayList = arrayList;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        for (int i = 0; i < this.arrayList.size() - 1; i++) {
            ArrayList<Point> pair = this.arrayList.get(i);
            int x1 = pair.get(0).x;
            int y1 = pair.get(0).y;
            int x2 = pair.get(1).x;
            int y2 = pair.get(1).y;
            //System.out.println((pair.get(0).get(2)*51));
            //System.out.println(x1 + "," + y1 + " : " + x2 + "," + y2);
            int val = 256 / 6;
            Color color = new Color((pair.get(0).drawVal * val), (pair.get(0).drawVal*val), (pair.get(0).drawVal*val));
            g.setColor(color);
            // g.fillRect(x1, y1, java.lang.Math.abs(x2 - x1), 1);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
