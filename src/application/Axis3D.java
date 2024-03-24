package application;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;


public class Axis3D extends Group{
    
    private Cylinder cylinderX;
    private Cylinder cylinderY;
    private Cylinder cylinderZ;
    
    public Axis3D(double radius, double height, Color color) {
    	PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        
        cylinderX = new Cylinder(radius, height);
        cylinderY = new Cylinder(radius, height);
        cylinderZ = new Cylinder(radius, height);

        cylinderX.setRotationAxis(new Point3D(1, 0, 0));
        cylinderY.setRotationAxis(new Point3D(0, 1, 0));
        cylinderZ.setRotationAxis(new Point3D(0, 0, 1));
        
        cylinderX.setRotate(90);
        cylinderY.setRotate(90);
        cylinderZ.setRotate(90);
        
        cylinderX.setMaterial(material);
        cylinderY.setMaterial(material);
        cylinderZ.setMaterial(material);
        
        
        
        this.getChildren().addAll(cylinderX, cylinderY, cylinderZ);
    }
}
