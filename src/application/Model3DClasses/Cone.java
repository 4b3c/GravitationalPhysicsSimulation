package application.Model3DClasses;

import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

public class Cone extends Group {

	int rounds = 360;
	
	public Cone(int r1, int r2, int h, PhongMaterial material) {
	    Group cone = new Group();
	
	    float[] points = new float[rounds *12];
	    float[] textCoords = {
	            0.5f, 0,
	            0, 1,
	            1, 1
	    };
	    int[] faces = new int[rounds *12];
	
	    for(int i= 0; i<rounds; i++){
	        int index = i*12;
	        //0
	        points[index] = (float)Math.cos(Math.toRadians(i))*r2;
	        points[index+1] = (float)Math.sin(Math.toRadians(i))*r2;
	        points[index+2] = h/2;
	        //1
	        points[index+3] = (float)Math.cos(Math.toRadians(i))*r1;
	        points[index+4] = (float)Math.sin(Math.toRadians(i))*r1;
	        points[index+5] = -h/2;
	        //2
	        points[index+6] = (float)Math.cos(Math.toRadians(i+1))*r1;
	        points[index+7] = (float)Math.sin(Math.toRadians(i+1))*r1;
	        points[index+8] = -h/2;
	        //3
	        points[index+9] = (float)Math.cos(Math.toRadians(i+1))*r2;
	        points[index+10] = (float)Math.sin(Math.toRadians(i+1))*r2;
	        points[index+11] = h/2;        
	    }
	
	    for(int i = 0; i<rounds ; i++){
	        int index = i*12;
	        faces[index]=i*4;
	        faces[index+1]=0;
	        faces[index+2]=i*4+1;
	        faces[index+3]=1;
	        faces[index+4]=i*4+2;
	        faces[index+5]=2;
	
	        faces[index+6]=i*4;
	        faces[index+7]=0;
	        faces[index+8]=i*4+2;
	        faces[index+9]=1;
	        faces[index+10]=i*4+3;
	        faces[index+11]=2;
	    }
	
	    TriangleMesh mesh = new TriangleMesh();
	    mesh.getPoints().addAll(points);
	    mesh.getTexCoords().addAll(textCoords);
	    mesh.getFaces().addAll(faces);
	
	    Cylinder circle1 = new Cylinder(r1, 0.1);
	    circle1.setMaterial(material);
	    circle1.setTranslateZ( -h / 2);
	    circle1.setRotationAxis(Rotate.X_AXIS);
	    circle1.setRotate(90);
	
	    Cylinder circle2 = new Cylinder(r2, 0.1);
	    circle2.setMaterial(material);
	    circle2.setTranslateZ( h / 2);
	    circle2.setRotationAxis(Rotate.X_AXIS);
	    circle2.setRotate(90);
	
	
	    MeshView meshView = new MeshView();
	    meshView.setMesh(mesh);
	    meshView.setMaterial(material);
	    //meshView.setDrawMode(DrawMode.LINE);
	    cone.getChildren().addAll(meshView);
	    Rotate rt1 = new Rotate(90, Rotate.X_AXIS);
	    cone.getTransforms().add(rt1);
	    
	    Cylinder cylinder = new Cylinder(r1, 1);
	    cylinder.setTranslateY(h/2);
	    cylinder.setMaterial(material);
	    getChildren().addAll(cone, cylinder);
	}
}