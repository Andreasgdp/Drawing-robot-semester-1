import robclient.RobotClient;
import edgedetect.EdgeDetector;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.*;

public class App {
	public static void main(String[] args) {
		// TODO: use default "img.jpg"
		EdgeDetector eDetect = new EdgeDetector("DEFAULT_IMG_PATH");
		// TODO: get correct hostname and port
		RobotClient client = new RobotClient("hostname", 5000);
		try {
			client.connect();
		} catch (Exception e) {
			System.out.println("Cannot connect to PLC. ERR: " + e);
		}

		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("Write command: ");
				String msg = scanner.next();
				if (msg.startsWith("q") || msg.startsWith("quit")) {
					break;
				} else if (msg.startsWith("reset")) {
					client.write("reset");
				} else if (msg.startsWith("stop")) {// TODO: add function to recognize ESC-btn
					client.write("stop");
				} else if (msg.startsWith("image") || msg.startsWith("loadImage")) {
					String imgPath = scanner.next();
					eDetect.loadNewImage(imgPath);
				} else if (msg.startsWith("coordinates") || msg.startsWith("loadCoordinates")) {
					eDetect.loadCoordinates();
				} else if (msg.startsWith("send")) {
					client.write(eDetect.getCoordinates());
				} else if (msg.startsWith("run")) {
					String imgPath = scanner.next();
					eDetect.loadNewImage(imgPath);
					eDetect.loadCoordinates();
					client.write(eDetect.getCoordinates());
				} else {
					System.out.print("\nCommand '" + msg + "' is not a valid command!\n");
				}
			} catch (Exception e) {
				System.out.println("CMD exited w. this err: " + e);
			}
		}
		scanner.close();
		client.disconnect();
	}
}
