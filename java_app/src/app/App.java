package app;

import app.drawing_in_java.AnimatedDraw;
import app.drawing_in_java.drawings;
import app.edgedetect.EdgeDetector;
import app.edgedetect.Point;
import app.robclient.RobotClient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // The path of the image has to start w. "../images/" as it is the relative path from the file app/edgedetect/Picture.java.
        String imgPath = "../images/";
        String fileName = "download.png";
        String imagePath = imgPath + fileName;

        EdgeDetector eDetect = new EdgeDetector(imagePath);
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
                if (msg.equals("q") || msg.equals("quit")) {
                    break;
                } else if (msg.startsWith("test")) {
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
                else if (msg.equals("show")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();
                    showImage(eDetect, coords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("reset") || msg.equals("re")) {
                    client.reconnect();
                    client.write("rset");
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("st") || msg.equals("stop")) {
                    // TODO: add function to recognize ESC-btn
                    client.reconnect();
                    client.write("stop");
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("i") || msg.equals("image") || msg.equals("loadImage")) {
                    System.out.print("Write new img path: ");
                    String iName = CMDscanner.nextLine();

                    if (iName.isEmpty()) {
                        System.out.println("no image name detected");
                    } else {
                        imagePath = "../images/" + iName;
                        eDetect.loadNewImage(imagePath);
                    }
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("send")) {
                    String draw;
                    String x;
                    String y;
                    boolean writeSuccess;
                    ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();

                    outer:
                    for (int i = 0; i < coords.size(); i++) {
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
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("message") || msg.equals("command") || msg.equals("cmd")) {
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
                else if (msg.equals("run")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getCoordinates();
                    runTest(client, cords);
                    // !---------------------------------------------------------------------------------------------------------------------
                } else if (msg.equals("h") || msg.equals("help")) {
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
                else if (msg.equals("cc") || msg.equals("change con")) {
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
                else if (msg.equals("edge")) {
                    // get coordinates
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    // send coordinates
                    runTest(client, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showedge")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    showImage(eDetect, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("sort")) {
                    // group
                    // sort
                    // https://stackoverflow.com/questions/25287834/how-to-sort-a-collection-of-points-so-that-they-set-up-one-after-another
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    runSortTest(client, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showsort") || msg.equals("ss")) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    showImageAnimated(eDetect, cords);
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

    private static void showImageAnimated(EdgeDetector eDetect, ArrayList<Point> cords) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();

        System.out.println(height + " : " + width);
        AnimatedDraw d = new AnimatedDraw(cords);
        System.out.println("shit works here");
        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(width, height);
        f.add(d);
        f.setVisible(true);
    }

    private static void showImage(EdgeDetector eDetect, ArrayList<ArrayList<ArrayList<Integer>>> cords) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();

        System.out.println(height + " : " + width);

        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawings d = new drawings(cords);
        f.add(d);
        f.setSize(width, height);
        f.setVisible(true);
    }

    private static void runTest(RobotClient client, ArrayList<ArrayList<ArrayList<Integer>>> cords) {
        String draw;
        String x;
        String y;
        boolean writeSuccess;

        outer:
        for (ArrayList<ArrayList<Integer>> cord : cords) {
            for (int j = 0; j < 2; j++) {
                y = String.format("%04d", cord.get(j).get(0));
                x = String.format("%04d", cord.get(j).get(1));
                draw = String.format("%04d", j);

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
    }

    private static void runSortTest(RobotClient client, ArrayList<Point> cords) {
        String draw;
        String x;
        String y;
        boolean writeSuccess;

        for (Point cord : cords) {
            y = String.format("%04d", cord.y);
            x = String.format("%04d", cord.x);
            draw = String.format("%04d", 1);

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
                break;
            }

        }
    }
}
