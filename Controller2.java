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
//import javafx.scene.media.AudioClip;

import static java.lang.Thread.sleep;


public class Controller2 extends AnchorPane implements Initializable {
    private Button exitButton;
    private boolean isGameRunning = false;
    TranslateTransition transition2;
    @FXML
    private Parent root;
    TranslateTransition transition3;
    private int m = 1;
    private boolean rotateStickFlag = false;
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane pane;
    private int previousWidth=0;
    private int score = 0;
    private Rectangle rectangle2;
    private Rectangle rectangle1;
    TranslateTransition transition;
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
    public void startRotatigStick(Rectangle rectangle2) throws InterruptedException {
        if(!rotateStickFlag) {
            Rotate r = new Rotate();
            r.setAngle(0);
            r.setPivotX(stick.getX());
            r.setPivotY(stick.getY() + stick.getHeight());
            stick.getTransforms().add(r);
            KeyValue keyValue = new KeyValue(r.angleProperty(), 90);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);

            Timeline timeline = new Timeline(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        }
    }
//    private void stopRotatingStick(Rectangle rectangle2) throws  IOException{
//        rotateStickFlag = true;
//        stick.setFill(Color.BLACK);
//    }
    private void imageTransition(double targetyo, int a) throws InterruptedException {
        sleep(2000);
        double targetX = targetyo;
        double targetY = 0;
        transition = new TranslateTransition(Duration.seconds(1), player);
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
            player.setLayoutX(player.getLayoutX());
        }
    }



    public void loadPlayer() {
        try {
            // Load player image (adjust the path accordingly)
            Image playerImage = new Image(new FileInputStream("C:\\Users\\Dell\\Downloads\\pooja\\pooja\\src\\main\\resources\\Player.png"));
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
            System.out.println("get function get's called");
            sleep(1000);
//            imageTransition();

            //-----------------------------------------------------------
            System.out.println("step2");
            System.out.println(stick.getHeight()+rectangle1.getWidth());
            System.out.println(rectangle2.getX());
            if(stick.getHeight()+rectangle1.getWidth() >= rectangle2.getX() && m == 1){
                System.out.println("flag2");
                System.out.println(stick.getHeight()+rectangle1.getWidth());
                System.out.println(rectangle2.getX() + rectangle2.getWidth());
                if(stick.getHeight()+rectangle1.getWidth() <= rectangle2.getX() + rectangle2.getWidth()){
//                    sleep(2000);
                    imageTransition(rectangle2.getX(), 1);
                    System.out.println("yo2");
                    transition.setOnFinished(event2 -> {
                        System.out.println("yo");
                        moveScene(rectangle2.getX(), 1);//r
                    });
//                    transition3.setOnFinished(event3 -> {
                        System.out.println("check");
//                    });

                }
                else {
                    imageTransition(stick.getHeight(), 1);
                    TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), player);
                    transition2.setToY(200);
                    transition.setOnFinished(event2 -> {
                        transition2.play();
                    });
                }
                m = 2;
            }





            else if (stick.getHeight()+rectangle2.getWidth() >= rectangle1.getX() && m == 2) {
                System.out.println("flag is for rectangle 2");
                System.out.println(stick.getHeight()+rectangle2.getWidth());
                System.out.println(rectangle1.getX() + rectangle1.getWidth());
                if(stick.getHeight()+rectangle2.getWidth() >= rectangle1.getX() + rectangle1.getWidth()){
                    imageTransition(rectangle2.getX(), 1);
                    System.out.println("yo2");
                    transition.setOnFinished(event2 -> {
                        System.out.println("yo");
                        moveScene2(rectangle1.getX(), 2);//
                    });
                    System.out.println("check");
                }
                else {
                    imageTransition(stick.getHeight(), 1);
                    TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), player);
                    transition2.setToY(200);
                    transition.setOnFinished(event2 -> {
                        transition2.play();
                    });
                }
                m = 1;
            } else {
                imageTransition(stick.getHeight(), 1);
                TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), player);
                transition2.setToY(200);
                transition.setOnFinished(event2 -> {
                    transition2.play();
                });
//                hitSoundEffect=new AudioClip("file:src/main/resources/com/example/stckhero/stick_fallen.wav");
//                hitSoundEffect.play();
            }
            //-----------------------------------------------------------
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
        stick.setHeight(0);
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


        rectangle2 = new Rectangle(100, 300, 40, 200);
        rectangle2.setFill(Color.RED);
        rectangles.add(rectangle2);
        pane.getChildren().add(rectangle2);
    }
    public void moveScene(double back, int yo){;
        System.out.println("yes"+back);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), stick);
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(2), rectangle1);//
        TranslateTransition transition4 = new TranslateTransition(Duration.seconds(2), rectangle2);
        TranslateTransition transition5 = new TranslateTransition(Duration.seconds(2), player);
        if(yo == 1) {
            transition5.setToX(-(back - 101));
            transition2.setToX(-(back + stick.getHeight()));
            transition3.setToX(-back);
            transition4.setToX(-back);
            transition2.play();
            transition3.play();
            transition4.play();
            transition5.play();
        }
        else if (yo == 2) {
            transition5.setToX((back + 100));//
            transition2.setToX((back + stick.getHeight()));
            transition3.setToX(back);
            transition4.setToX(back);
            transition2.play();
            transition3.play();
            transition4.play();
            transition5.play();
        }
        transition2.setOnFinished(event2 -> {
            System.out.println("come in");
            stick();
            try {
                moveNextRectangle(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void moveScene2(double back, int yo){
        System.out.println("yes"+back);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), stick);
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(2), rectangle1);//
        TranslateTransition transition4 = new TranslateTransition(Duration.seconds(2), rectangle2);
        TranslateTransition transition5 = new TranslateTransition(Duration.seconds(2), player);
            transition5.setToX((back + 100)- 100);
            transition2.setToX((back + stick.getHeight()) - 230);
            transition3.setToX(-back);//-back
            transition4.setToX(back-200);
            System.out.println("back - 200:" + (back-200));
            transition2.play();
            transition3.play();
            transition4.play();
            transition5.play();

        transition5.setOnFinished(event2 -> {
            System.out.println("come in 2");
            stick();
            try {
                moveNextRectangle(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void moveNextRectangle(int i) throws InterruptedException {
//        rectangle1.setX(100);
        if(i == 1){
            TranslateTransition newTransition = new TranslateTransition(Duration.seconds(2), rectangle1);
            newTransition.setToX(100);
            newTransition.play();
        } else if (i == 2) {
            TranslateTransition newTransition = new TranslateTransition(Duration.seconds(2), rectangle2);
            newTransition.setToX(100);
            newTransition.play();
        }
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