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
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.ParallelCamera;


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
        
        
        ParallelCamera camera = new ParallelCamera();
        camera.setTranslateZ(-250);
        Scene scene = new Scene(root, WINDOW_SIZE[0], WINDOW_SIZE[1], true);
        scene.setCamera(camera);
        
        
        
        
        
        
        
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
        
        
        
        

        
        Body body = new Body(40, 40, new Point3D(0, 0, 0), new Vector3D(new Point3D(100, 0, 0), Color.BLUE));
        body.setRotationAxis(Rotate.Y_AXIS);
        
        Axis3D axes = new Axis3D(5, 700, Color.DARKGRAY);
        
        Vector3D v = new Vector3D(new Point3D(100, -100, 100), Color.GREEN);
        Vector3D v2 = new Vector3D(new Point3D(200, 0, 100), Color.RED);
        Vector3D v3 = new Vector3D(new Point3D(100, -300, 0), Color.YELLOW);
        
        
        
        Group group = new Group(body, axes, v, v2, v3);
        group.setTranslateX(SIMULATION_CENTER[0]);
        group.setTranslateY(SIMULATION_CENTER[1]);
        group.setTranslateZ(-500);
        group.getTransforms().addAll(xRotate, yRotate, zRotate);
        
        
        root.getChildren().addAll(mousePosText, group);
        

            
        // Set up the game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
        	handleRotation(scene);
        	t = t + 0.01;
        	body.setTranslateX(Math.sin(t) * 300);
            body.setTranslateZ(Math.cos(t) * 300);
            body.setRotate(t * 180 / Math.PI);
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


    public static void main(String[] args) {
        launch(args);
    }
}
