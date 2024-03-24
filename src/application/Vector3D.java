package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class Vector3D extends Group {
	
	private Cylinder cylinder;
	private Cone cone;
	private double len;
	private double angle;
	
	public Vector3D(Point3D  start, Point3D end) {
		len = start.distance(end);
		angle = start.angle(end);
		
		cone = new Cone(6, 0, 12);
		cylinder = new Cylinder(3, len - 12);
		cylinder.setTranslateY(len / 2);
		cylinder.setMaterial(new PhongMaterial(Color.BLUE));

		this.getChildren().addAll(cylinder, cone);
//		this.setTranslateX(start.getX() - end.getX());
//		this.setTranslateY(start.getY() - end.getY());
//		this.setTranslateZ(start.getZ() - end.getZ());
		this.setRotationAxis(start.subtract(end).normalize());
		this.setRotate(90);
		System.out.println(start.subtract(end).normalize() + "" + angle);
	}


}
