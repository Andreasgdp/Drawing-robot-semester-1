package drawing_in_java;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class drawings extends JPanel {
    Color[][] colorArray;

    public drawings(Color[][] colorArray) {
        this.colorArray = colorArray;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        for (int y = 0; y < colorArray.length; y++) {
            for (int x = 0; x < colorArray.length; x++) {
                if (colorArray[y][x].getRed() == 0 && colorArray[y][x].getGreen() == 0
                        && colorArray[y][x].getBlue() == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(y, x, 1, 1);
                } else {
                    // Don't draw.
                }
            }
        }
    }
}
