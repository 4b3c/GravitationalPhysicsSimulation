package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SolarSystem {
	
	public SolarSystem(String filename) throws FileNotFoundException {
		Scanner scnr = new Scanner(new FileReader(filename + ".txt"));
	}
}
