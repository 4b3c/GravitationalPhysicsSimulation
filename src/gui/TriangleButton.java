package gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleButton extends Group {

	public final Polygon triangle;
	public Button button;
	private boolean isDownward = true;

	public TriangleButton(String text, int width) {
		DropShadow shadow = new DropShadow();
		shadow.setSpread(0.2); // Controls blurriness (0 for sharp, 1 for very blurry)
		shadow.setColor(Color.rgb(80, 80, 80)); // Set shadow color
		
		button = new Button(text);
		button.setPrefWidth(width);
		button.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
		button.setEffect(shadow);
		button.setTextFill(Color.WHITE);
		
		triangle = createTriangle();
		triangle.setTranslateX(10);
		triangle.setTranslateY(8);
		
		getChildren().addAll(button, triangle);

		button.setOnAction(event -> toggleTriangle());
		triangle.setOnMouseClicked(event -> toggleTriangle());
	}

	private Polygon createTriangle() {
		double size = 10; // Adjust size as needed
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(
				0.0, size,
				size, size/2,
				0.0, 0.0
		);
		triangle.setFill(Color.rgb(140, 140, 140));
		return triangle;
	}

	public void toggleTriangle() {
		isDownward = !isDownward;
		triangle.setRotate(isDownward ? 0 : 90);
	}

}
