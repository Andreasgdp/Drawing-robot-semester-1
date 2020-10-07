import javax.swing.*;

public class window {
    public static void main(String[] args) {
        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawings d = new drawings();
        f.add(d);
        f.setSize(400, 250);
        f.setVisible(true);
    }
}