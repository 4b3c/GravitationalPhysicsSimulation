package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Axis3D;
import javafx.scene.ParallelCamera;


public class Main extends Application {
	
	public final int[] WINDOW_SIZE = {1250, 800};
    public final int[] SIMULATION_SIZE = {1000, 800};
    public final int[] SIMULATION_CENTER = {SIMULATION_SIZE[0] / 2, SIMULATION_SIZE[1] / 2};
    public final int[] GUI_SIZE = {WINDOW_SIZE[0] - SIMULATION_SIZE[0], WINDOW_SIZE[1]};
    
    private boolean mouseClicked = false;
    private double[] mousePos = {0.0, 0.0};
    private double[] lastMousePos = {0.0, 0.0};
    private boolean paused = true;
    
    public static Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    public static Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    public static Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
    
    public static final double GRAVITATIONAL_CONSTANT = 0.00000000006674;
    public static final double SCALE = 1000000000;

    @Override
    public void start(Stage primaryStage) {
    	
    	// Define text areas for debugging mouse input, and basic things for the window
        Text mousePosText = new Text(10, 20, null);
        
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        
        
        ParallelCamera camera = new ParallelCamera();
        camera.setTranslateZ(-250);
        Scene scene = new Scene(root, WINDOW_SIZE[0], WINDOW_SIZE[1], true);
        scene.setCamera(camera);
        
        String stylesheet = getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        
        VBox planetList = new VBox();
        planetList.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
        planetList.setDepthTest(DepthTest.DISABLE);
        
        Axis3D axes = new Axis3D(5, 700, Color.DARKGRAY);
        Group group = new Group(axes);

        SolarSystem system = new SolarSystem("SunEarthMoon.txt");
        system.addPlanetUIs(planetList);
        system.addToGroup(group);
        
        root.setRight(planetList);
        group.setTranslateX(SIMULATION_CENTER[0]);
        group.setTranslateY(SIMULATION_CENTER[1]);
        group.setTranslateZ(-500);
        group.getTransforms().addAll(xRotate, yRotate, zRotate);
        
        
        root.getChildren().addAll(mousePosText, group);
            
        // Set up the game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
        	handleRotation(scene);
        	
        	if (!paused) {
        		system.timeTick();
 	        }
        	
        }));
        
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
            

        setInputEvents(scene, mousePosText);
            

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gravitational Physics Simulation");
        primaryStage.show();

    }
    
    private void setInputEvents(Scene scene, Text text1) {
        scene.setOnMouseMoved(event -> updateMousePosition(text1, event));
        scene.setOnMouseDragged(event -> updateMousePosition(text1, event));
        scene.setOnMousePressed(event -> { mouseClicked = true; });
        scene.setOnMouseReleased(event -> { mouseClicked = false; });
        scene.setOnKeyPressed(event -> { if (event.getCode() == KeyCode.P) { paused = !paused; }});
    }
    
    private void updateMousePosition(Text text1, javafx.scene.input.MouseEvent event) {
    	lastMousePos = mousePos.clone();
    	mousePos[0] = event.getX();
        mousePos[1] = event.getY();
        text1.setText(String.format("Mouse Position: (%.1f, %.1f)", mousePos[0], mousePos[1]));
    }

    private void handleRotation(Scene scene) {
    	if (mouseClicked) {
    		xRotate.setAngle(xRotate.getAngle() + (mousePos[1] - lastMousePos[1]) / 2);
    		yRotate.setAngle(yRotate.getAngle() - (mousePos[0] - lastMousePos[0]) / 2);
    		lastMousePos = mousePos.clone();
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
