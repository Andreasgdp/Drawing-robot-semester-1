import robclient.RobotClient;
import edgedetect.EdgeDetector;
import drawing_in_java.drawings;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class App {
	public static void main(String[] args) {
		App app = new App();
		boolean imageLoaded = false;
		boolean coordsLoaded = false;
		String apath = "app/images/";
		String imgPath = "app/images/";

		EdgeDetector eDetect = new EdgeDetector("app/images/download.png");
		// RobotClient client = new RobotClient("localhost", 4999);
		// RobotClient client = new RobotClient("127.0.0.1", 12345);
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
				}

				else if (msg.startsWith("test")) {
					Color[][] testColor = eDetect.getColorArray();
					boolean coordsLoadedTest = eDetect.loadCoordinates(testColor);
					if (coordsLoadedTest) {
						ArrayList<ArrayList<ArrayList<Integer>>> coordinates = eDetect.getCoordinates();
						System.out.println(coordinates);
						for (int i = 0; i < coordinates.size(); i++) {
							System.out.println(coordinates.get(i));
						}
					}
				}

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

				else if (msg.startsWith("reset") || msg.startsWith("re")) {
					app.reconnect(client);
					client.write("rset");
				}

				else if (msg.startsWith("st") || msg.startsWith("stop")) {
					// TODO: add function to recognize ESC-btn
					app.reconnect(client);
					client.write("stop");
				}

				else if (msg.startsWith("i") || msg.startsWith("image") || msg.startsWith("loadImage")) {
					System.out.print("Write new img path: ");
					String iName = CMDscanner.nextLine();
					// TODO: move this check of filepath to the loadNewImage method
					// imageLoaded = eDetect.loadNewImage(imgPath);
					if (iName.isEmpty()) {
						System.out.println("no image name detected");
					} else if (!iName.isEmpty()) {
						imgPath = "app/images/" + iName;
						File afile = new File(apath);
						if (afile.exists()) {
							System.out.println("file exists");
							imageLoaded = eDetect.loadNewImage(imgPath);
						} else {
							System.out.println("image dosnt exist in folder: app/images");
							System.out.println("do you wish to select an alternative path?");
							String diffpath = CMDscanner.nextLine();
							if (diffpath.startsWith("y")) {
								System.out.println("enter alternative image path:");
								String alpath = CMDscanner.nextLine();
								File alfile = new File(alpath);
								if (alfile.exists()) {
									System.out.println("file exists");
									imageLoaded = true;
									// EdgeDetector eDetect = new EdgeDetector(alpath);
									// System.out.println(eDetect);
								} else {
									System.out.println("file dosn't exist");
								}
							}
						}
					}
				}

				else if (msg.startsWith("coordinates") || msg.startsWith("loadCoordinates")) {
					if (imageLoaded) {
						Color[][] colorArray = eDetect.getColorArray();
						eDetect.loadCoordinates(colorArray);
						coordsLoaded = true;
					} else {
						System.out.println("Image is not loaded! Use command 'image' to load image");
					}
				}

				else if (msg.startsWith("send")) {
					if (coordsLoaded) {
						// client.write(eDetect.getCoordinates());
						String draw = "0";
						String x = "0";
						String y = "0";
						boolean writeSuccess = false;
						ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();
						outer: for (int i = 0; i < coords.size(); i++) {
							// [[x1,y1],[x2,y2]]
							for (int j = 0; j < 2; j++) {
								// j = 0: [x1,y1] ; j = 1: [x2,y2]
								x = Integer.toString(coords.get(i).get(j).get(0));
								y = Integer.toString(coords.get(i).get(j).get(1));
								draw = Integer.toString(j);

								// Send x
								writeSuccess = client.write(x);
								app.reconnect(client);

								// Send y
								if (writeSuccess) {
									writeSuccess = client.write(y);
									app.reconnect(client);
								}

								// Send draw
								if (writeSuccess) {
									writeSuccess = client.write(draw);
									app.reconnect(client);
								}
								if (writeSuccess) {
									String waitVariable = "test";
									long startTime = System.currentTimeMillis();
									while (waitVariable.compareTo("done") != 0
											|| (System.currentTimeMillis() - startTime) < 10000) {
										waitVariable = client.read();
										if (waitVariable == null) {
											waitVariable = "test";
										}
									}
									app.reconnect(client);
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

				else if (msg.startsWith("message")) {
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
					System.out.println(waitVariable);
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
				}

				else if (msg.startsWith("run")) {
					// System.out.print("Write new img path: ");
					// String imgPath = CMDscanner.next();
					// imageLoaded = eDetect.loadNewImage(imgPath);
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
						System.out.println("shit works here " + coords.size());
						outer: for (int i = 0; i < coords.size(); i++) {
							// [[x1,y1],[x2,y2]]
							for (int j = 0; j < 2; j++) {
								// j = 0: [x1,y1] ; j = 1: [x2,y2]
								int drawValue = (j == 0 && i != 0) ? 0 : 1;
								y = String.format("%04d", coords.get(i).get(j).get(0));
								x = String.format("%04d", coords.get(i).get(j).get(1));
								draw = String.format("%04d", drawValue);

								System.out.println(x + "," + y + "," + draw);

								// Send x
								writeSuccess = client.write(x);
								app.reconnect(client);

								// Send y
								if (writeSuccess) {
									writeSuccess = client.write(y);
									app.reconnect(client);
								}

								// Send draw
								if (writeSuccess) {
									writeSuccess = client.write(draw);
									app.reconnect(client);
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
									app.reconnect(client);
								} else {
									System.out.println("A problem occurred when trying to send coordinates to the PLC");
									break outer;
								}

							}
						}
					} else {
						System.out.println("Coordinates is not loaded! Use command 'coordinates' to load coordinates");
					}
				} else if (msg.startsWith("h") || msg.startsWith("help")) {
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

	public void reconnect(RobotClient client) {
		if (client.isConnected()) {
			client.disconnect();
			try {
				client.connect();
			} catch (Exception e) {
				System.out.println("Cannot connect to PLC. ERR: " + e);
			}
		}
	}
}