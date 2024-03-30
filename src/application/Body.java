package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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
	
	private Vector3D accelerationVector;
	private Vector3D forceVector;
	
	public BodyUI ui;
	
	
	public Body(String name, double radius, double mass, Color color) {
		this.name = name;
		planet = new Sphere(0);
		planet.setMaterial(new PhongMaterial(color));
		
		velocityVector = new Vector3D(0, 0, 0, Color.RED);
		ui = new BodyUI(this.name);
		
		radiusField = ui.addEntryField("Radius :");
		updateAction radiusUpdate = (newValue) -> radiusChanged();
		this.radius = new LinkedDouble(radius, radiusField, radiusUpdate);
		radiusField.setText("" + this.radius.get());
		
		massField = ui.addEntryField("Mass :");
		updateAction massUpdate = (newValue) -> massChanged();
		this.mass = new LinkedDouble(mass, massField, massUpdate);
		massField.setText("" + this.mass.get());
		
		
		posField = ui.add3DEntryField("Position :", "X :", "Y :", "Z :");
		velField = ui.add3DEntryField("Velocity :", "X :", "Y :", "Z :");
		updateAction posUpdate = (newValue) -> posChanged();
		updateAction velUpdate = (newValue) -> velChanged();
		for (int i = 0; i < 3; i++) {
			this.pos[i] = new LinkedDouble(0, posField[i], posUpdate);
			this.vel[i] = new LinkedDouble(0, velField[i], velUpdate);
		}
		for (int i = 0; i < 3; i++) {
			posField[i].setText("0.0");
			velField[i].setText("0.0");
		}
		
		forceVector = new Vector3D(0, 0, 0, Color.BLUE);
		accelerationVector = new Vector3D(0, 0, 0, Color.BLUE);
		
		this.getChildren().addAll(planet, velocityVector, forceVector, accelerationVector);
		
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
		pos[2].set(z);
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
		vel[2].set(z);
		velChanged();
	}
	
	private void velChanged() {
		velocityVector.updateEnd(vel[0].get(), vel[1].get(), vel[2].get());
	}
	
	public void calculateForce(Body otherPlanet) {
		final double G = 0.0000006674;
		Point3D ourPosition = new Point3D(getPos()[0], getPos()[1], getPos()[2]);
		Point3D theirPosition = new Point3D(otherPlanet.getPos()[0], otherPlanet.getPos()[1], otherPlanet.getPos()[2]);
			
		Point3D dist = theirPosition.subtract(ourPosition);
		Point3D distNorm = dist.normalize();
		double distMag = dist.magnitude();
		double forceMag = (G * getMass() * otherPlanet.getMass()) / (distMag * distMag);
			
		forceVector.addVector(forceMag * distNorm.getX(), forceMag * distNorm.getY(), forceMag * distNorm.getZ());
		accelerationVector.updateEnd(0, 0, 0);
	}
	
	public void calculateAcceleration() {
		accelerationVector.addVector(forceVector.getX() / mass.get(), forceVector.getY() / mass.get(), forceVector.getZ() / mass.get());
		forceVector.updateEnd(0, 0, 0);
	}
	
	public void calculateVelocity() {
		if (this.name == "Moon") {
			System.out.println(this.name + " V: " + vel[0].get() + ", " + vel[1].get() + ", " + vel[2].get());
			System.out.println(this.name + " A: " + accelerationVector.getX() + ", " + accelerationVector.getY() + ", " + accelerationVector.getZ());
			
		}
		this.setVel(vel[0].get() + accelerationVector.getX(), vel[1].get() + accelerationVector.getY(), vel[2].get() + accelerationVector.getZ());
		if (this.name == "Moon") {
			System.out.println(this.name + " A: " + accelerationVector + " NV: " + velocityVector);
		}
	}
	
	public void calculatePosition() {
		this.setPos(pos[0].get() + velocityVector.getX(), pos[1].get() + velocityVector.getY(), pos[2].get() + velocityVector.getZ());
	}
	
	public void timeTick() {
		calculateAcceleration();
		calculateVelocity();
		calculatePosition();
	}
 
}
