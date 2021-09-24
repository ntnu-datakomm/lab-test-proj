package no.ntnu.lab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Controller for the Java FX frame
 */
public class GuiController implements MessageListener {
    @FXML
    Button connectBtn;
    @FXML
    Button sendBtn;
    @FXML
    TextArea areaMessages;
    // Store the buffered message content here
    StringBuilder messageContent = new StringBuilder();

    TcpLogic logic = new TcpLogic();

    public void initialize() {
        setMouseClickListeners();
        logic.addListener(this);
        System.out.println("GUI Controller initialized");
    }

    /**
     * Initialize mouse-click listeners
     */
    private void setMouseClickListeners() {
        connectBtn.setOnMouseClicked(event -> {
            if (connectToServer()) {
                new Thread(() -> {
                    logic.receiveDataFromServer();
                }).start();
            }
        });
        sendBtn.setOnMouseClicked(event -> {
            sendMessageToServer();
        });
    }

    /**
     * Send a message to the server (TCP socket)
     */
    private void sendMessageToServer() {
        String error = logic.sendMessageToServer("hei");
        if (error != null) {
            printGuiMessage(error);
        } else {
            printGuiMessage("Message sent to the server");
        }
    }

    /**
     * Connect to the TCP server
     * @return true on success, false on error
     */
    private boolean connectToServer() {
        String error = logic.connectToServer();
        if (error == null) {
            printGuiMessage("Connection successful");
            // Can't connect now, but can send a message
            connectBtn.setDisable(true);
            sendBtn.setDisable(false);
        } else {
            printGuiMessage(error);
        }
        return error == null;
    }

    /**
     * Add a message to the GUI text-area
     * @param message
     */
    private void printGuiMessage(String message) {
        messageContent.append(message);
        messageContent.append("\n");
        areaMessages.setText(messageContent.toString());
    }

    /**
     * This method is called when an incoming message is received from the server
     * @param message
     */
    @Override
    public void onMessageReceived(String message) {
        printGuiMessage("FROM SERVER: " + message);
    }

    /**
     * This method is called when connection with the server is closed
     */
    @Override
    public void onServerDisconnected() {
        printGuiMessage("Connection to the server closed");
        // Can't send a message now, but can connect again
        connectBtn.setDisable(false);
        sendBtn.setDisable(true);
    }
}
