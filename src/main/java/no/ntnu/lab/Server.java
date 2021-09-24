package no.ntnu.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple Echo server
 */
public class Server {

    public static void main(String[] args) {
        System.out.println("Starting ECHO server...");
        Server server = new Server();
        server.run();
    }

    /**
     * Run the TCP server - listen for incoming connections, handle each connection
     */
    private void run() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(1380);

            // We could set this to false based on some condition - when we want to shut down the server...
            boolean mustRun = true;

            while (mustRun) {
                Socket clientSocket = welcomeSocket.accept();
                System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
                Thread clientThread = new Thread(() -> {
                    handleNextClientConnection(clientSocket);
                });
                clientThread.start();
            }

            System.out.println("Server shutting down...");
            welcomeSocket.close();

        } catch (IOException e) {
            System.out.println("Could not open a listening socket: " + e.getMessage());
        }
        System.out.println("Server done, exiting");
    }

    /**
     * Handle connection of one client - send/receive messages according to the protocol. Closes the socket in the end.
     * @param clientSocket
     */
    private static void handleNextClientConnection(Socket clientSocket) {
        try {
            String clientMessage = readClientInput(clientSocket);
            if (clientMessage != null) {
                System.out.println("Received message from client: " + clientMessage);
                if (clientMessage.length() < 100) {
                    sendResponseToClient(clientSocket, clientMessage.toUpperCase());
                    System.out.println("Response sent to client");
                } else {
                    System.out.println("Client message too long!");
                }
            } else {
                System.out.println("Client sent no valid message");
            }
            System.out.println("Closing client socket...");
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error while handling client socket: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    /**
     * Send a message to the client, over the socket
     * @param clientSocket Socket connected to the particular client
     * @param message The text message to send
     * @throws IOException
     * @throws InterruptedException
     */
    private static void sendResponseToClient(Socket clientSocket, String message) throws IOException, InterruptedException {
        PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        // Simulate some long operations
        Thread.sleep(3 * 1000);
        outToClient.println(message);
    }

    /**
     * Read one line of text from the socket
     * @param clientSocket
     * @return The message received, null when socket is closed by the client
     * @throws IOException
     */
    private static String readClientInput(Socket clientSocket) throws IOException {
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return inFromClient.readLine();
    }
}
