package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Body;
import models.Trail;

public class SolarSystem {
	
	private Scanner scnr;
	private ArrayList<Body> planets = new ArrayList<Body>();
	public ArrayList<Trail> planetTrails = new ArrayList<Trail>();
	
	public SolarSystem(String filename) {
		try {
			scnr = new Scanner(new FileReader("src/stablesystems/" + filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (scnr.hasNext()) {
			Body planet = new Body(scnr.next(), scnr.nextDouble(), Double.parseDouble(scnr.next()));
			scnr.next(); // Skip the text "Color:"
			planet.setColor(Color.rgb(scnr.nextInt(), scnr.nextInt(), scnr.nextInt()));
			scnr.next(); // Skip the text "Position:"
			planet.setPos(scnr.nextDouble(), scnr.nextDouble(), scnr.nextDouble());
			scnr.next(); // Skip the text "Velocity:"
			planet.setVel(scnr.nextDouble(), scnr.nextDouble(), scnr.nextDouble());
			planets.add(planet);
			System.out.println(planet);
		}
		
		calculateAllForces();
		for (Body planet : planets)
			planet.calculateAcceleration();
	}
	
	public void addPlanetUIs(VBox vbox) {
		for (Body planet : planets)
			vbox.getChildren().add(planet.ui);
	}
	
	public void addToGroup(Group group) {
		for (Body planet : planets) {
			group.getChildren().add(planet);
			planetTrails.add(new Trail(planet, group));
		}
	}
	
	public void calculateAllForces() {
		for (Body planet : planets) {
			for (Body otherPlanet : planets) {
				if (planet != otherPlanet)
					planet.calculateForce(otherPlanet);
			}
		}
		
	}
	
	public boolean timeTick() {
		calculateAllForces();
		for (Body planet : planets)
			planet.timeTick();
		for (Trail trail : planetTrails) {
			if (trail.timeTick() && trail.planet.name.equals("Moon")) {
				return true;
			}
		}
		return false;
	}

	public void changeMoon(double newVelocity) {
		for (Body planet : planets) {
			if (planet.name.equals("Moon")) {
				planet.setVel(newVelocity, 0, 0);
				planet.setPos(0, 0, -160);
			}
		}
		for (Trail trail : planetTrails) {
			if (trail.planet.name.equals("Moon")) {
				trail.emptyList();
			}
		}
	}
	
	public String toString() {
		String output = "";
		for (Body planet : planets)
			output += planet.toString() + "\n";
		return output;
	}
}
