package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.util.Duration;

public class Main extends Application {
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

            // Set up the animation
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0), new KeyValue(box.rotateProperty(), 0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(box.rotateProperty(), 360))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}