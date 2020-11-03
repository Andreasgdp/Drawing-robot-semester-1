package app.drawing_in_java;

import app.edgedetect.EdgeDetector;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class drawings extends JPanel {
    ArrayList<ArrayList<ArrayList<Integer>>> arrayList;

    public drawings(ArrayList<ArrayList<ArrayList<Integer>>> arrayList) {
        this.arrayList = arrayList;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        for (int i = 0; i < this.arrayList.size() - 1; i++) {
            ArrayList<ArrayList<Integer>> pair = this.arrayList.get(i);
            int x1 = pair.get(0).get(1);
            int y1 = pair.get(0).get(0);
            int x2 = pair.get(1).get(1);
            int y2 = pair.get(1).get(0);
            //System.out.println((pair.get(0).get(2)*51));
            //System.out.println(x1 + "," + y1 + " : " + x2 + "," + y2);
            Color color = new Color((pair.get(0).get(2)*51), (pair.get(0).get(2)*51), (pair.get(0).get(2)*51));
            g.setColor(color);
            // g.fillRect(x1, y1, java.lang.Math.abs(x2 - x1), 1);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
