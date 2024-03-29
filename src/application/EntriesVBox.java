package application;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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
	
	public void addEntryField(String text) {
		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		GridPane.setMargin(label, new javafx.geometry.Insets(2, 2, 2, 2));
		
		DropShadow shadow = new DropShadow();
        shadow.setSpread(0.2);
        shadow.setColor(Color.rgb(80, 80, 80));
		
		TextField entry = new TextField();
		entry.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry, new javafx.geometry.Insets(2, 2, 2, 2));
        entry.setEffect(shadow);
        
        double[] widths = {this.width / 4, this.width * 3 / 4};
        Node[] nodes = {label, entry};
        GridSplitter labelAndEntry = new GridSplitter(widths, 2);
        labelAndEntry.addNodes(nodes);
        
        getChildren().add(labelAndEntry);
	}
	
	public void add3DEntryField(String text, String text1, String text2, String text3) {
		Label label = new Label(text);
		label.setTextFill(Color.WHITE);
		GridPane.setMargin(label, new javafx.geometry.Insets(2, 2, 2, 2));
		
		Label label1 = new Label(text1);
		label1.setTextFill(Color.WHITE);
		GridPane.setMargin(label1, new javafx.geometry.Insets(2, 2, 2, 2));
		
		Label label2 = new Label(text2);
		label2.setTextFill(Color.WHITE);
		GridPane.setMargin(label2, new javafx.geometry.Insets(2, 2, 2, 2));
		
		Label label3 = new Label(text3);
		label3.setTextFill(Color.WHITE);
		GridPane.setMargin(label3, new javafx.geometry.Insets(2, 2, 2, 2));
		
		DropShadow shadow = new DropShadow();
        shadow.setSpread(0.2);
        shadow.setColor(Color.rgb(80, 80, 80));
		
		TextField entry = new TextField();
		entry.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry, new javafx.geometry.Insets(2, 2, 2, 2));
        entry.setEffect(shadow);
        
        TextField entry1 = new TextField();
		entry1.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry1.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry1, new javafx.geometry.Insets(2, 2, 2, 2));
        entry1.setEffect(shadow);
        
        TextField entry2 = new TextField();
		entry2.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry2.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry2, new javafx.geometry.Insets(2, 2, 2, 2));
        entry2.setEffect(shadow);
        
        TextField entry3 = new TextField();
		entry3.setBackground(new Background(new BackgroundFill(Color.rgb(80, 80, 80), null, null)));
		entry3.setStyle("-fx-text-fill: white;");
		GridPane.setMargin(entry3, new javafx.geometry.Insets(2, 2, 2, 2));
        entry3.setEffect(shadow);
        
        double[] widths1 = {this.width / 16, this.width * 3 / 16};
        Node[] nodes1 = {label1, entry1};
        GridSplitter labelAndEntry1 = new GridSplitter(widths1, 2);
        labelAndEntry1.addNodes(nodes1);
        
        double[] widths2 = {this.width / 16, this.width * 3 / 16};
        Node[] nodes2 = {label2, entry2};
        GridSplitter labelAndEntry2 = new GridSplitter(widths2, 2);
        labelAndEntry2.addNodes(nodes2);
        
        double[] widths3 = {this.width / 16, this.width * 3 / 16};
        Node[] nodes3 = {label3, entry3};
        GridSplitter labelAndEntry3 = new GridSplitter(widths3, 2);
        labelAndEntry3.addNodes(nodes3);
        
        double[] widths4 = {this.width / 4, this.width / 4, this.width / 4};
        Node[] nodes4 = {labelAndEntry1, labelAndEntry2, labelAndEntry3};
        GridSplitter labelAndEntry4 = new GridSplitter(widths4, 3);
        labelAndEntry4.addNodes(nodes4);
        
        double[] widths = {this.width / 4, this.width * 3 / 4};
        Node[] nodes = {label, labelAndEntry4};
        GridSplitter labelAndEntry = new GridSplitter(widths, 2);
        labelAndEntry.addNodes(nodes);
        
        
        
        getChildren().add(labelAndEntry);
	}

}