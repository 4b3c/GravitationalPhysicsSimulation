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
	}

	public void addListener() {
	    field.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }

	            try {
	                double newDouble = Double.parseDouble(newValue);
	            	var = newDouble;
	            	action.performUpdates(newDouble);
	            } catch (NumberFormatException e) {
	                field.setText(oldValue);
	            }
	        }
	    });
	}
	

}
