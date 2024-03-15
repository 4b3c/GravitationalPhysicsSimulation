package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Main extends Application {

    private boolean mouseClicked = false;
    private double[] mousePos = {0.0, 0.0};
    private double[] lastMousePos = {0.0, 0.0};
    
    public static Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    public static Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    public static Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
    public static Rotate xRotate90 = new Rotate(90, Rotate.X_AXIS);
    public static Rotate yRotate90 = new Rotate(90, Rotate.Y_AXIS);
    public static Rotate zRotate90 = new Rotate(90, Rotate.Z_AXIS);
    
    public static Rotate groupRotateX = new Rotate(0, Rotate.X_AXIS);
    public static Rotate groupRotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage primaryStage) {
    	
    	// Define text areas for debugging mouse input, and basic things for the window
        Text mousePosText = new Text(10, 20, null);
        Text lastMousePosText = new Text(10, 40, null);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1200, 800);
        
        // Define our box and its center and rotation
        Box box = new Box(50, 100, 200);
        setMaterial(box);
        box.setTranslateX(300);
        
        // Define some cylinders to act as axis for the 3D space
        Cylinder cylinderX = new Cylinder(2, 750);
        Cylinder cylinderY = new Cylinder(2, 750);
        Cylinder cylinderZ = new Cylinder(2, 750);
        setCenter(cylinderX);
        setCenter(cylinderY);
        setCenter(cylinderZ);
        cylinderX.getTransforms().addAll(xRotate, yRotate90, zRotate90);
        cylinderY.getTransforms().addAll(xRotate, yRotate, zRotate90);
        cylinderZ.getTransforms().addAll(xRotate, yRotate, zRotate);
        
        Group group = new Group(box);
        group.setTranslateX(600);
        group.setTranslateY(400);
        group.getTransforms().addAll(xRotate, yRotate, zRotate);
        
        
        root.getChildren().addAll(mousePosText, lastMousePosText, cylinderX, cylinderY, cylinderZ, group);

            
        // Set up the game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> { handleRotation(scene); }));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
            

        setInputEvents(scene, mousePosText, lastMousePosText);
            

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gravitational Physics Simulation");
        primaryStage.show();

    }
    
    private void setInputEvents(Scene scene, Text text1, Text text2) {
        scene.setOnMouseMoved(event -> updateMousePosition(text1, text2, event));
        scene.setOnMouseDragged(event -> updateMousePosition(text1, text2, event));
        scene.setOnMousePressed(event -> { mouseClicked = true; });
        scene.setOnMouseReleased(event -> { mouseClicked = false; });
    }
    
    private void updateMousePosition(Text text1, Text text2, javafx.scene.input.MouseEvent event) {
    	updateLastMousePos();
    	mousePos[0] = event.getX();
        mousePos[1] = event.getY();
        text1.setText(String.format("Mouse Position: (%.1f, %.1f)", mousePos[0], mousePos[1]));
        text2.setText(String.format("Last mouse Position: (%.1f, %.1f)", lastMousePos[0], lastMousePos[1]));
    }

    private void handleRotation(Scene scene) {
    	if (mouseClicked) {
    		xRotate.setAngle(xRotate.getAngle() + (mousePos[1] - lastMousePos[1]) / 2);
    		yRotate.setAngle(yRotate.getAngle() - (mousePos[0] - lastMousePos[0]) / 2);
    		xRotate90.setAngle(xRotate.getAngle() + 90);
    		yRotate90.setAngle(yRotate.getAngle() + 90);
    		updateLastMousePos();
    	}
    }
    
    private void updateLastMousePos() {
    	lastMousePos[0] = mousePos[0];
    	lastMousePos[1] = mousePos[1];
    }
    
    public static void setMaterial(Shape3D shape) {
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);
        shape.setMaterial(blueMaterial);
    }
    
    public static void setCenter(Shape3D shape) {
        shape.setTranslateX(600);
        shape.setTranslateY(400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
