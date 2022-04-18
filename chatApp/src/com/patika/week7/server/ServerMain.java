package com.patika.week7.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerMain {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // list for multiple client threads
        ArrayList<ServerThread> threadList = new ArrayList<>();

        System.out.print("Enter port number: ");
        int port = in.nextInt();

        try (ServerSocket serversocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port + "...");

            while (true) {
                Socket socket = serversocket.accept();
                System.out.println("New user connected.");

                // creating server thread
                ServerThread serverThread = new ServerThread(socket, threadList);

                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }
}