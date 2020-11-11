package app.drawing_in_java;

import app.edgedetect.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnimatedDrawGreyline extends JPanel {
    public AnimatedDrawGreyline(ArrayList<ArrayList<Point>> points, int width, int height) {
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

        ArrayList<ArrayList<Point>> points;
        int width;
        int height;
        int index = 0;


        public TestPane(ArrayList<ArrayList<Point>> points, int width, int height) {
            this.points = points;
            this.width = width;
            this.height = height;
            Timer timer = new Timer(5, e -> {
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
                ArrayList<Point> pair = this.points.get(i);
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
            g2d.dispose();
        }

    }

}




