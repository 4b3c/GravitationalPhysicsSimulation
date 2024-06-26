package models;

import application.PhysicsSimulation;
import application.UpdateAction;
import gui.BodyUI;
import gui.LinkedDouble;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Body extends Group {
	
	public String name;
	
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
	
	private double[] acc = {0, 0, 0};
	private Vector3D accelerationVector;
	
	private double[] force = {0, 0, 0};
	private Vector3D forceVector;
	
	private Color color;
	public BodyUI ui;
	
	
	public Body(String name, double radius, double mass) {
		this.name = name;
		planet = new Sphere(0);
		
		velocityVector = new Vector3D(0, 0, 0, Color.RED);
		ui = new BodyUI(this.name);
		
		radiusField = ui.addEntryField("Radius :");
		UpdateAction radiusUpdate = (newValue) -> radiusChanged();
		this.radius = new LinkedDouble(radius, radiusField, radiusUpdate);
		radiusField.setText("" + this.radius.get());
		
		massField = ui.addEntryField("Mass :");
		UpdateAction massUpdate = (newValue) -> massChanged();
		this.mass = new LinkedDouble(mass, massField, massUpdate);
		massField.setText("" + this.mass.get());
		
		
		posField = ui.add3DEntryField("Position :", "X :", "Y :", "Z :");
		velField = ui.add3DEntryField("Velocity :", "X :", "Y :", "Z :");
		UpdateAction posUpdate = (newValue) -> posChanged();
		UpdateAction velUpdate = (newValue) -> velChanged();
		for (int i = 0; i < 3; i++) {
			this.pos[i] = new LinkedDouble(0, posField[i], posUpdate);
			this.vel[i] = new LinkedDouble(0, velField[i], velUpdate);
		}
		for (int i = 0; i < 3; i++) {
			posField[i].setText("0.0");
			velField[i].setText("0.0");
		}
		
		CheckBox showVectors = ui.addCheckBox("Show Vectors :");
		
		ChangeListener<Boolean> vectorBoxListener = (observable, oldValue, newValue) -> {
			if (newValue == false) {
				getChildren().remove(velocityVector);
				getChildren().remove(accelerationVector);
			} else {
				getChildren().add(velocityVector);
				getChildren().add(accelerationVector);
			}
		};
		
		showVectors.selectedProperty().addListener(vectorBoxListener);
		showVectors.setSelected(false);
		
		forceVector = new Vector3D(0, 0, 0, Color.BLUE);
		accelerationVector = new Vector3D(0, 0, 0, Color.BLUE);
		
		this.getChildren().addAll(planet);
		
	}
	
	public void setColor(Color color) {
		this.color = color;
		planet.setMaterial(new PhongMaterial(color));
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
	
	public void addToPos(double x, double y, double z) {
		setPos(pos[0].get() + x, pos[1].get() + y, pos[2].get() + z);
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
	
	public double[] getVel() {
		double[] velocity = {vel[0].get(), vel[1].get(), vel[2].get()};
		return velocity; 
	}
	
	public void setVel(double x, double y, double z) {
		vel[0].set(x);
		vel[1].set(y);
		vel[2].set(z);
		velChanged();
	}
	
	public void addToVel(double x, double y, double z) {
		setVel(vel[0].get() + x, vel[1].get() + y, vel[2].get() + z);
	}
	
	private void velChanged() {
		velocityVector.updateEnd(vel[0].get(), vel[1].get(), vel[2].get());
		velocityVector.scaleBy(100);
	}
	
	public double[] getAcc() {
		return acc;
	}
	
	public void setAcc(double x, double y, double z) {
		acc[0] = x;
		acc[1] = y;
		acc[2] = z;
		accChanged();
	}
	
	public void addToAcc(double x, double y, double z) {
		setAcc(acc[0] + x, acc[1] + y, acc[2] + z);
	}
	
	private void accChanged() {
		accelerationVector.updateEnd(acc[0], acc[1], acc[2]);
		accelerationVector.scaleBy(3000);
	}
	
	public double[] getForce() {
		return force;
	}
	
	public void setForce(double x, double y, double z) {
		force[0] = x;
		force[1] = y;
		force[2] = z;
		forceChanged();
	}
	
	public void addToForce(double x, double y, double z) {
		setForce(force[0] + x, force[1] + y, force[2] + z);
	}
	
	private void forceChanged() {
		forceVector.updateEnd(force[0], force[1], force[2]);
		forceVector.scaleBy(3000);
	}
	
	
	
	public void calculateForce(Body otherPlanet) {
		Point3D ourPosition = new Point3D(getPos()[0], getPos()[1], getPos()[2]);
		Point3D theirPosition = new Point3D(otherPlanet.getPos()[0], otherPlanet.getPos()[1], otherPlanet.getPos()[2]);
			
		Point3D dist = theirPosition.subtract(ourPosition);
		Point3D distNorm = dist.normalize();
		double distMag = dist.magnitude() * PhysicsSimulation.SCALE;
		double forceMag = (PhysicsSimulation.GRAVITATIONAL_CONSTANT * getMass() * otherPlanet.getMass()) / (distMag * distMag);
		
		addToForce(forceMag * distNorm.getX(), forceMag * distNorm.getY(), forceMag * distNorm.getZ());
		setAcc(0, 0, 0);
	}
	
	public void calculateAcceleration() {
		addToAcc(force[0] / mass.get(), force[1] / mass.get(), force[2] / mass.get());
		setForce(0, 0, 0);
	}
	
	public void calculateVelocity() {
		addToVel(acc[0], acc[1], acc[2]);
	}
	
	public void calculatePosition() {
		addToPos(vel[0].get(), vel[1].get(), vel[2].get());
	}
	
	public void timeTick() {
		calculateAcceleration();
		calculateVelocity();
		calculatePosition();
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String toString() {
		return String.format("%s: {Mass: %.2f, Radius: %.2f, Pos: %.2f, %.2f, %.2f}", 
				this.name, this.mass.get(), this.radius.get(), pos[0].get(), pos[1].get(), pos[2].get());
		
	}
 
}
