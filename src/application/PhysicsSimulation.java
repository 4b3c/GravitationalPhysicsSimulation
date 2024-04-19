package application;

import javafx.application.Application;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
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
import javafx.scene.ParallelCamera;
import javafx.embed.swing.SwingFXUtils;

import gui.PauseButton;
import models.Axis3D;


public class PhysicsSimulation extends Application {
	
	public final int[] WINDOW_SIZE = {1250, 800};
	public final int[] SIMULATION_SIZE = {1000, 800};
	public final int[] SIMULATION_CENTER = {SIMULATION_SIZE[0] / 2, SIMULATION_SIZE[1] / 2};
	public final int[] GUI_SIZE = {WINDOW_SIZE[0] - SIMULATION_SIZE[0], WINDOW_SIZE[1]};
	public static final double GRAVITATIONAL_CONSTANT = 0.00000000006674;
	public static final double SCALE = 1000000000;
	
	public static Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
	public static Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
	public static Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
	
	private boolean mouseClicked = false;
	private double[] mousePos = {0.0, 0.0};
	private double[] lastMousePos = {0.0, 0.0};
	private boolean paused = true;
	private PauseButton playbutton;
	
	public static Scene scene;
	
	public double moonvel = 0.5;

	@Override
	public void start(Stage primaryStage) {
		
		// Define text areas for debugging mouse input, and basic things for the window
		Text mousePosText = new Text(10, 780, null);
		
		BorderPane root = new BorderPane();
		root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
		
		
		ParallelCamera camera = new ParallelCamera();
		camera.setTranslateZ(-250);
		scene = new Scene(root, WINDOW_SIZE[0], WINDOW_SIZE[1], true);
		scene.setCamera(camera);
		
		String stylesheet = getClass().getResource("styles.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);
		
		VBox planetList = new VBox();
		planetList.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
		planetList.setDepthTest(DepthTest.DISABLE);
		
		Axis3D axes = new Axis3D(5, 700, Color.DARKGRAY);
		Group group = new Group(axes);

		SolarSystem system = new SolarSystem("InitVelTest.txt");
		system.addPlanetUIs(planetList);
		system.addToGroup(group);
		
		root.setRight(planetList);
		group.setTranslateX(SIMULATION_CENTER[0]);
		group.setTranslateY(SIMULATION_CENTER[1]);
		group.setTranslateZ(-500);
		group.getTransforms().addAll(xRotate, yRotate, zRotate);
		
		playbutton = new PauseButton();
		VBox buttonContainer = new VBox();
		buttonContainer.getChildren().add(playbutton);
		root.setLeft(buttonContainer);
		buttonContainer.setDepthTest(DepthTest.DISABLE);
		
		root.getChildren().addAll(mousePosText, group);
			
		// Set up the game loop
		Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60), event -> {
			handleRotation();
			
			if (!paused) {
				// Moon completed a rotation
				if (system.timeTick()) {
					PhysicsSimulation.captureScene("MoonVelocity" + moonvel + "TrailLength" + system.planetTrails.get(1).size() + "img.png");
					moonvel = moonvel + 0.1;
					system.changeMoon(moonvel);
				}
			}
			
		}));
		
		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.play();
			

		setInputEvents(scene, mousePosText);
		pausePlay();

		primaryStage.setScene(scene);
		primaryStage.setTitle("Gravitational Physics Simulation");
		primaryStage.show();

	}
	
	private void pausePlay() {
		paused = !paused;
		playbutton.toggle();
	}
	
	private void setInputEvents(Scene scene, Text text1) {
		scene.setOnMouseMoved(event -> updateMousePosition(text1, event));
		scene.setOnMouseDragged(event -> updateMousePosition(text1, event));
		scene.setOnMousePressed(event -> { mouseClicked = true; });
		scene.setOnMouseReleased(event -> { mouseClicked = false; });
		scene.setOnKeyPressed(event -> { if (event.getCode() == KeyCode.P) { pausePlay(); }});
		playbutton.button.setOnAction(event -> { pausePlay(); });
		playbutton.triangle.setOnMouseClicked(event -> { pausePlay(); });
		playbutton.bars[0].setOnMouseClicked(event -> { pausePlay(); });
		playbutton.bars[1].setOnMouseClicked(event -> { pausePlay(); });
	}
	
	private void updateMousePosition(Text text1, javafx.scene.input.MouseEvent event) {
		lastMousePos = mousePos.clone();
		mousePos[0] = event.getX();
		mousePos[1] = event.getY();
		text1.setText(String.format("Mouse Position: (%.1f, %.1f)", mousePos[0], mousePos[1]));
	}

	private void handleRotation() {
		if (mouseClicked) {
			xRotate.setAngle(xRotate.getAngle() + (mousePos[1] - lastMousePos[1]) / 2);
			yRotate.setAngle(yRotate.getAngle() - (mousePos[0] - lastMousePos[0]) / 2);
			lastMousePos = mousePos.clone();
		}
	}
	
    public static void captureScene(String filename) {
        try {
            // Capture the screenshot
            WritableImage image = scene.snapshot(null);

            // Convert the image to a buffered image
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Create a file object
            File file = new File("captures/" + filename);

            // Write the buffered image to the file
            ImageIO.write(bufferedImage, "png", file);

            System.out.println("Screenshot saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

	public static void main(String[] args) {
		launch(args);
	}
}
