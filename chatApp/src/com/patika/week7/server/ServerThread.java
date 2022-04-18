package com.patika.week7.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            // reading the input from client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // writing the output from client
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String outputString = input.readLine();

                if (outputString.equals("exit") || outputString.equals("quit")) {
                    break;
                }

                // print to all clients
                printToClients(outputString);

                // print for server
                System.out.println(outputString);
            }

        } catch (Exception e) {
            System.out.println("Error occured " + e.getStackTrace());
        }
    }

    private void printToClients(String outputString) {
        for (ServerThread sT : threadList) {
            sT.output.println(outputString);
        }
    }
}
