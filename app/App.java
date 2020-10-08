
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import robclient.RobotClient;
import edgedetect.*;
import java.util.*;




public class App {
    public static String command;
    public String image;
    public static void main(String[] args) {
            Scanner scan = new Scanner (System.in);
            String image = "not loaded";
            RobotClient client = new RobotClient("hostname", 5000);
            try {
                client.connect();
            } catch (Exception e) {
                System.out.println(e);
            }
            EdgeDetector eDetect = new EdgeDetector("./imanges/black.jpg");
            String imagePath = "./images/";

            while(true) {
                try {
                    System.out.println("Enter message");
                    String message = scan.nextLine(); 
                    

					if (message.startsWith("q") || message.startsWith("qiut")) {
                        break;
                    }
                    else if (message.startsWith("re") || message.startsWith("reset")){
                        System.out.println("Resetting PLC coordinats");
                        command = "reset";
                    }
                    else if (message.startsWith("st") || message.startsWith("stop")){
                        System.out.println("Stopping PLC");
                        command = "stop";
                    }
                    else if (message.startsWith("image") || message.startsWith("load image")){
                        System.out.println("Enter image path:");
                        String iPath = scan.nextLine();
                        File tmpDir = new File("./images/black.jpg");
                        System.out.println(tmpDir);
                        boolean exists = tmpDir.exists();
                        if (exists){
                            System.out.println("image found: " + iPath);
                            command = "open image"; 
                            System.out.println("Loading coordinats");
                            command = "loadCoord";
                        }     
                        else {
                            System.out.println("image not found");
                        }                  
                        
                    }
                    else if (message.startsWith("sd") || message.startsWith("send")) {
                        System.out.println("Sending coordinats to PLC");
                        String command = eDetect.loadCordinates();
                        client.write(command);
                    }
                    else if (message.startsWith("c") || message.startsWith("command")) {
                        System.out.println(command);
                        
                    }
                    // else if (message.startsWith("loadc") || message.startsWith("load coordinats")) {
                    //     if (image == "loaded") {
                            
                    //     }
                        // else {
                        //     System.out.println("no immage selected");
                        // }
                        // Runs Load, send og load image
                    //}
                    else if (message.startsWith("run")) {
                        System.out.println("Running EVERYTHING!!!");
                    }
                    // Skriv hvis man skriver en invalid cmd ind
                    else {
						System.out.println("The command: \"" + message + "\" is invalid");
						System.out.println("(╯°□°)╯︵ ┻━┻");
						
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
        }
        scan.close();
    }
}