package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;

public class Body extends Group{
	
	private double radius;
	private double mass;
	private Point3D pos;
	private Vector3D vel;
	private Vector3D acc;
	private Vector3D force;
	
	public Body(double radius, double mass, Point3D pos) {
		this.radius = radius;
		this.mass = mass;
		this.pos = pos;
	}
}
