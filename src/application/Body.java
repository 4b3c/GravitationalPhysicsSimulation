package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.shape.Sphere;


@FunctionalInterface
interface whatToDoWithIt {
  void perform(double newValue);
}


public class Body extends Group{
	
	private String name;
	private double mass;
	private LinkedDouble radius;
	private Point3D pos;
	private Vector3D vel;
	private Vector3D acc;
	private Vector3D force;
	private Sphere sphere;
	public BodyUI ui;
	
	
	public Body(String name, double radius, double mass, Point3D pos, Vector3D velocity) {
		this.name = name;
		
		whatToDoWithIt radiusUpdate = (newValue) -> sphere.setRadius(newValue);
		this.radius = new LinkedDouble(radius, radiusUpdate);
		
		this.mass = mass;
		this.pos = pos;
		
		sphere = new Sphere(this.radius.get());
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
        
        this.radius.addListener(radiusField);
        
		
	}
	
	public void setRadius(double newValue) {
		this.radius.set(newValue);
		sphere.setRadius(newValue);
		
	}
	
	public double getRadius() {
		return this.radius.get();
	}

}
