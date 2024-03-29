package application;

import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
        TextField massField = optionsContainer.addEntryField("Mass :");
        TextField radiusField = optionsContainer.addEntryField("Radius :");
        TextField[] posField = optionsContainer.add3DEntryField("Position :", "X :", "Y :", "Z :");
        TextField[] velField = optionsContainer.add3DEntryField("Velocity :", "X :", "Y :", "Z :");
        
        
        getChildren().addAll(expandButton);
        expandButton.button.setOnAction(event -> toggleOptions());
        
        massField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Text changed: " + newValue);
            }
        });
        massField.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                String text = change.getText();
                if (!text.matches("[.0-9]")) {
                    return null;
                }
                return change;
            }
        }));
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
} 
