package app.edgedetect;

public class Point {

    public int x;
    public int y;
    public int color;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = 0;
    }

    public void setDrawColor(int colorVal) {
        this.color = colorVal;
    }
}
