package com.example.pooja;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


import java.io.IOException;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
            Scene scene =new Scene(root);
            stage.setTitle("Stick Hero");

            stage.setOnCloseRequest(event -> {
                event.consume();
                exitGame(stage);
            });

            stage.setScene (scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    private void exitGame(Stage stage) {
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Stick Hero");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText ("Do you want to exit? ");
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}