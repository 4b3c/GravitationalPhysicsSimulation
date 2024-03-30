package application;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

@FunctionalInterface
interface updateAction {
  void performUpdates(double newValue);
}

public class LinkedDouble {
	
	public double var;
	public TextField field;
	public updateAction action;
	
	public LinkedDouble(double value, TextField textField, updateAction updateFunction) {
		var = value;
		field = textField;
		action = updateFunction;
		
		addListener();
	}
	
	public double get() {
		return var;
	}
	
	public void set(double newValue) {
		var = newValue;
		field.setText("" + var);
	}

	public void addListener() {
		field.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {

			@Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
            	try {
            		double newValue = Double.parseDouble(field.getText() + change.getText());
            		var = newValue;
            		action.performUpdates(newValue);
            		return change;
            	} catch (NumberFormatException e) {
            		return null;
            	}
            }
        }));
	}
}
