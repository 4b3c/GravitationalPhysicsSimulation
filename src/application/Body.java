package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Sphere;

public class Body extends Group{
	
	private String name;
	private double radius;
	private double mass;
	private Point3D pos;
	private Vector3D vel;
	private Vector3D acc;
	private Vector3D force;
	private Sphere sphere;
	private BodyUI ui;
	
	public Body(String name, double radius, double mass, Point3D pos, Vector3D velocity) {
		this.name = name;
		this.radius = radius;
		this.mass = mass;
		this.pos = pos;
		
		sphere = new Sphere(radius);
		vel = velocity;
		
		this.getChildren().addAll(sphere, vel);
		
	}
	
	public void create3DSelf() {
		
	}
	
	public void createUISelf() {
		ui = new BodyUI(this.name);
	}

}
