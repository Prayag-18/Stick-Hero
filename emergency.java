package com.example.pooja;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

//import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class emergency extends AnchorPane implements Initializable {
    private Button exitButton;
    private boolean isGameRunning = false;



    @FXML
    private Parent root;
    private int m = 1;
    private boolean rotateStickFlag = false;
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane pane;
    private int previousWidth=0;
    private int score = 0;
    private Rectangle rectangle1;
    private final ArrayList<Rectangle> rectangles=new ArrayList<>();
    private AnimationTimer gameLoop;
    private ImageView player;
    private  Rectangle stick;
    private Thread thread1 = new Thread();
    double time=0;
    Rotate rotate ;


    private boolean isSpacePressed =false;

    public void setPane(AnchorPane pane, int width) {
        this.pane = pane;
        this.previousWidth=width;
    }
    public void startRotatigStick(Rectangle rectangle2){
        if(!rotateStickFlag) {
            Rotate r = new Rotate();
            r.setAngle(0);
            r.setPivotX(stick.getX());
            r.setPivotY(stick.getY() + stick.getHeight());
            stick.getTransforms().add(r);

            KeyValue keyValue = new KeyValue(r.angleProperty(), 90);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);

            Timeline timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.setOnFinished(event -> {
                try {
                    stopRotatingStick(rectangle2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            timeline.play();
        }
    }
    private void stopRotatingStick(Rectangle rectangle2) throws  IOException{
        rotateStickFlag = true;
        stick.setFill(Color.BLACK);
    }
    private void imageTransition(double targetyo){
        double targetX = targetyo;
        double targetY = 0;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), player);
        transition.setToX(targetX);
        transition.setToY(targetY);
        transition.play();
    }
//    public void exitGame(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Stick Hero");
//        alert.setHeaderText("You're about to exit!");
//        alert.setContentText("Do you want to exit? ");
//        if (alert.showAndWait().get() == ButtonType.OK) {
//            stage = (Stage) pane.getScene().getWindow();
//
//            stage.close();
//        }
//    }

    //    public void switchToScene1(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource ("Scene1.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene (root);
//        stage.setScene (scene);
//        stage.show();
//        //createObstacles();
//    }
    public void yonda() {
        Thread incrementThread = new Thread(() -> {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                score++;
                System.out.println("Score: " + score);
            }
        });

        incrementThread.start();
        System.out.println("Main thread continues...");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the incrementThread after observing the output for a while
        incrementThread.interrupt();
        System.out.println("Main thread interrupted. Final Score: " + score);
    }

    public void movePlayer() {
        if (player != null) {
            player.setLayoutX(player.getLayoutX() );
        }
    }



    public void loadPlayer() {
        try {
            // Load player image (adjust the path accordingly)
            Image playerImage = new Image(new FileInputStream("C:\\Users\\PRINCE YADAV\\IdeaProjects\\pooja\\src\\main\\resources\\Player.png"));
            // Create an ImageView for the player
            player = new ImageView(playerImage);

            // Set initial position
            player.setX(-75);
            player.setY(112);
            player.setFitWidth(200); // Set your desired width
            player.setFitHeight(250); // Set your desired height

            // Add player to the pane
            pane.getChildren().add(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void update() {
        if (isSpacePressed) {
            extendStick();
        }
        movePlayer();

    }



    public void extendStick() {

        stick.setVisible(true);
        stick.setHeight(stick.getHeight() + 1);

        double currentLayoutY = player.getLayoutY() + player.getFitHeight() / 2;

        stick.setLayoutY(currentLayoutY - stick.getHeight()+63);
        stick.setLayoutX(player.getLayoutX() + player.getFitWidth() -85);

    }
    private void rotateAndLand() {
        if (stick.isVisible()) {
            stick.setRotate(stick.getRotate() + 90);
            for (Rectangle platform : rectangles) {
                if (stick.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    // Stick landed on the platform
                    stick.setVisible(false);
                    stick.setHeight(0);
                    stick.setRotate(0);

                    break;
                }
            }
        }
    }




    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.E) {
            isSpacePressed = true;
        }
    }

    @FXML
    private void handleKeyRelease(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.E ) {
            isSpacePressed = false;
//            stick.setRotate(+90);
//            dropStick();
            startRotatigStick(stick);
            sleep(1000);
            /*
            imageTransition();

            //-----------------------------------------------------------
            if((stick.getHeight()+48) >= rectangle1.getX() && m == 1){
                System.out.println("step2");
                System.out.println(rectangle1.getX());
                System.out.println(rectangle1.getWidth());
                System.out.println(-1*stick.getY()+48);
                if((rectangle1.getX()+ rectangle1.getWidth()) >= (-1*stick.getY()+48)){
                    System.out.println("flag");
//                    transition.setToX(rectangle1.getX());
//                    transition.setCycleCount(1);
//                    transition.play();
                }
                else {
                    transition.setToX(-1 * recStick.getY()-2);
                    transition.setCycleCount(1);
                    transition.play();
                }
                m = 2;
                sceneOffset = (int) recStick.getHeight();
                transition.setOnFinished(actionEvent -> {
                    updateScene(2);
                });

            } else if ((recStick.getHeight()+48) >= rectangle2.getX() && m == 2) {
                System.out.println("step2");
                System.out.println(rectangle1.getX());
                System.out.println(rectangle1.getWidth());
                System.out.println(-1*recStick.getY()+48);
                if((rectangle1.getX()+ rectangle1.getWidth()) >= (-1*recStick.getY()+48)){
                    System.out.println("flag");
                    transition.setToX(rectangle1.getX());
                    transition.setCycleCount(1);
                    transition.play();
                }
                else {
                    transition.setToX(-1 * recStick.getY()-2);
                    transition.setCycleCount(1);
                    transition.play();
                }
                m = 1;
                sceneOffset = (int) recStick.getHeight();
                transition.setOnFinished(actionEvent -> {
                    updateScene(1);
                });
            } else {
                System.out.println("flag2");
                transition.setToX(-1 * recStick.getY()-2);
                transition.setCycleCount(1);
                transition.play();


                transition.setOnFinished(event -> {
                    // Second Transition
                    TranslateTransition secondTransition = new TranslateTransition(Duration.seconds(1), player);
                    secondTransition.setToY(1000);
                    secondTransition.setCycleCount(1);
                    secondTransition.play();
                });
                sceneOffset = (int) recStick.getHeight();
                transition.setOnFinished(actionEvent -> {
                    updateScene(0);
                });
            }

            //-----------------------------------------------------------
             */
        }
    }
    private void moveObstacles() {
        double size = rectangles.get(0).getX();
        rectangles.get(0).setX(rectangles.get(0).getWidth());
        rectangles.get(1).setX(size);
        Rectangle removedObstacle = rectangles.get(0);
        rectangles.remove(0);
        pane.getChildren().remove(removedObstacle);
    }
    private void createRectangle() {
        Random rand = new Random();
        double height = 200;
        double width = rand.nextInt(60) + 40;
        double availableWidth = pane.getWidth() - width - rectangles.get(0).getWidth();
        double space = rand.nextInt((int) Math.abs(availableWidth));
        double refactor = (int) (rectangles.get(0).getWidth() + Math.abs(space));
        Rectangle rectangle = new Rectangle(refactor, 300, width, height);
        rectangle.setFill(Color.BLACK);
        rectangles.add(rectangle);
        pane.getChildren().add(rectangle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        yonda();
        createRectangle2();
        loadPlayer();
        stick();
        startGameLoop();
        gameLoop=new AnimationTimer() {
            @Override
            public void handle(long l) {
                movePlayer();
                update();
            }
        };
        isGameRunning = true;
        gameLoop.start();
    }

    private void stick() {
        stick = new Rectangle(0, 0, 2, 0);
        stick.setTranslateX(-75);
        stick.setTranslateY(112);
        stick.getStyleClass().add("stick");
        pane.getChildren().add(stick);
        stick.setVisible(false);
    }

    private void startGameLoop() {
        if (gameLoop == null) {
            gameLoop = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    update();
                }
            };
        }
        isGameRunning = true;
        gameLoop.start();
    }
    public void switchToScene3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource ("Scene3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene (root);
        stage.setScene (scene);
        stage.show();
    }
    public void createRectangle2(){
        rectangle1 = new Rectangle(0, 300, 40, 200);
        rectangle1.setFill(Color.BLACK);
        rectangles.add(rectangle1);
        pane.getChildren().add(rectangle1);
        Rectangle rectangle2 = new Rectangle(100, 300, 40, 200);
        rectangle2.setFill(Color.BLACK);
        rectangles.add(rectangle2);
        pane.getChildren().add(rectangle2);
    }
}
//    private void dropStick() {
//        int i=0;
//        double startY = stick.getY() + stick.getHeight()/2;
//
//        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), stick);
//        rotateTransition.setByAngle(90);
//
//        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), stick);
//        translateTransition.setByY(startY);
//        translateTransition.setByX((stick.getHeight() / 2));
//
//
//        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, translateTransition);
//        TranslateTransition p1 = new TranslateTransition();
//
//        TranslateTransition translateTransitionPlayer = new TranslateTransition(Duration.millis(500), player);
//        double stickheight = stick.getHeight();
//        double distBtwPlatform = rectangles.get(1).getX() - rectangles.get(0).getWidth();
//
//        parallelTransition.play();
//        parallelTransition.setOnFinished(event -> {
//
//            if(stick.getHeight()>rectangles.get(1).getWidth()+distBtwPlatform){
//                player.setLayoutX(player.getLayoutX()+stick.getHeight());
//                translateTransitionPlayer.setByY(500);
//            }
//            else if(stick.getHeight()>=distBtwPlatform){
//                player.setLayoutX(player.getLayoutX()+rectangles.get(1).getX());
//            }
//            else{
//                player.setLayoutX(player.getLayoutX()+stick.getHeight());
//                translateTransitionPlayer.setByY(500);
//            }
//
//            //   translateTransitionPlayer.play();
//            //stick.setHeight(0);
//
//            moveObstacles();
//            player.setX(-rectangles.get(0).getWidth()-stickheight);
//            createRectangle();
//
//            //stick.setX(rectangles.get(0).getWidth());
//            RotateTransition rotateTransitionagain = new RotateTransition(Duration.millis(500), stick);
//            rotateTransitionagain.setByAngle(270);
////
//
//            ParallelTransition parallelTransitionagain = new ParallelTransition(rotateTransitionagain);
////            TranslateTransition p1again = new TranslateTransition();
//
//            parallelTransitionagain.play();
//
//            TranslateTransition translateTransitionagain = new TranslateTransition(Duration.millis(500), stick);
//            translateTransitionagain.setByX(rectangles.get(0).getWidth());
//
//            ParallelTransition parallelTransitionagain2 = new ParallelTransition(translateTransitionagain);
//            parallelTransitionagain2.play();
//        });
//
//    }
