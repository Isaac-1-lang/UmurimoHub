package com.sockets;

import java.io.*;
import java.net.*;
public class ChatClient {
   public static void main(String[] args) {
       try (Socket socket = new Socket("10.11.73.240", 1234)) {
           System.out.println("Connected to Divin!");
           BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
           // Thread to read from server
        new Thread(new Runnable() {
    @Override
    public void run() {
        String msgFromServer;
        try {
            while ((msgFromServer = in.readLine()) != null) {
                System.out.println("Server: " + msgFromServer);
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }
}).start();
           // Main thread writes to server
           String msgToServer;
           while ((msgToServer = consoleInput.readLine()) != null) {
               out.println(msgToServer);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
  }
