package models;

import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Trail {

	  private LinkedList<Sphere> queue;
	  private Body planet;
	  private Group group;
	  private int trailLength = 500;
	  private int markSeparation = 3;
	  private int timeVar = 0;

	  public Trail(Body planet, Group group) {
	    this.queue = new LinkedList<>();
	    this.planet = planet;
	    this.group = group;
	  }

	  public void enqueue(Sphere sphere) {
	    queue.addLast(sphere);
	  }

	  public Sphere dequeue() {
	    return queue.removeFirst();
	  }

	  public boolean isEmpty() {
	    return queue.isEmpty();
	  }

	  public Sphere peek() {
		  return queue.getFirst();
	  }
	  
	  public int size() {
		  return queue.size();
	  }
	  
	  public void addMark() {
		  Sphere mark = new Sphere(3);
		  mark.setMaterial(new PhongMaterial(planet.getColor()));
		  mark.setTranslateX(planet.getPos()[0]);
		  mark.setTranslateY(planet.getPos()[1]);
		  mark.setTranslateZ(planet.getPos()[2]);
		  group.getChildren().add(mark);
		  enqueue(mark);
		  if (size() > 10) {
			  if (Math.abs(queue.peek().getTranslateX() - planet.getPos()[0]) < 5) {
			    	if (Math.abs(queue.peek().getTranslateY() - planet.getPos()[1]) < 5) {
			    		if (Math.abs(queue.peek().getTranslateZ() - planet.getPos()[2]) < 5) {
			    			removeMark();
			    		}
			    	}
			    }
			  if (size() > trailLength) {
				  removeMark();  
			  }
		  }
	  }
	  
	  public void removeMark() {
		  group.getChildren().remove(dequeue());
	  }
	  
	  public void timeTick() {
		  timeVar++;
		  if (timeVar > markSeparation) {
			  timeVar = 0;
			  addMark();
		  }
	  }
	}
