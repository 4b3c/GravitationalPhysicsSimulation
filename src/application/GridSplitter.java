package application;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GridSplitter extends GridPane {
	
	private ColumnConstraints[] columns;
	private int splits;
	
	public GridSplitter(double[] widths, int splits) {
		this.splits = splits;
		columns = new ColumnConstraints[this.splits];
		
		for (int i = 0; i < splits; i++) {
			columns[i] = new ColumnConstraints(widths[i]);
			getColumnConstraints().add(columns[i]);
		}
	}
	
	public void addNodes(Node[] nodes) {
		for (int i = 0; i < this.splits; i++) {
			setConstraints(nodes[i], i, 0);
			getChildren().add(nodes[i]);
		}
	}
	
	public void addText(String text, int column) {
		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		GridPane.setMargin(label, new javafx.geometry.Insets(2, 2, 2, 2));
		
		setConstraints(label, column, 0);
		getChildren().add(label);
	}
	
	public void addTextBox(int column) {
		DropShadow shadow = new DropShadow();
        shadow.setSpread(0.2);
        shadow.setColor(Color.rgb(80, 80, 80));
        
		TextField entry = new TextField();
		entry.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry, new javafx.geometry.Insets(2, 2, 2, 2));
        entry.setEffect(shadow);
        
        setConstraints(entry, column, 0);
		getChildren().add(entry);
	}

}
