package application;

import java.awt.event.MouseEvent;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final double ROTATION_SPEED = 3.0;

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 600, 400);
       
            final PhongMaterial blueMaterial = new PhongMaterial();
            Box box = new Box(100, 200, 300);
            
            // Create separate Rotate transforms for x and y axes
            Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
            Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
            
            blueMaterial.setDiffuseColor(Color.BLUE);
            blueMaterial.setSpecularColor(Color.LIGHTBLUE);
        
            box.setMaterial(blueMaterial);
            box.setTranslateX(300);
            box.setTranslateY(200);
            
            box.getTransforms().addAll(xRotate, yRotate);
            root.getChildren().addAll(box);

            
            
            // Set up the game loop
            Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
                // Handle user input
                handleInput(scene, xRotate, yRotate);

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

    private void handleInput(Scene scene, Rotate xRotate, Rotate yRotate) {
    	final boolean pressed;
        scene.setOnMousePressed(event -> {
        	MouseButton mouseButton = event.getButton();
            switch (mouseButton) {
                case PRIMARY:
                	pressed = true;
                default:
                    break;
            }
        });
        
        
        scene.setOnMouseReleased(event -> {
        	MouseButton mouseButton = event.getButton();
        	switch (mouseButton) {
        		case PRIMARY:
        			int x = 10;
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
