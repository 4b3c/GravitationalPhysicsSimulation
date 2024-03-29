package application;

import java.util.function.UnaryOperator;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.shape.Sphere;

public class Body extends Group{
	
	private String name;
	private double[] radius = new double[1];
	private double mass;
	private Point3D pos;
	private Vector3D vel;
	private Vector3D acc;
	private Vector3D force;
	private Sphere sphere;
	public BodyUI ui;
	
	public Body(String name, double radius, double mass, Point3D pos, Vector3D velocity) {
		this.name = name;
		this.radius[0] = radius;
		this.mass = mass;
		this.pos = pos;
		
		sphere = new Sphere(this.radius[0]);
		vel = velocity;
		
		this.getChildren().addAll(sphere, vel);
		
	}
	
	public void create3DSelf() {
		
	}
	
	public void createUISelf() {
		ui = new BodyUI(this.name);
		TextField massField = ui.addEntryField("Mass :");
        TextField radiusField = ui.addEntryField("Radius :");
        TextField[] posField = ui.add3DEntryField("Position :", "X :", "Y :", "Z :");
        TextField[] velField = ui.add3DEntryField("Velocity :", "X :", "Y :", "Z :");
        
        
    	radiusField.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {

			@Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
            	try {
            		double newValue = Double.parseDouble(radiusField.getText() + change.getText());
            		setRadius(newValue);
            		return change;
            	} catch (NumberFormatException e) {
            		return null;
            	}
            }
        }));
		
	}
	
	public void setRadius(double newValue) {
		sphere.setRadius(newValue);
		
	}

}
