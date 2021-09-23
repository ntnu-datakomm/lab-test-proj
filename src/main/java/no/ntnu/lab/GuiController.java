package no.ntnu.lab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GuiController {
    @FXML
    Button connectBtn;
    @FXML
    Button sendBtn;

    public void initialize() {
        connectBtn.setOnMouseClicked(event -> {
            System.out.println("Connection NOT IMPLEMENTED!");
        });
        sendBtn.setOnMouseClicked(event -> {
            System.out.println("Message sending NOT IMPLEMENTED!");
        });

        System.out.println("GUI Controller initialized");
    }
}
