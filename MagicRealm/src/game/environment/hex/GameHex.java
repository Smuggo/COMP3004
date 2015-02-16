package game.environment.hex;
import config.Config;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameHex {
	
	ArrayList<Clearing> clearings;
	ArrayList<Roadway> roadways;
	String name;
	String abbreviation;
	int xLocation;
	int yLocation;
	int rotation; //in degrees
	boolean enchanted;
	private BufferedImage imageFile;
	
	
	public GameHex() {
		clearings = new ArrayList<Clearing>();
		roadways = new ArrayList<Roadway>();
	}
	
	public void initialize(String n, String a, BufferedImage i, int x, int y, int r, boolean e) {
		name = n;
		abbreviation = a;
		imageFile = i;
		xLocation = x;
		yLocation = y;
		rotation = r;
		enchanted = e;
	}
	
	public BufferedImage getTileImage(){
		return imageFile;
	}
	
	public void addClearing(Clearing clearing) {
		clearings.add(clearing);
	}
	
	public void addRoadway(Roadway roadway) {
		roadways.add(roadway);
	}
	
	public void connectTo(Hextile hexTile, Config.IncompleteRoadwayDirection relativePosition) {
		Config.IncompleteRoadwayDirection head = null;
		Config.IncompleteRoadwayDirection tail = null;
		
		Config.IncompleteRoadwayDirection directions[] = new Config.IncompleteRoadwayDirection[] { Config.IncompleteRoadwayDirection.TOP, Config.IncompleteRoadwayDirection.TOP_RIGHT, Config.IncompleteRoadwayDirection.BOTTOM_RIGHT, Config.IncompleteRoadwayDirection.BOTTOM, Config.IncompleteRoadwayDirection.BOTTOM_LEFT, Config.IncompleteRoadwayDirection.TOP_LEFT};
		Config.IncompleteRoadwayDirection relativePositionArray[] = new Config.IncompleteRoadwayDirection[directions.length];
		
		int startingLocation = -1;
		
		// Find relativePosition in direction array
		for (int i = 0; i < directions.length; i++) {
			if (directions[i] == relativePosition) {
				startingLocation = i;
				break;
			}
		}
		
		if (startingLocation == -1) {
			System.out.println("ERROR: startingLocation = -1");
		}
		
		// Create new array which starts in the middle of the direction array with all the same content
		for (int i = 0; i < relativePositionArray.length; i++) {
			relativePositionArray[i] = directions[(startingLocation + i) % 6];
		}
		
		switch (rotation) {
            case 0:  
            	head = relativePositionArray[0]; break;
            case 60:  
            	head = relativePositionArray[5]; break;
            case 120:
            	head = relativePositionArray[4]; break;
            case 180:
            	head = relativePositionArray[3]; break;
            case 240:
            	head = relativePositionArray[2]; break;
            case 300:
            	head = relativePositionArray[1]; break;
            default: System.out.println("ERROR: Function connectTo - switch(rotation) does not handle value" + rotation); break;
		}
		
		switch (hexTile.getRotation()) {
			case 0:  
	        	tail = relativePositionArray[3]; break;
	        case 60:  
	        	tail = relativePositionArray[2]; break;
	        case 120:
	        	tail = relativePositionArray[1]; break;
	        case 180:
	        	tail = relativePositionArray[0]; break;
	        case 240:
	        	tail = relativePositionArray[5]; break;
	        case 300:
	        	tail = relativePositionArray[4]; break;
	        default: System.out.println("ERROR: Function connectTo - switch(hexTile.getRotation()) does not handle value" + rotation);
		}
		
		//System.out.println("Connecting " + head + " to " + tail);
		Roadway headRoadway = this.findRoadway(head);
		Roadway tailRoadway = hexTile.findRoadway(tail);
		
		// The roadways do not line up
		if ((headRoadway != null) && (tailRoadway != null)) {
			headRoadway.setTailClearing(tailRoadway.getHeadClearing());
			tailRoadway.setTailClearing(headRoadway.getHeadClearing());
			
			headRoadway.print();
			tailRoadway.print();
		}
		else {
			System.out.println("Warning: Trying to connect hextiles with misaligned roadways");
		}
		
		
	}
	
	public static void checkForAdjacentHextiles(int x, int y, Hextile[][] gameboard) {
		System.out.println("x:" + x + " y: " + y);
		
		// Top and bottom require the same calculations whether x is even or odd
		// Check if there exist a hextile above the one we are trying to place
		if ((y > 0) && (gameboard[x][y - 1] != null)) {
			gameboard[x][y].connectTo(gameboard[x][y - 1], Config.IncompleteRoadwayDirection.TOP);
		}
		// Check if there exist a hextile below the one we are trying to place
		if ((y < 3) && (gameboard[x][y + 1] != null)) {
			gameboard[x][y].connectTo(gameboard[x][y + 1], Config.IncompleteRoadwayDirection.BOTTOM);
		}
		
		// If x is even we handle things differently
		if ((x % 2) == 0) {
			// Check if there exist a hextile above-right to the one we are trying to place
			if ((x < 3) && (y > 0) && (gameboard[x + 1][y - 1] != null)) {
				gameboard[x][y].connectTo(gameboard[x + 1][y - 1], Config.IncompleteRoadwayDirection.TOP_RIGHT);
			}
			// Check if there exist a hextile below-right to the one we are trying to place
			if ((x < 3) && (gameboard[x + 1][y] != null)) {
				gameboard[x][y].connectTo(gameboard[x + 1][y], Config.IncompleteRoadwayDirection.BOTTOM_RIGHT);
			}
			// Check if there exist a hextile below-left to the one we are trying to place
			if ((x > 0) && (gameboard[x - 1][y] != null)) {
				gameboard[x][y].connectTo(gameboard[x - 1][y], Config.IncompleteRoadwayDirection.BOTTOM_LEFT);
			}
			// Check if there exist a hextile above-left to the one we are trying to place
			if ((x > 0) && (y > 0) && (gameboard[x - 1][y - 1] != null)) {
				gameboard[x][y].connectTo(gameboard[x - 1][y - 1], Config.IncompleteRoadwayDirection.TOP_LEFT);
			}
		}
		// x is odd
		else {
			// Check if there exist a hextile above-right to the one we are trying to place
			if ((x < 3) && (gameboard[x + 1][y] != null)) {
				gameboard[x][y].connectTo(gameboard[x + 1][y], Config.IncompleteRoadwayDirection.TOP_RIGHT);
			}
			// Check if there exist a hextile below-right to the one we are trying to place
			if ((x < 3) && (y < 3) && (gameboard[x + 1][y + 1] != null)) {
				gameboard[x][y].connectTo(gameboard[x + 1][y + 1], Config.IncompleteRoadwayDirection.BOTTOM_RIGHT);
			}
			// Check if there exist a hextile below-left to the one we are trying to place
			if ((x > 0) && (y < 3) && (gameboard[x - 1][y + 1] != null)) {
				gameboard[x][y].connectTo(gameboard[x - 1][y + 1], Config.IncompleteRoadwayDirection.BOTTOM_LEFT);
			}
			// Check if there exist a hextile above-left to the one we are trying to place
			if ((x > 0) && (gameboard[x - 1][y] != null)) {
				gameboard[x][y].connectTo(gameboard[x - 1][y], Config.IncompleteRoadwayDirection.TOP_LEFT);
			}
		}
	}
	
	
	public Clearing getClearing(int num) {
		for (int i = 0; i < clearings.size(); i++) {
			if (clearings.get(i).getNumber() == num) {
				return clearings.get(i);
			}
		}
		return null;
	}
	
	public Roadway findRoadway(Config.IncompleteRoadwayDirection direction) {
		for (int i = 0; i < roadways.size(); i++) {
			if (roadways.get(i).getIncompleteRoadwayDirection() == direction) {
				//System.out.println("Roadway " + i + ": " + direction);
				return roadways.get(i);
			}
		}
		//System.out.println("ERROR: findRoadway " + direction);
		return null;
	}
	
	public int getXLocation() {
		return xLocation;
	}
	
	public int getYLocation() {
		return yLocation;
	}

	public int getRotation() {
		return rotation;
	}
	
	//public void print() {
		
		/*System.out.println("My clearings: ");
		for (int i = 0; i < clearings.size(); i++) {
			clearings.get(i).print();
		}*/
		/*System.out.println("My roadways: ");
		for (int i = 0; i < roadways.size(); i++) {
			roadways.get(i).print();
		}*/
		/*	System.out.println("My non-interconnected roadways: ");
		for (int i = 0; i < roadways.size(); i++) {
			roadways.get(i).printNonInterconnected();
		}
	}*/
	
	public void print() {
		System.out.println("My name is: " + name);
		System.out.println(imageFile);
	}
		
}