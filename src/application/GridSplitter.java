package application;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

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

}
