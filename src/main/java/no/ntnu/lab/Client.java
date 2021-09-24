package no.ntnu.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A simple TCP client for testing of the ECHO server
 */
public class Client {
    private Socket socket;

    public static void main(String[] args) {
        System.out.println("Starting client...");
        Client client = new Client();
        client.run();
    }

    /**
     * Run the TCP client
     */
    private void run() {
        try {
            socket = new Socket("localhost", 1380);
            sendMessageToServer();
            waitResponseFromServer();
            System.out.println("Closing socket...");
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not establish a connection:" + e.getMessage());
        }

        System.out.println("Client DONE");
    }

    /**
     * Wait for a response message from the server
     * @throws IOException
     */
    private void waitResponseFromServer() throws IOException {
        System.out.println("Waiting for response from server");
        BufferedReader inFromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String serversResponse = inFromSocket.readLine();
        if (serversResponse != null) {
            System.out.println("Server's response: " + serversResponse);
        } else {
            System.out.println("Server closed the connection!");
        }
    }

    /**
     * Send a text message to the server
     * @throws IOException
     */
    private void sendMessageToServer() throws IOException {
        String messageToSend = "hei";
        PrintWriter outToSocket = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Sending message to server...");
        outToSocket.println(messageToSend);
    }
}
