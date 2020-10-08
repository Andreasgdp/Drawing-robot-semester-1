import robclient.RobotClient;
import edgedetect.EdgeDetector;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class App {
	public static void main(String[] args) {
		RobotClient client = new RobotClient("10.0.0.50", 12345);
		client.connect();
		if (client.isConnected())
			client.write("this is a message");

		EdgeDetector eDetector = new EdgeDetector("app/images/black.jpg");
		Color[][] array = eDetector.getGreyscaleArray();
		int white = 0;
		int black = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j].getRGB() == -1)
					white++;
				else
					black++;
			}
		}
		System.out.println("white: " + white);
		System.out.println("black: " + black);
	}
}
