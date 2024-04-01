package application.Model3DClasses;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;


public class Axis3D extends Group{
   
	private PhongMaterial material = new PhongMaterial();;
    private Cylinder[] axes = new Cylinder[3];
    private Text[] textLabels = new Text[3];
    
    private double radius;
    private double height;
    
    public Axis3D(double radius, double height, Color color) {
        material.setDiffuseColor(color);
        this.radius = radius;
        this.height = height;
        
        createAxis(0, "X");
        createAxis(1, "Y");
        createAxis(2, "Z");
        
    }
    
    public void createAxis(int ind, String label) {
    	int[] rax = {0, 0, 0};
    	rax[ind] = 1;
    	
    	axes[ind] = new Cylinder(radius, height);
    	axes[ind].setRotationAxis(new Point3D(rax[0], rax[1], rax[2]));
    	axes[ind].setRotate(90);
    	axes[ind].setMaterial(material);
    	
    	textLabels[ind] = new Text(0, 0, label);
    	textLabels[ind].setScaleX(3);
    	textLabels[ind].setScaleY(3);
    	textLabels[ind].setTranslateX((((height / 2) + 20 ) * rax[0]) - 3);
    	textLabels[ind].setTranslateY(-(((height / 2) + 20 ) * rax[1]) + 3);
    	textLabels[ind].setTranslateZ(((height / 2) + 20 ) * rax[2]);
    	
    	if (ind == 2) {
    		textLabels[ind].setRotationAxis(Rotate.Y_AXIS);
    		textLabels[ind].setRotate(-90);
    	}
    	
    	this.getChildren().addAll(axes[ind], textLabels[ind]);
    }
    
}
