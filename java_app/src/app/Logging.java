package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class

public class Logging implements ActionListener {
    private long startTime;
    private long currTime;
    private long finishTime;
    private String filePath;
    private boolean activated;


    public Logging(String filePath){
        this.filePath = filePath;
        this.startTime = 0;
        this.finishTime = 0;
        this.activated = false;
    }

    public void changeFile(String file) {
        this.filePath = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currTime = e.getWhen();
        if (this.currTime != 0 && !this.activated) {
            this.activated = true;
            this.setStartTime();
        }
    }

    public void setStartTime() {
        this.startTime = this.currTime;
    }

    public void setFinishTime() {
        this.finishTime = this.currTime;
    }

    public void logTime() {
        System.out.println("Start time: " + this.startTime + " and finish time: " + this.finishTime);
        this.createFile();
        this.writeFile(Long.toString(java.lang.Math.abs(this.startTime - this.finishTime)/1000));
        this.startTime = 0;
        this.finishTime = 0;
        this.currTime = 0;
        this.activated = false;
    }

    private void createFile() {
        try {
            File myObj = new File(this.filePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeFile(String input) {
        try {
            FileWriter myWriter = new FileWriter(this.filePath);
            myWriter.write("The time it took to run the latest run was: " + input + " seconds");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
