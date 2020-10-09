/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.*;
import java.io.*;

/**
 *
 * @author ancla
 */
public class RobotClient {

    private String hostname;
    private int port;
    private Socket connection;
    private PrintWriter out;

    /**
     *
     * @param hostname Hostname of the robot
     * @param port     Port of the robot
     */
    public RobotClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Method which connects to the robot, using the parameters provided to the
     * constructor.
     */
    public void connect() {
        try {
            connection = new Socket(hostname, port);
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(RobotClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error connecting to robot: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to determine if a connection has been established to the
     * robot.
     *
     * @return COnnection state to see if connection is established (true) or not
     *         (false).
     */
    public boolean isConnected() {
        return connection.isConnected();

    }

    /**
     * This method writes a message to the robot iff a connection to the robot is
     * established.
     *
     * @param message The message to write to the robot.
     */
    public boolean write(String message) {
        if (isConnected()) {
            out.print(message);
            out.flush();
            String inputClient = this.read();
            String waitVariable = inputClient;
            if (waitVariable == null) {
                waitVariable = "null";
            }
            long startTime = System.currentTimeMillis();
            System.out.println("Message: " + message + " Wait: " + waitVariable + " Check: "
                    + (waitVariable.equals(message)));
            while (!waitVariable.equals(message)) {
                waitVariable = this.read();
                if (waitVariable == null) {
                    waitVariable = "null";
                }
            }
            return (waitVariable.equals(message)) ? true : false;
        } else {
            return false;
        }
    }

    /**
     * Method to close connection to the robot.
     */
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (IOException ex) {
                Logger.getLogger(RobotClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String read() {
        String str = "";
        try {
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            str = bf.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return str;
    }
}
