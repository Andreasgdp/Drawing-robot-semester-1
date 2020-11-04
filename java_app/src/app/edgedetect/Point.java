package app.edgedetect;

public class Point {

    public int x;
    public int y;
    public int drawVal;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int drawVal) {
        this.x = x;
        this.y = y;
        this.drawVal = drawVal;
    }

    public void setDrawVal(int drawVal) {
        this.drawVal = drawVal;
    }
}
