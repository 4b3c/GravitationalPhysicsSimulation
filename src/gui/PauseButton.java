package gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class PauseButton extends Group {

	public final Polygon triangle;
	public final Rectangle[] bars;
	public Button button;
	private boolean isPaused = true;

	public PauseButton() {
		button = new Button();
		button.setPrefSize(100, 100);
		button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		
		triangle = createTriangle();
		bars = createBars();
		
		getChildren().add(button);

		button.setOnAction(event -> toggle());
		toggle();
	}

	private Polygon createTriangle() {
		double size = 60; // Adjust size as needed
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(
				0.0, size,
				size, size/2,
				0.0, 0.0
		);
		triangle.setFill(Color.rgb(140, 140, 140));
		triangle.setTranslateX(20);
		triangle.setTranslateY(20);
		return triangle;
	}
	
	private Rectangle[] createBars() {
		Rectangle[] bars = {new Rectangle(20, 60), new Rectangle(20, 60)};
		bars[0].setFill(Color.rgb(140, 140, 140));
		bars[0].setTranslateX(20);
		bars[0].setTranslateY(20);
		
		bars[1].setFill(Color.rgb(140, 140, 140));
		bars[1].setTranslateX(60);
		bars[1].setTranslateY(20);
		
		return bars;
	}

	public void toggle() {
		isPaused = !isPaused;
		if (isPaused) {
			getChildren().removeAll(bars[0], bars[1]);
			getChildren().add(triangle);
		} else {
			getChildren().remove(triangle);
			getChildren().addAll(bars[0], bars[1]);
		}
	}

}