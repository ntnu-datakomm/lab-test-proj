package no.ntnu.lab;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class MainRunner extends Application {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxml_file_url = getClass().getResource("gui.fxml");
        if (fxml_file_url != null) {
            Parent root = FXMLLoader.load(fxml_file_url);
            Scene scene = new Scene(root);
            stage.setTitle("TCP Client test");
            stage.setScene(scene);
            setupProperApplicationClose(stage);
            stage.show();
        } else {
            System.out.println("Did not find the FXML file!");
        }
    }

    /**
     * Make sure the application finishes work when we click [X] icon of the FX frame
     */
    private void setupProperApplicationClose(Stage stage) {
        stage.setOnCloseRequest((event) -> {
            try {
                stop();
            } catch (Exception e) {
                System.out.println("Could not close the application");
            }
        });
    }
}
