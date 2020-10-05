import robclient.RobotClient;
import edgedetect.EdgeDetector;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class App {
	public static void main(String[] args) {
		EdgeDetector eDetector = new EdgeDetector("app/images/black.jpg");
		BufferedImage test = eDetector.getBufferedImage();
		System.out.println(test);

	}
}
