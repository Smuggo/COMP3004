package game.environment.hex;

import java.util.ArrayList;
import config.Config;

public class Clearing {
	ArrayList<Roadway> roadways;
	String identifier; //Abbreviation of Hex Tile Name + Number
	int number;
	Config.ClearingType clearingType;
	
	public Clearing() {
		roadways = new ArrayList<Roadway>();
	}
	
	public Clearing(String str, Config.ClearingType cT) {
		identifier = str;
		clearingType = cT;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void initialize(String abbreviation, int n, Config.ClearingType cT) {
		number = n;
		identifier = abbreviation + n;
		clearingType = cT;
	}
	
	public void addRoadway(Roadway roadway) {
		roadways.add(roadway);
	}
	
	public boolean neighbourTo(Clearing possibleNeighbour) {
		return true;
	}
	
	public void print() {
		System.out.println("My identifier is: " + identifier);
		System.out.println("My clearing type is: " + clearingType);
	}
}
