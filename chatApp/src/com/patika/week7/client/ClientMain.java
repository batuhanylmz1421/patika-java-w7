package com.patika.week7.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    private static List<String> usernameList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String clientName = in.nextLine();
        usernameList.add(clientName);

        System.out.print("Enter server port number: ");
        int port = in.nextInt();

        try (Socket socket = new Socket("localhost", port)) {

            // reading the input from server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // returning the output to the server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // for getting the user input
            String userInput = "joined to chat!";
            String response;

            ClientRunnable clientRun = new ClientRunnable(socket);
            new Thread(clientRun).start();

            System.out.println("Joined Chat Room-" + port);
            System.out.println("You can send messages now.");
            do {
                String message = ("[" + clientName + "]\t: ");
                userInput = in.nextLine();
                if(!userInput.isEmpty()) {
                    output.println(message + userInput);
                }

                if (userInput.equals("exit")) {
                    usernameList.remove(clientName);
                    System.out.println("Chat room closed.");
                    break;
                }
            }
            while (!userInput.equals("exit"));

        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getStackTrace());
        } finally {

        }
    }

    public static String getUsernameList() {
        return Arrays.toString(usernameList.toArray());
    }
}