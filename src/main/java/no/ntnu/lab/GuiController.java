package no.ntnu.lab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.Timer;
import java.util.TimerTask;

public class GuiController {
    @FXML
    Button connectBtn;
    @FXML
    Button sendBtn;
    @FXML
    TextArea areaMessages;
    // Store the buffered message content here
    StringBuilder messageContent = new StringBuilder();

    Timer messageTimer = new Timer();

    public void initialize() {
        setMouseClickListeners();
        startTimedTasks();

        System.out.println("GUI Controller initialized");
    }

    private void startTimedTasks() {
        messageTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                printGuiMessage("Timer fired");
            }
        }, 0, 3000);
    }

    private void setMouseClickListeners() {
        connectBtn.setOnMouseClicked(event -> {
            System.out.println("Connection NOT IMPLEMENTED!");
        });
        sendBtn.setOnMouseClicked(event -> {
            System.out.println("Message sending NOT IMPLEMENTED!");
        });
    }

    private void printGuiMessage(String message) {
        messageContent.append(message);
        messageContent.append("\n");
        areaMessages.setText(messageContent.toString());
    }
}
