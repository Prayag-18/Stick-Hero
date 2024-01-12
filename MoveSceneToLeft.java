package com.example.pooja;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MoveSceneToLeft extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 200);

        Rectangle rectangle = new Rectangle(50, 50, 100, 50);
        rectangle.setFill(Color.BLUE);

        root.getChildren().add(rectangle);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Move Scene to Left");
        primaryStage.show();

        // Move the rectangle to the left
        moveSceneToLeft(root);
    }

    private void moveSceneToLeft(Pane root) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), root);
        transition.setToX(-100); // Move to the left by 100 units (adjust as needed)
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
