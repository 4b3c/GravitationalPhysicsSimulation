package application;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class LinkedDouble {
	
	private double var;
	private whatToDoWithIt updFun;
	
	public LinkedDouble(double value, whatToDoWithIt updateFunction) {
		var = value;
		updFun = updateFunction;
	}

	public double get() {
		return var;
	}
	
	public void set(Double newValue) {
		var = newValue;
		updFun.perform(newValue);
	}
	
	public void addListener(TextField field) {
		field.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {

			@Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
            	try {
            		double newValue = Double.parseDouble(field.getText() + change.getText());
            		set(newValue);
            		return change;
            	} catch (NumberFormatException e) {
            		return null;
            	}
            }
        }));
	}
}
