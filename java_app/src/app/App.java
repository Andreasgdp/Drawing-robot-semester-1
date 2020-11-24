package app;

import app.drawing_in_java.*;
import app.edgedetect.EdgeDetector;
import app.edgedetect.Point;
import app.robclient.RobotClient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;


public class App {
    public App() {

    }

    public static void main(String[] args) {
        // The path of the image has to start w. "../images/" as it is the relative path from the file app/edgedetect/Picture.java.
        String imgPath = "../images/";
        String fileName = "download_nobar.jpg";
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

//        Commandhandler
        while (true) {
            try {
                System.out.print("Write command: ");
                String msg = scanner.next();
                // !---------------------------------------------------------------------------------------------------------------------
                if (msg.equals("q") || msg.equals("quit")) {
                    break;
                } else if (msg.startsWith("test")) {
                    Logging logger = new Logging("MyLogFile.txt");
                    Timer timer = new Timer(1000, logger);

                    timer.start();
                    long testCounter = 0;
                    while (testCounter < 999999999) {
                        long testCounter2 = 0;
                        while (testCounter2 < 20) {
                            testCounter2++;
                        }
                        testCounter++;
                    }
                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

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
                else if (msg.equals("run")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getCoordinates();
                    runTest(client, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("runGreyLine")) {
                    ArrayList<ArrayList<Point>> cords = eDetect.getGreyLineCoordinates();
                    runGreyLineTest(client, cords);
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
                else if (msg.equals("sortedge")) {
                    // get coordinates
                    ArrayList<Point> cords = eDetect.getSortedEdgeCords();
                    // send coordinates
                    runSortTest(client, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("sort")) {
                    // https://stackoverflow.com/questions/25287834/how-to-sort-a-collection-of-points-so-that-they-set-up-one-after-another
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    runSortTest(client, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("show")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> coords = eDetect.getCoordinates();
                    showImage(eDetect, coords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showgreyline") || msg.equals("sgl")) {
                    ArrayList<ArrayList<Point>> coords = eDetect.getGreyLineCoordinates();
                    showGereyLineImage(eDetect, coords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("animategreyline") || msg.equals("agl")) {
                    ArrayList<ArrayList<Point>> coords = eDetect.getGreyLineCoordinates();
                    animateImageAnimated(eDetect, coords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showedge")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    showImage(eDetect, cords);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showsort") || msg.equals("ss")) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    showImageAnimated(eDetect, cords, false);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showsortedge") || msg.equals("sse")) {
                    ArrayList<Point> cords = eDetect.getSortedEdgeCords();
                    System.out.println("Shit works here");
                    showImageAnimated(eDetect, cords, false);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("showsorttest") || msg.equals("sst")) {
                    ArrayList<Point> cords = eDetect.getSortedCords();
                    showImageAnimated(eDetect, cords, true);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("allsimulations") || msg.equals("sim")) {
                    ArrayList<ArrayList<ArrayList<Integer>>> cords1 = eDetect.getCoordinates();
                    showImage(eDetect, cords1);
                    ArrayList<ArrayList<Point>> cords2 = eDetect.getGreyLineCoordinates();
                    showGereyLineImage(eDetect, cords2);
                    animateImageAnimated(eDetect, cords2);
                    ArrayList<ArrayList<ArrayList<Integer>>> cords3 = eDetect.getEdgeCords();
                    showImage(eDetect, cords3);
                    ArrayList<Point> cords4 = eDetect.getSortedCords();
                    showImageAnimated(eDetect, cords4, false);
                    showImageAnimated(eDetect, cords4, true);
                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("benchmark") || msg.equals("bm")) {
                    Logging logger = new Logging("MyLogFile.txt");
                    Timer timer = new Timer(1000, logger);

                    eDetect.loadNewImage("very_small_yoda.jpg");

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    logger.changeFile("first_run_log.txt");
                    timer.start();

//                  Run normal black
                    ArrayList<ArrayList<ArrayList<Integer>>> cords1 = eDetect.getCoordinates();
                    runTest(client, cords1);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    client.reconnect();
                    logger.changeFile("edge_line_log.txt");
                    timer.start();

//                  Run grey line cords
                    ArrayList<ArrayList<Point>> cords2 = eDetect.getGreyLineCoordinates();
                    runGreyLineTest(client, cords2);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    client.reconnect();
                    logger.changeFile("edgedetect_log.txt");
                    timer.start();

//                  Run edge detection cords
                    ArrayList<ArrayList<ArrayList<Integer>>> cords = eDetect.getEdgeCords();
                    runTest(client, cords);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    client.reconnect();
                    logger.changeFile("sort2_log.txt");
                    timer.start();

//                  Run sorted cords (complexity 2)
                    ArrayList<Point> cords3 = eDetect.getSortedCords();
                    runSortTest(client, cords3);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    client.reconnect();
                    eDetect.loadNewImage("download_nobar.jpg");
                    logger.changeFile("sort1_log.txt");
                    timer.start();

//                  Run sorted cords (complexity 1)
                    cords3 = eDetect.getSortedCords();
                    runSortTest(client, cords3);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();

                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    client.reconnect();
                    eDetect.loadNewImage("bigpp.jpg");
                    logger.changeFile("sort3_log.txt");
                    timer.start();

//                  Run sorted cords (complexity 3)
                    cords3 = eDetect.getSortedCords();
                    runSortTest(client, cords3);

                    logger.setFinishTime();
                    timer.stop();
                    logger.logTime();
                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                }
                // !---------------------------------------------------------------------------------------------------------------------
                else if (msg.equals("h") || msg.equals("help")) {
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
                else {
                    System.out.print("\nCommand '" + msg + "' is not a valid command!\n");
                }
                // !---------------------------------------------------------------------------------------------------------------------

            } catch (Exception e) {
                System.out.println("CMD exited w. this err: " + e);
            }
        }
        scanner.close();
        CMDscanner.close();
        client.disconnect();
    }

    public static void showGereyLineImage(EdgeDetector eDetect, ArrayList<ArrayList<Point>> cords) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();

        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawArraylistArrayListPoint d = new DrawArraylistArrayListPoint(cords);
        f.add(d);
        f.setSize(width, height);
        f.setVisible(true);
    }

    public static void animateImageAnimated(EdgeDetector eDetect, ArrayList<ArrayList<Point>> cords) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();
        new AnimatedDrawGreyline(cords, width, height);
    }

    public static void showImageAnimated(EdgeDetector eDetect, ArrayList<Point> cords, boolean test) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();
        if (test) {
            new AnimatedDrawTest(cords, width, height);
        } else {
            new AnimatedDraw(cords, width, height);
        }
    }

    public static void showImage(EdgeDetector eDetect, ArrayList<ArrayList<ArrayList<Integer>>> cords) {
        int height = eDetect.getBufferedImage().getHeight();
        int width = eDetect.getBufferedImage().getWidth();

        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawings d = new drawings(cords);
        f.add(d);
        f.setSize(width, height);
        f.setVisible(true);
    }

    public static void runTest(RobotClient client, ArrayList<ArrayList<ArrayList<Integer>>> cords) {
        String draw;
        String x;
        String y;
        boolean writeSuccess;

        outer:
        for (ArrayList<ArrayList<Integer>> cord : cords) {
            for (int j = 0; j < 2; j++) {
                y = String.format("%04d", cord.get(j).get(0));
                x = String.format("%04d", cord.get(j).get(1));
                int drawVal = (j == 0) ? 5 : 3;
                draw = String.format("%04d", drawVal);

//                System.out.println(x + "," + y + "," + draw);

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

    public static void runGreyLineTest(RobotClient client, ArrayList<ArrayList<Point>> cords) {
        String draw;
        String x;
        String y;
        boolean writeSuccess;

        outer:
        for (ArrayList<Point> cord : cords) {
            for (int j = 0; j < 2; j++) {
                y = String.format("%04d", cord.get(j).y);
                x = String.format("%04d", cord.get(j).x);
                draw = String.format("%04d", cord.get(j).drawVal);

//                System.out.println(x + "," + y + "," + draw);

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

                    while (waitVariable.compareTo("done") != 0 && client.isConnected()) {
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

    public static void runSortTest(RobotClient client, ArrayList<Point> cords) {
        String draw;
        String x;
        String y;
        boolean writeSuccess;
        cords.get(0).setDrawVal(5);

        for (Point cord : cords) {
            y = String.format("%04d", cord.y);
            x = String.format("%04d", cord.x);
            draw = String.format("%04d", cord.drawVal);

//            System.out.println(x + "," + y + "," + draw);

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

                while (waitVariable.compareTo("done") != 0 && client.isConnected()) {
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
