package application;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EntriesVBox extends VBox {
	
	private double width;

	
	public EntriesVBox(double width) {
		super();
		this.width = width;
		setPrefWidth(this.width);
		setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
	}
	
	public TextField addEntryField(String text) {
        double[] widths = {this.width / 4, this.width * 3 / 4};
        GridSplitter labelAndEntry = new GridSplitter(widths, 2);
        labelAndEntry.addText(text, 0);
        TextField field = labelAndEntry.addTextBox(1);
                
        getChildren().add(labelAndEntry);
        
        return field;
	}
	
	public TextField[] add3DEntryField(String text, String text1, String text2, String text3) {
		TextField[] fields = new TextField[3];
		
        double[] widths = {this.width / 16, this.width * 3 / 16};
        GridSplitter labelAndEntry1 = new GridSplitter(widths, 2);
        labelAndEntry1.addText(text1, 0);
        fields[0] = labelAndEntry1.addTextBox(1);
        
        GridSplitter labelAndEntry2 = new GridSplitter(widths, 2);
        labelAndEntry2.addText(text2, 0);
        fields[1] = labelAndEntry2.addTextBox(1);
        
        GridSplitter labelAndEntry3 = new GridSplitter(widths, 2);
        labelAndEntry3.addText(text3, 0);
        fields[2] = labelAndEntry3.addTextBox(1);
        
        
        double[] widths1 = {this.width / 4, this.width / 4, this.width / 4};
        Node[] nodes = {labelAndEntry1, labelAndEntry2, labelAndEntry3};
        GridSplitter labelAndEntry4 = new GridSplitter(widths1, 3);
        labelAndEntry4.addNodes(nodes);

        
        Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		GridPane.setMargin(label, new javafx.geometry.Insets(2, 2, 2, 2));
        
        double[] widths2 = {this.width / 4, this.width * 3 / 4};
        Node[] nodes2 = {label, labelAndEntry4};
        GridSplitter labelAndEntry = new GridSplitter(widths2, 2);
        labelAndEntry.addNodes(nodes2);
        
        getChildren().add(labelAndEntry);
        
        return fields;
	}

}