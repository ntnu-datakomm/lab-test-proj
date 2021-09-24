package no.ntnu.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents logic of TCP communication - the client side
 */
public class TcpLogic {
    List<MessageListener> listeners = new LinkedList<>();
    Socket socket;
    BufferedReader inFromServer;
    PrintWriter outToServer;

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_TCP_PORT = 1380;


    /**
     * Add a new listener which will receive updates (incoming messages from the server)
     *
     * @param listener
     */
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    /**
     * Establish connection to a TCP server
     *
     * @return null on success, error message on failure
     */
    public String connectToServer() {
        String error = null;
        try {
            if (socket == null) {
                socket = new Socket(SERVER_ADDRESS, SERVER_TCP_PORT);
                initializeStreams();
            } else {
                error = "Already connected!";
            }
        } catch (IOException e) {
            error = "Could not connect to the server: " + e.getMessage();
        }
        return error;
    }

    /**
     * Initialize input and output streams for the socket. The socket must be connected!
     */
    private void initializeStreams() throws IOException {
        outToServer = new PrintWriter(socket.getOutputStream(), true);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Send a message to the server
     *
     * @param message
     * @return null on success, error message on failure
     */
    public String sendMessageToServer(String message) {
        String error = null;
        if (socket != null) {
            outToServer.println(message);
        } else {
            error = "Not connected!";
        }
        return error;
    }

    /**
     * Receive data from the server, in a loop. On each new message all the listeners will be notified
     */
    public void receiveDataFromServer() {
        try {
            while (socket != null) {
                String messageFromServer = inFromServer.readLine();
                if (messageFromServer != null) {
                    for (MessageListener listener : listeners) {
                        listener.onMessageReceived(messageFromServer);
                    }
                } else {
                    closeConnection();
                }
            }
        } catch (IOException e) {
            System.out.println("Reading from socket failed: " + e.getMessage());
        }
    }

    /**
     * Close connection (socket) to the server. Also notify listeners about it.
     */
    public void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Could not close socket: " + e.getMessage());
            }
            socket = null;
            for (MessageListener listener : listeners) {
                listener.onServerDisconnected();
            }
        }
    }
}
