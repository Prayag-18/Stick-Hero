package com.example.pooja;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MoveSceneToLeft2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Move Scene to Left");
        primaryStage.show();

        // Create and move rectangles
        moveRectangle(root, 50, 50);
        moveRectangle(root, 150, 100);
    }

    private void moveRectangle(Pane root, double startX, double startY) {
        Rectangle rectangle = new Rectangle(startX, startY, 100, 50);
        rectangle.setFill(Color.BLUE);
        root.getChildren().add(rectangle);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), rectangle);
        transition.setToX(-100); // Move to the left by 100 units (adjust as needed)

        // Set an event handler to remove the rectangle after the transition
        transition.setOnFinished(event -> root.getChildren().remove(rectangle));

        // Play the transition
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
