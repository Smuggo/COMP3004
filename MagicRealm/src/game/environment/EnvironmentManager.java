package game.environment;

import java.awt.Dimension;

import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	
	public EnvironmentManager(){
		
	}

	public Dimension createNewMap(){
		HexGrid lNewHexGrid = HexGridFactory.newHexGrid();
		setHexGrid(lNewHexGrid);
		return lHexGrid.getCanvasSize();
	}

	public HexGrid getHexGrid() {
		return lHexGrid;
	}

	public void setHexGrid(HexGrid aHexGrid) {
		lHexGrid = aHexGrid;
	}
	
	
	
}
