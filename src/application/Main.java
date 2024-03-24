package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Main extends Application {
	
	public final int[] WINDOW_SIZE = {1250, 800};
    public final int[] SIMULATION_SIZE = {1000, 800};
    public final int[] SIMULATION_CENTER = {SIMULATION_SIZE[0] / 2, SIMULATION_SIZE[1] / 2};
    public final int[] GUI_SIZE = {WINDOW_SIZE[0] - SIMULATION_SIZE[0], WINDOW_SIZE[1]};
    
    private boolean mouseClicked = false;
    private double[] mousePos = {0.0, 0.0};
    private double[] lastMousePos = {0.0, 0.0};
    private double t = 0;
    
    public static Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    public static Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    public static Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
    

    @Override
    public void start(Stage primaryStage) {
    	
    	// Define text areas for debugging mouse input, and basic things for the window
        Text mousePosText = new Text(10, 20, null);
        
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        Scene scene = new Scene(root, WINDOW_SIZE[0], WINDOW_SIZE[1]);
        
        
        
        
        
        
        
        // Create a VBox for the right side content
        VBox rightVBox = new VBox();
        rightVBox.setPrefWidth(GUI_SIZE[0]);
        rightVBox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        Label label1 = new Label("Label 1");
        VBox.setMargin(label1, new javafx.geometry.Insets(10, 0, 0, 10));
        Label label2 = new Label("Label 2");
        VBox.setMargin(label2, new javafx.geometry.Insets(5, 0, 0, 10));
        Button button = new Button("Click me");
        VBox.setMargin(button, new javafx.geometry.Insets(5, 0, 0, 10));
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Option 1", "Option 2", "Option 3");
        VBox.setMargin(comboBox, new javafx.geometry.Insets(5, 0, 0, 10));
        TextField textField = new TextField();
        textField.setPromptText("Enter your text here");
        VBox.setMargin(textField, new javafx.geometry.Insets(5, 0, 0, 10));
        rightVBox.getChildren().addAll(label1, label2, button, comboBox, textField);
       
        // Set the VBox as the right content of the BorderPane
        root.setRight(rightVBox);
        
        
        
        
        
        
        // Define our box and its center and rotation
        Box box = new Box(50, 100, 200);
        setMaterial(box);
        box.setTranslateX(300);
        box.setTranslateY(20);
        box.setTranslateZ(50);
        
        Sphere body = new Sphere(70);
        setMaterial(body);
        body.setTranslateX(Math.sin(t) * 300);
        body.setTranslateY(0);
        body.setTranslateZ(Math.cos(t) * 300);
        
        // Define some cylinders to act as axis for the 3D space
        Cylinder cylinderX = new Cylinder(2, 750);
        Cylinder cylinderY = new Cylinder(2, 750);
        Cylinder cylinderZ = new Cylinder(2, 750);

        cylinderX.setRotationAxis(new Point3D(1, 0, 0));
        cylinderX.setRotate(90);
        setMaterial(cylinderX);
        cylinderY.setRotationAxis(new Point3D(0, 1, 0));
        cylinderY.setRotate(90);
        setMaterial(cylinderY);
        cylinderZ.setRotationAxis(new Point3D(0, 0, 1));
        cylinderZ.setRotate(90);
        setMaterial(cylinderZ);
        
        Group group = new Group(body, cylinderX, cylinderY, cylinderZ);
        group.setTranslateX(SIMULATION_CENTER[0]);
        group.setTranslateY(SIMULATION_CENTER[1]);
        group.getTransforms().addAll(xRotate, yRotate, zRotate);
        
        
        root.getChildren().addAll(mousePosText, group);
        
        
        
        
        
        
        
        
        

            
        // Set up the game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
        	handleRotation(scene);
        	t = t + 0.01;
        	body.setTranslateX(Math.sin(t) * 300);
            body.setTranslateZ(Math.cos(t) * 300);
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
    }
    
    private void updateMousePosition(Text text1, javafx.scene.input.MouseEvent event) {
    	updateLastMousePos();
    	mousePos[0] = event.getX();
        mousePos[1] = event.getY();
        text1.setText(String.format("Mouse Position: (%.1f, %.1f)", mousePos[0], mousePos[1]));
    }

    private void handleRotation(Scene scene) {
    	if (mouseClicked) {
    		xRotate.setAngle(xRotate.getAngle() + (mousePos[1] - lastMousePos[1]) / 2);
    		yRotate.setAngle(yRotate.getAngle() - (mousePos[0] - lastMousePos[0]) / 2);
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
