package application;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BodyUI extends VBox {
	
	public Button expandButton;
    private String planetName;
    private EntriesVBox optionsContainer;
    
    private int WIDTH = 300;
    private int DROP_WIDTH = 290;

    public BodyUI(String planetName) {
    	this.planetName = planetName;
        expandButton = new Button(this.planetName); // Downward triangle for expansion
        expandButton.setPrefWidth(WIDTH);
        expandButton.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
        
     // Create a DropShadow object with desired properties
        DropShadow shadow = new DropShadow();
        shadow.setSpread(0.2); // Controls blurriness (0 for sharp, 1 for very blurry)
        shadow.setColor(Color.rgb(80, 80, 80)); // Set shadow color

        // Apply the shadow effect to the button
        expandButton.setEffect(shadow);
        
        expandButton.setTextFill(Color.WHITE);
        VBox.setMargin(expandButton, new javafx.geometry.Insets(10, 0, 0, 0));
        
        
        optionsContainer = new EntriesVBox(DROP_WIDTH);
        VBox.setMargin(optionsContainer, new javafx.geometry.Insets(10, 0, 0, WIDTH - DROP_WIDTH));
        optionsContainer.setVisible(false);
        optionsContainer.addEntryField("Mass :");
        optionsContainer.addEntryField("Radius :");
        optionsContainer.add3DEntryField("Position :", "X :", "Y :", "Z :");
        optionsContainer.add3DEntryField("Init Vel :", "X :", "Y :", "Z :");
        
        
        getChildren().addAll(expandButton);
        expandButton.setOnAction(event -> toggleOptions());
    }

    public void toggleOptions() {
        optionsContainer.setVisible(!optionsContainer.isVisible());
        if (optionsContainer.isVisible()) {
            getChildren().add(optionsContainer);
            expandButton.setText(this.planetName);
        } else {
            getChildren().remove(optionsContainer);
            expandButton.setText(this.planetName);
        }
    }
}