package game.environment;

import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	
	public EnvironmentManager(){
		
	}

	public void createNewMap(){
		setHexGrid(HexGridFactory.newHexGrid());
	}

	public HexGrid getHexGrid() {
		return lHexGrid;
	}

	public void setHexGrid(HexGrid aHexGrid) {
		lHexGrid = aHexGrid;
	}
	
	
	
}
