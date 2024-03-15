package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Main extends Application {

    private boolean mouseClicked = false;
    private double[] mousePos = {0.0, 0.0};
    private double[] lastMousePos = {0.0, 0.0};

    @Override
    public void start(Stage primaryStage) {
        try {
        	Text mousePosText = new Text(10, 20, null);
        	Text lastMousePosText = new Text(10, 40, null);
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
            root.getChildren().addAll(box, mousePosText, lastMousePosText);

            
            // Set up the game loop
            Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
                // Handle user input
            	handleRotation(scene, xRotate, yRotate);

                // Update game logic
                updateGameLogic(box);
            }));
            gameLoop.setCycleCount(Animation.INDEFINITE);
            gameLoop.play();
            

            
            // Event handler to update mouse position
            scene.setOnMouseMoved(event -> updateMousePosition(mousePosText, lastMousePosText, event));
            scene.setOnMouseDragged(event -> updateMousePosition(mousePosText, lastMousePosText, event));

            // Event handler to check mouse button status
            scene.setOnMousePressed(event -> { mouseClicked = true; });
            scene.setOnMouseReleased(event -> { mouseClicked = false; });

            primaryStage.setScene(scene);
            primaryStage.setTitle("Gravitational Physics Simulation");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateMousePosition(Text text1, Text text2, javafx.scene.input.MouseEvent event) {
    	lastMousePos[0] = mousePos[0];
    	lastMousePos[1] = mousePos[1];
    	mousePos[0] = event.getX();
        mousePos[1] = event.getY();
        text1.setText(String.format("Mouse Position: (%.1f, %.1f)", mousePos[0], mousePos[1]));
        text2.setText(String.format("Last mouse Position: (%.1f, %.1f)", lastMousePos[0], lastMousePos[1]));
    }

    private void handleRotation(Scene scene, Rotate xRotate, Rotate yRotate) {
    	if (mouseClicked) {
    		xRotate.setAngle(xRotate.getAngle() + (mousePos[1] - lastMousePos[1]) / 2);
    		yRotate.setAngle(yRotate.getAngle() - (mousePos[0] - lastMousePos[0]) / 2);
    		lastMousePos[0] = mousePos[0];
        	lastMousePos[1] = mousePos[1];
    	}
    }


    private void updateGameLogic(Box box) {
        // Add any game logic updates here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
