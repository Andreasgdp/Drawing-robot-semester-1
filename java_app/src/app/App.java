package app;

import app.robclient.RobotClient;
import app.edgedetect.EdgeDetector;
import app.drawing_in_java.drawings;

import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class App {
	public static void main(String[] args) {
		boolean imageLoaded = false;
		boolean coordsLoaded = false;
		String imgPath = "app/images/";

		EdgeDetector eDetect = new EdgeDetector("app/images/download.png");
		RobotClient client = new RobotClient("192.168.0.20", 12345);

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
				// !---------------------------------------------------------------------------------------------------------------------
				if (msg.startsWith("q") || msg.startsWith("quit")) {
					break;
				}

				else if (msg.startsWith("test")) {
					Color[][] testColor = eDetect.getColorArray();
					boolean coordsLoadedTest = eDetect.loadCoordinates(testColor);

					if (coordsLoadedTest) {
						ArrayList<ArrayList<ArrayList<Integer>>> coordinates = eDetect.getCoordinates();
						System.out.println(coordinates);

						for (ArrayList<ArrayList<Integer>> coordinate : coordinates) {
							System.out.println(coordinate);
						}
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("show")) {
					int height = eDetect.getBufferedImage().getHeight();
					int width = eDetect.getBufferedImage().getWidth();

					System.out.println(height + " : " + width);

					JFrame f = new JFrame("Title");
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Color[][] colorArrayTest = eDetect.getColorArray();
					boolean coordsLoadedTest = eDetect.loadCoordinates(colorArrayTest);

					if (coordsLoadedTest) {
						ArrayList<ArrayList<ArrayList<Integer>>> arrayList = eDetect.getCoordinates();
						drawings d = new drawings(arrayList);
						f.add(d);
						f.setSize(width, height);
						f.setVisible(true);
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("reset") || msg.startsWith("re")) {
					client.reconnect();
					client.write("rset");
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("st") || msg.startsWith("stop")) {
					// TODO: add function to recognize ESC-btn
					client.reconnect();
					client.write("stop");
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("i") || msg.startsWith("image") || msg.startsWith("loadImage")) {
					System.out.print("Write new img path: ");
					String iName = CMDscanner.nextLine();

					if (iName.isEmpty()) {
						System.out.println("no image name detected");
					} else {
						imgPath = "app/images/" + iName;
						imageLoaded = eDetect.loadNewImage(imgPath);
						// Resetting coordsloaded as it the image might not match the coords from before
						coordsLoaded = false;
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("coordinates") || msg.startsWith("loadCoordinates")) {
					if (imageLoaded) {
						Color[][] colorArray = eDetect.getColorArray();
						coordsLoaded = eDetect.loadCoordinates(colorArray);
					} else {
						System.out.println("Image is not loaded! Use command 'image' to load image");
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("send")) {
					if (coordsLoaded) {
						String draw = "0";
						String x = "0";
						String y = "0";
						boolean writeSuccess = false;
						ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();

						outer: for (int i = 0; i < coords.size(); i++) {
							for (int j = 0; j < 2; j++) {
								int drawValue = (j == 0 && i != 0) ? 0 : 1;
								y = String.format("%04d", coords.get(i).get(j).get(0));
								x = String.format("%04d", coords.get(i).get(j).get(1));
								draw = String.format("%04d", drawValue);

								System.out.println(x + "," + y + "," + draw);

								// Send x
								writeSuccess = client.write(x);
								client.reconnect();

								// Send y
								if (writeSuccess) {
									writeSuccess = client.write(y);
									client.reconnect();
								}

								// Send draw
								if (writeSuccess) {
									writeSuccess = client.write(draw);
									client.reconnect();
								}

								if (writeSuccess) {
									String waitVariable = "test";
									long startTime = System.currentTimeMillis();

									while (waitVariable.compareTo("done") != 0) {
										waitVariable = client.read();
										if (waitVariable == null) {
											waitVariable = "test";
										}
									}

									client.reconnect();
								} else {
									System.out.println("A problem occurred when trying to send coordinates to the PLC");
									break outer;
								}

							}
						}
					} else {
						System.out.println("Coordinates is not loaded! Use command 'coordinates' to load coordinates");
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("message") || msg.startsWith("command") || msg.startsWith("cmd")) {
					System.out.print("Write the message for the plc: ");
					String message = CMDscanner.nextLine();
					client.write(message);

					String waitVariable = client.read();
					if (waitVariable == null) {
						waitVariable = "null";
					}

					while (waitVariable.compareTo(message) != 0) {
						waitVariable = client.read();
						if (waitVariable == null) {
							waitVariable = "null";
						}
					}

					System.out.println(waitVariable);
					client.reconnect();
					waitVariable = "test";

					while (waitVariable.compareTo("done") != 0) {
						waitVariable = client.read();
						if (waitVariable == null) {
							waitVariable = "test";
						}
					}

					System.out.println(waitVariable);
					client.reconnect();
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("run")) {
					imageLoaded = eDetect.loadNewImage("app/images/download.png");

					if (imageLoaded) {
						Color[][] colorArray = eDetect.getColorArray();
						coordsLoaded = eDetect.loadCoordinates(colorArray);
					} else {
						System.out.println("Image is not loaded! Use command 'image' to load image");
					}

					if (coordsLoaded) {
						String draw = "0";
						String x = "0";
						String y = "0";
						boolean writeSuccess = false;
						ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();

						outer: for (int i = 0; i < coords.size(); i++) {
							for (int j = 0; j < 2; j++) {
								int drawValue = j;
								y = String.format("%04d", coords.get(i).get(j).get(0));
								x = String.format("%04d", coords.get(i).get(j).get(1));
								draw = String.format("%04d", drawValue);

								System.out.println(x + "," + y + "," + draw);

								// Send x
								writeSuccess = client.write(x);
								client.reconnect();

								// Send y
								if (writeSuccess) {
									writeSuccess = client.write(y);
									client.reconnect();
								}

								// Send draw
								if (writeSuccess) {
									writeSuccess = client.write(draw);
									client.reconnect();
								}

								if (writeSuccess) {
									String waitVariable = "test";
									long startTime = System.currentTimeMillis();

									while (waitVariable.compareTo("done") != 0) {
										waitVariable = client.read();
										if (waitVariable == null) {
											waitVariable = "test";
										}
									}

									client.reconnect();
								} else {
									System.out.println("A problem occurred when trying to send coordinates to the PLC");
									break outer;
								}

							}
						}
					} else {
						System.out.println("Coordinates is not loaded! Use command 'coordinates' to load coordinates");
					}
					// !---------------------------------------------------------------------------------------------------------------------
				} else if (msg.startsWith("h") || msg.startsWith("help")) {
					// TODO: Update help command w. all commands
					System.out.println(" ___________________________________________________________________________");
					System.out.println("| HELP:                                                                     |");
					System.out.println("| cc / change con      - Changes hostname and port                          |");
					System.out.println("| i  / load image      - Loads image based on selected image name or path   |");
					System.out.println("| L  / load coordinats - Loads coordinats of the chosen image               |");
					System.out.println("| re / reset           - Resets the PLC coordinats                          |");
					System.out.println("| run                  - Loads image and coordinats, then sends them to PLC |");
					System.out.println("| sd / send            - Sends coordinats to the PLC                        |");
					System.out.println("| st / stop            - Stops the PLC                                      |");
					System.out.println("| q  / quit            - Quits the program                                  |");
					System.out.println("|___________________________________________________________________________|");
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else if (msg.startsWith("cc") || msg.startsWith("change con")) {
					System.out.println("Enter Hostname: ");
					String hostname = CMDscanner.nextLine();
					System.out.println("Enter Port: ");
					String portString = CMDscanner.nextLine();
					int port = Integer.parseInt(portString);
					client = new RobotClient(hostname, port);

					try {
						client.disconnect();
						client.connect();
					} catch (Exception e) {
						System.out.println("Cannot connect to PLC. ERR: " + e);
					}
				}
				// !---------------------------------------------------------------------------------------------------------------------
				else {
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
}
