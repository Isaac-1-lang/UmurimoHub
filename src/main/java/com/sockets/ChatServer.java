package com.sockets;

import java.io.*;
import java.net.*;
public class ChatServer {
   public static void main(String[] args) {
       try (ServerSocket serverSocket = new ServerSocket(1234)) {
           System.out.println("Server is waiting for a client...");
           Socket socket = serverSocket.accept();
           System.out.println("Client connected!");
           BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
           // Thread to read from client
        new Thread(new Runnable() {
    @Override
    public void run() {
        String msgFromClient;
        try {
            while ((msgFromClient = in.readLine()) != null) {
                System.out.println("Client: " + msgFromClient);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}).start();
           // Main thread writes to client
           String msgToClient;
           while ((msgToClient = consoleInput.readLine()) != null) {
               out.println(msgToClient);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
  } 