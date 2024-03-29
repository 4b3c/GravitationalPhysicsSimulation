package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleButton extends Group {

    private final Polygon triangle;
    private BodyUI button;
    private boolean isDownward = true;

    public TriangleButton(String text) {
        button = new BodyUI(text);
        triangle = createTriangle();
        
        triangle.setTranslateX(10);
        triangle.setTranslateY(18);
        
        getChildren().addAll(button, triangle);

        button.expandButton.setOnAction(event -> toggleTriangle());
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

    private void toggleTriangle() {
    	button.toggleOptions();
        isDownward = !isDownward;
        triangle.setRotate(isDownward ? 0 : 90);
    }

}
