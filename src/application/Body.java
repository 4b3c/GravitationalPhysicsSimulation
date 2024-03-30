package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;





public class Body extends Group{
	
	private String name;
	
	private LinkedDouble mass;
	private TextField massField;
	
	private LinkedDouble radius;
	private TextField radiusField;
	
	private LinkedDouble[] pos = new LinkedDouble[3];
	private TextField[] posField;
	private Sphere planet;
	
	private LinkedDouble[] vel = new LinkedDouble[3];
	private TextField[] velField;
	private Vector3D velocityVector;
	
	private Vector3D acc;
	private Vector3D force;
	public BodyUI ui;
	
	
	public Body(String name, double radius, double mass, Point3D position, Point3D velocity) {
		this.name = name;
		ui = new BodyUI(this.name);
		
		radiusField = ui.addEntryField("Radius :");
		updateAction radiusUpdate = (newValue) -> radiusChanged();
		this.radius = new LinkedDouble(radius, radiusField, radiusUpdate);
		
		massField = ui.addEntryField("Mass :");
		updateAction massUpdate = (newValue) -> massChanged();
		this.mass = new LinkedDouble(mass, massField, massUpdate);
		
		posField = ui.add3DEntryField("Position :", "X :", "Y :", "Z :");
		updateAction posUpdate = (newValue) -> posChanged();
		this.pos[0] = new LinkedDouble(position.getX(), posField[0], posUpdate);
		this.pos[1] = new LinkedDouble(position.getY(), posField[1], posUpdate);
		this.pos[2] = new LinkedDouble(position.getZ(), posField[2], posUpdate);
		planet = new Sphere(this.radius.get());
		
		velField = ui.add3DEntryField("Velocity :", "X :", "Y :", "Z :");
		updateAction velUpdate = (newValue) -> velChanged();
		this.vel[0] = new LinkedDouble(velocity.getX(), velField[0], velUpdate);
		this.vel[1] = new LinkedDouble(velocity.getY(), velField[1], velUpdate);
		this.vel[2] = new LinkedDouble(velocity.getZ(), velField[2], velUpdate);
		velocityVector = new Vector3D(vel[0].get(), vel[1].get(), vel[2].get(), Color.RED);
		
		
		
		this.getChildren().addAll(planet, velocityVector);
		
        
	}
	
	public void setRadius(double newValue) {
		radius.set(newValue);
		radiusChanged();
	}
		
	public double getRadius() {
		return this.radius.get();
	}
	
	private void radiusChanged() {
		planet.setRadius(radius.get());
	}
	
	public void setMass(double newValue) {
		mass.set(newValue);
	}
	
	public double getMass() {
		return this.mass.get();
	}
	
	private void massChanged() {
		
	}
	
	public void setPos(double x, double y, double z) {
		pos[0].set(x);
		pos[1].set(y);
		pos[1].set(z);
		posChanged();
	}
	
	public double[] getPos() {
		double[] position = {pos[0].get(), pos[1].get(), pos[2].get()};
		return position;
	}
	
	private void posChanged() {
		setTranslateX(pos[0].get());
		setTranslateY(pos[1].get());
		setTranslateZ(pos[2].get());
	}
	
	public void setVel(double x, double y, double z) {
		vel[0].set(x);
		vel[1].set(y);
		vel[1].set(z);
		velChanged();
	}
	
	public double[] velPos() {
		double[] velocity = {vel[0].get(), vel[1].get(), vel[2].get()};
		return velocity;
	}
	
	private void velChanged() {
		velocityVector.updateEnd(vel[0].get(), vel[1].get(), vel[2].get());
	}
 

}
