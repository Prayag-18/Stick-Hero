package com.example.pooja;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RectangleMovement extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a rectangle
        Rectangle rectangle = new Rectangle(50, 50, 100, 50);
        rectangle.setFill(Color.BLUE);

        // Create a translate transition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), rectangle);
        transition.setFromY(0);
        transition.setToY(300); // Move forward to the right
//        transition.setCycleCount(TranslateTransition.INDEFINITE); // Repeat indefinitely
//        transition.setAutoReverse(true); // Move backward as well

        // Create a pane and add the rectangle to it
        Pane root = new Pane();
        root.getChildren().add(rectangle);

        // Set up the scene and show the stage
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Rectangle Movement");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the transition
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
