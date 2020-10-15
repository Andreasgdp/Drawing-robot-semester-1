
import robclient.RobotClient;
import edgedetect.EdgeDetector;
import java.awt.image.BufferedImage;

import java.io.*;
import java.awt.Color;
import edgedetect.*;
import java.util.*;

public class App {
    public static String command;
    public boolean imageL;
    public boolean coordinatsL;
    public static void main(String[] args) {
            Scanner scan = new Scanner (System.in);
            boolean imageL = false;
            boolean coordinatsL = false;
            boolean connection = true;
            String path = "";
            String apath = "";
            EdgeDetector eDetect = new EdgeDetector(apath);

            RobotClient client = new RobotClient("hostname", 5000);
            try {
                client.connect();
            } catch (Exception e) {
                System.out.println(e);
            }
            // EdgeDetector eDetect = new EdgeDetector(path);         
            while(true) {
                try {
                    System.out.println("Write command:");
                    String message = scan.nextLine(); 
                    

					if (message.startsWith("q") || message.startsWith("qiut")) {
                        break;
                    }
                    else if (message.startsWith("re") || message.startsWith("reset")){
                        if (connection) {
                        System.out.println("Resetting PLC coordinats");
                        command = "reset";
                        }
                        else {
                            System.out.println("not connected to PLC");
                        }
                    }
                    else if (message.startsWith("st") || message.startsWith("stop")){
                        if (connection) {
                        System.out.println("Stopping PLC");
                        command = "stop";
                        }
                        else {
                            System.out.println("not connected to PLC");
                        }
                    }
                    else if (message.startsWith("i") || message.startsWith("load image")){
                        System.out.println("Enter image name:");
                        String iName = scan.nextLine();
                            if (iName.isEmpty()) {
                                System.out.println("no image name detected");
                            }                  
                            else if (!iName.isEmpty()){
                                apath = "app/images/" + iName;
                                File afile = new File(apath);
                                if(afile.exists()){
                                    System.out.println("file exists");
                                    command = "open image"; 
                                    imageL = true;
                                    // EdgeDetector eDetect = new EdgeDetector(apath);
                                    // eDetect.loadNewImage(apath);
                                    // System.out.println(eDetect);
                                }
                                else {
                                    System.out.println("image dosnt exist in folder: app/images");
                                    System.out.println("do you wish to select an alternative path?");
                                    String diffpath = scan.nextLine();
                                    if(diffpath.startsWith("y")){
                                        System.out.println("enter alternative image path:");
                                        String alpath = scan.nextLine();
                                        File alfile = new File(alpath);
                                        if(alfile.exists()){
                                            System.out.println("file exists");
                                            command = "open image"; 
                                            imageL = true;
                                            // EdgeDetector eDetect = new EdgeDetector(alpath);
                                            // System.out.println(eDetect);
                                        }
                                        else {
                                            System.out.println("file dosn't exist");
                                        }
                                    }
                                }
                            }

                    }
                    else if (message.startsWith("sd") || message.startsWith("send")) {
                        if (connection) {
                            if (imageL == true && coordinatsL == true) {
                            System.out.println("Sending coordinats to PLC");
                            // String command = eDetect.loadCordinates();
                            client.write(command);
                            }
                            else if (imageL != true) {
                                System.out.println("no immage loaded");
                            }
                            else if (coordinatsL != true) {
                                System.out.println("coordinats not loaded");
                            }
                        }
                        else {
                            System.out.println("not connected to PLC");
                        }
                    }
                    else if (message.startsWith("cm") || message.startsWith("command")) {
                        System.out.println(command);
                    }
                    else if (message.startsWith("l") || message.startsWith("load coordinats")) {
                        if (imageL == true) {
                            System.out.println("Loading coordinats");
                            command = "loadCoord";
                            coordinatsL = true;
                            // Color[][] colorArray = eDetect.getColorArray();
						    // eDetect.loadCoordinates(colorArray);
                        }
                        else {
                            System.out.println("no immage selected");
                        }
                    }
                    else if (message.startsWith("run")) {
                        if (connection) {
                            System.out.println("Enter image name");
                            String iName = scan.nextLine();
                            if (iName.isEmpty()) {
                                System.out.println("no image selectet");
                            }
                            else if (!iName.isEmpty()) {
                                apath = "app/images/" + iName;
                                File afile = new File(apath);
                                    if(afile.exists()){
                                        System.out.println("file exists");
                                        command = "open image"; 
                                        imageL = true;
                                        path = apath;
                                    }
                                    else {
                                        System.out.println("image dosnt exist in folder: app/images");
                                        System.out.println("do you wish to select an alternative path?");
                                        String diffpath = scan.nextLine();
                                        if(diffpath.startsWith("y")){
                                            System.out.println("enter alternative image path:");
                                            String alpath = scan.nextLine();
                                            File alfile = new File(alpath);
                                            if(alfile.exists()){
                                                System.out.println("image choosen");
                                                command = "open image"; 
                                                imageL = true;
                                                path = alpath;
                                            }
                                        }
                                    }
                            } 
                            if (imageL == true) {
                                System.out.println("loading coordinats");
                                coordinatsL = true;
                                command = "loadCoord";
                            }
                            else {
                                System.out.println("somthing wendt wrong while loading image");
                            }
                            if (coordinatsL == true){
                                System.out.println("sending coordinats to PLC");
                                command = "send";
                            }
                            else {
                                System.out.println("somthing wendt wrong while loading image");
                            }
                        }
                        else {
                            System.out.println("not connected to PLC");
                        }
                    }
                    else if (message.startsWith("h") || message.startsWith("help")) {
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
                    // last "if" funktion possibly irellecant
                    else if (message.startsWith("cc") || message.startsWith("change con")){
                        System.out.println("enter Hostname:");
                        String hostname = scan.nextLine();
                        System.out.println("enter Port:");
                        String porti = scan.nextLine();
                        int port = Integer.parseInt(porti);
                        String client1 = new String(hostname + ", " + port) ;
                        System.out.println(client1);
                    }
                    else {
						System.out.println("The command: \"" + message + "\" is invalid");
                        System.out.println("if you need help, type help");
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
        }
        scan.close();
    }
}