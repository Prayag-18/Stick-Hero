package com.example.pooja;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene. Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Controller1 {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;


    public void exitGame(ActionEvent event) {
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Stick Hero");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText ("Do you want to exit? ");
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();

            stage.close();
        }
    }
    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource ("Scene1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene (root);
        stage.setScene (scene);
        stage.show();
    }
    public void switchToScene2(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource ("Scene2.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene (root);
//        stage.setScene (scene);
//        stage.show();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        Parent root = loader.load();

        AnchorPane anchorPane = (AnchorPane) root;
//        Rectangle r = new Rectangle(0, 300, 70, 200);
//        r.setFill(Color.BLACK);
////        Rectangle r2= new Rectangle(500, 300, 100, 200);
////        r2.setFill(Color.BLACK);
//
//        anchorPane.getChildren().add(r);
////        anchorPane.getChildren().add(r2);
        Controller2 controller2 = loader.getController();
        controller2.setPane((AnchorPane) root,70);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        scene = new Scene (root);


        stage.setScene (scene);
        stage.show();


    }


    public void switchToScene3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource ("Scene3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene (root);
        stage.setScene (scene);
        stage.show();


    }
}



