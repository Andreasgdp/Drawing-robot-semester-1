package app.drawing_in_java;

import app.edgedetect.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnimatedDraw extends JPanel {
    public AnimatedDraw(ArrayList<Point> points, int width, int height) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            JFrame frame = new JFrame("Testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TestPane(points, width, height));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static class TestPane extends JPanel {

        ArrayList<Point> points;
        int width;
        int height;
        int index = 0;


        public TestPane(ArrayList<Point> points, int width, int height) {
            this.points = points;
            this.width = width;
            this.height = height;
            Timer timer = new Timer(1, e -> {
                if (index < points.size()) {
                    index++;
                }
                repaint();
            });
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(this.width, this.height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            for (int i = 0; i < index; i++) {
                int val = 256 / 6;
                g2d.setColor(new Color(points.get(i).drawVal * val, points.get(i).drawVal * val, points.get(i).drawVal * val));
                if (points.get(i+1).drawVal == 1 && i < index - 1) {
                    g2d.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
                } else {
                    g2d.fillRect(points.get(i).x, points.get(i).y, 1, 1);
                }
            }
            g2d.dispose();
        }

    }

}




