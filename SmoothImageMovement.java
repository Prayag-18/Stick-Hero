package com.example.pooja;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SmoothImageMovement extends Application {

    @Override
    public void start(Stage primaryStage) {
        ImageView movingImage = new ImageView(new Image("C:\\Users\\PRINCE YADAV\\IdeaProjects\\pooja\\src\\main\\resources\\Player.png"));

        // Set the initial position
        movingImage.setTranslateX(0);
        movingImage.setTranslateY(0);

        // Set the target position
        double targetX = 200;
        double targetY = 0;

        // Create a TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(5), movingImage);
        transition.setToX(targetX);
        transition.setToY(targetY);

        // Play the transition
        transition.play();

        StackPane root = new StackPane();
        root.getChildren().add(movingImage);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Smooth Image Movement in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
