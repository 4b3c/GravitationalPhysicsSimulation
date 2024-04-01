package gui;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BodyUI extends VBox {
	
	public TriangleButton expandButton;
    private String planetName;
    private EntriesVBox optionsContainer;
    
    private int WIDTH = 300;
    private int DROP_WIDTH = 290;

    public BodyUI(String planetName) {
    	this.planetName = planetName;
        expandButton = new TriangleButton(this.planetName, WIDTH);        
        VBox.setMargin(expandButton.button, new javafx.geometry.Insets(10, 0, 0, 0));
        
        
        optionsContainer = new EntriesVBox(DROP_WIDTH);
        VBox.setMargin(optionsContainer, new javafx.geometry.Insets(10, 0, 0, WIDTH - DROP_WIDTH));
        optionsContainer.setVisible(false);
        
        
        getChildren().addAll(expandButton);
        expandButton.button.setOnAction(event -> toggleOptions());
        
    }

    public void toggleOptions() {
    	expandButton.toggleTriangle();
        optionsContainer.setVisible(!optionsContainer.isVisible());
        if (optionsContainer.isVisible()) {
            getChildren().add(optionsContainer);
            expandButton.button.setText(this.planetName);
        } else {
            getChildren().remove(optionsContainer);
            expandButton.button.setText(this.planetName);
        }
    }
    
    public TextField addEntryField(String text) {
    	return optionsContainer.addEntryField(text);
    }
    
    public TextField[] add3DEntryField(String text, String text1, String text2, String text3) {
    	return optionsContainer.add3DEntryField(text, text1, text2, text3);
    }
} 
