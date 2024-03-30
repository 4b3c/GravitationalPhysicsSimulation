package application;

import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
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
		System.out.println("WOWWEE  " + var + "  " + newValue);
	}

	public void addListener() {
	    field.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (newValue == null || newValue.isEmpty()) {
	                return; // Handle empty field if needed
	            }

	            try {
	                double newDouble = Double.parseDouble(newValue);
	            	var = newDouble;
	            	action.performUpdates(newDouble);
	            	System.out.println("passed");
	            } catch (NumberFormatException e) {
	                // Revert the text change to the previous value
	                field.setText(oldValue);
	            }
	        }
	    });
	}
	

}
