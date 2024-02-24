package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final double ROTATION_SPEED = 1.0; // Adjust as needed
    private static final double MOVEMENT_SPEED = 5.0; // Adjust as needed

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 600, 400);

            final PhongMaterial blueMaterial = new PhongMaterial();
            blueMaterial.setDiffuseColor(Color.BLUE);
            blueMaterial.setSpecularColor(Color.LIGHTBLUE);

            Box box = new Box(100, 200, 300);
            box.setMaterial(blueMaterial);
            box.setTranslateX(300);
            box.setTranslateY(200);
            box.setRotationAxis(new Point3D(10, 60, 40));
            box.setRotate(0);

            root.getChildren().add(box);

            // Set up the game loop
            Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
                // Handle user input
                handleInput(scene, box);

                // Update game logic
                updateGameLogic(box);
            }));
            gameLoop.setCycleCount(Animation.INDEFINITE);
            gameLoop.play();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInput(Scene scene, Box box) {
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    box.setTranslateY(box.getTranslateY() - MOVEMENT_SPEED);
                    break;
                case DOWN:
                    box.setTranslateY(box.getTranslateY() + MOVEMENT_SPEED);
                    break;
                case LEFT:
                    box.setRotate(box.getRotate() - ROTATION_SPEED);
                    break;
                case RIGHT:
                    box.setRotate(box.getRotate() + ROTATION_SPEED);
                    break;
                default:
                    break;
            }
        });
    }

    private void updateGameLogic(Box box) {
        // Add any game logic updates here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
