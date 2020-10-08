import robclient.RobotClient;
import edgedetect.EdgeDetector;
import drawing_in_java.drawings;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class App {
	public static void main(String[] args) {
		App app = new App();
		boolean imageLoaded = false;
		boolean coordsLoaded = false;

		EdgeDetector eDetect = new EdgeDetector("app/images/download.png");
		RobotClient client = new RobotClient("10.0.0.50", 12345);
		try {
			client.connect();
		} catch (Exception e) {
			System.out.println("Cannot connect to PLC. ERR: " + e);
		}

		Scanner scanner = new Scanner(System.in);
		Scanner CMDscanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("Write command: ");
				String msg = scanner.next();
				if (msg.startsWith("q") || msg.startsWith("quit")) {
					break;
				} else if (msg.startsWith("test")) {
					Color[][] testColor = eDetect.getColorArray();
					int black = 0;
					int white = 0;
					for (int i = 0; i < testColor.length; i++) {
						for (int j = 0; j < testColor[i].length; j++) {
							if (testColor[i][j].getRed() == 0 && testColor[i][j].getGreen() == 0
									&& testColor[i][j].getBlue() == 0) {
								black++;
							} else {
								white++;
							}
						}
					}
					System.out.println("White: " + white);
					System.out.println("Black: " + black);
				} else if (msg.startsWith("show")) {
					int height = eDetect.getBufferedImage().getHeight();
					int width = eDetect.getBufferedImage().getWidth();
					System.out.println(height + " : " + width);
					JFrame f = new JFrame("Title");
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Color[][] colorArray = eDetect.getColorArray();
					drawings d = new drawings(colorArray);
					f.add(d);
					f.setSize(width, height);
					f.setVisible(true);
				} else if (msg.startsWith("reset")) {
					client.write("reset");
				} else if (msg.startsWith("stop")) {// TODO: add function to recognize ESC-btn
					client.write("stop");
				} else if (msg.startsWith("image") || msg.startsWith("loadImage")) {
					System.out.print("Write new img path: ");
					String imgPath = CMDscanner.next();
					eDetect.loadNewImage(imgPath);
					imageLoaded = true;
				} else if (msg.startsWith("coordinates") || msg.startsWith("loadCoordinates")) {
					if (imageLoaded) {
						eDetect.loadCoordinates();
						coordsLoaded = true;
					} else {
						System.out.println("Image is not loaded! Use command 'image' to load image");
					}
				} else if (msg.startsWith("send")) {
					if (coordsLoaded) {
						client.write(eDetect.getCoordinates());
					} else {
						System.out.println("Coordinates is not loaded! Use command 'coordinates' to load coordinates");
					}
				} else if (msg.startsWith("message")) {
					System.out.print("Write the message for the plc: ");
					String message = CMDscanner.nextLine();
					client.write(message);
					String inputClient = client.read();
					String waitVariable = inputClient;
					if (waitVariable == null) {
						waitVariable = "null";
					}
					while (waitVariable.compareTo(message) != 0) {
						waitVariable = client.read();
						if (waitVariable == null) {
							waitVariable = "null";
						}
					}
					System.out.println(inputClient);
					app.reconnect(client);
					waitVariable = "test";
					while (waitVariable.compareTo("done") != 0) {
						waitVariable = client.read();
						if (waitVariable == null) {
							waitVariable = "test";
						}
					}
					System.out.println(waitVariable);
					app.reconnect(client);
				} else if (msg.startsWith("run")) {
					String imgPath = CMDscanner.next();
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
		CMDscanner.close();
		client.disconnect();
	}

	public void reconnect(RobotClient client) {
		client.disconnect();
		try {
			client.connect();
		} catch (Exception e) {
			System.out.println("Cannot connect to PLC. ERR: " + e);
		}
	}
}