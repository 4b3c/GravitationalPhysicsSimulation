package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

public class Vector3D extends Group {
	
	private Cylinder cylinder;
	private Cone cone;
	private Sphere sphere;
	
	private double len;
	private Point3D diff;
	private Point3D diffn;
	
	public Vector3D(double x, double y, double z, Color color) {
		Point3D start = new Point3D(0, 0, 0);
		Point3D end = new Point3D(x, y, z);
		PhongMaterial mat = new PhongMaterial(color);
		
		
		len = end.distance(start);
		diff = end.subtract(start);
		diff = new Point3D(diff.getX(), -diff.getY(), diff.getZ());
		diffn = diff.normalize();
		
		sphere = new Sphere(0);
		sphere.setTranslateY(len + 3);
		
		cone = new Cone(6, 0, 12, mat);
		cone.setTranslateY(-len + 3);
		
		cylinder = new Cylinder(3, len - 12);
		cylinder.setTranslateY((-len / 2) + 3);
		cylinder.setMaterial(mat);

		this.getChildren().addAll(cylinder, cone, sphere);
		this.setRotationAxis(diffn.crossProduct(new Point3D(0, 1, 0)));
		this.setRotate(Math.acos(diffn.getY()) * 180 / Math.PI);
		
		this.setTranslateX(start.getX());
		this.setTranslateY(start.getY());
		this.setTranslateZ(start.getZ());
	
	}
	
	public void updateEnd(double x, double y, double z) {
		Point3D start = new Point3D(0, 0, 0);
		Point3D end = new Point3D(x, y, z);
		
		len = end.distance(start);
		diff = end.subtract(start);
		diff = new Point3D(diff.getX(), -diff.getY(), diff.getZ());
		diffn = diff.normalize();
		
		sphere.setTranslateY(len + 3);
		cone.setTranslateY(-len + 3);
		cylinder.setHeight(len - 12);
		cylinder.setTranslateY((-len / 2) + 3);

		this.setRotationAxis(diffn.crossProduct(new Point3D(0, 1, 0)));
		this.setRotate(Math.acos(diffn.getY()) * 180 / Math.PI);
		
		this.setTranslateX(start.getX());
		this.setTranslateY(start.getY());
		this.setTranslateZ(start.getZ());
	}


}
