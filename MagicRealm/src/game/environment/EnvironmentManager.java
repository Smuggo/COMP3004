package game.environment;

import java.awt.Dimension;
import java.util.ArrayList;

import config.ImageMap;
import game.chit.ChitFactory;
import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;
import game.environment.hex.Hextile;
import game.environment.hex.Roadway;
import game.environment.hex.XMLParser;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	
	public EnvironmentManager(){
		
	}

	public Dimension createNewMap(ImageMap aImageMap){
		
		// Create hextiles logic
		ArrayList<Hextile> hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap);
		
		// Create hextile chits
		ChitFactory cF = new ChitFactory();
		
		// Attach hextile chits to hextiles
		cF.addChitsToHextiles(hextiles);
				
		HexGrid lNewHexGrid = HexGridFactory.newHexGrid(hextiles);
		setHexGrid(lNewHexGrid);
		return lHexGrid.getCanvasSize();
	}

	public HexGrid getHexGrid() {
		return lHexGrid;
	}

	public void setHexGrid(HexGrid aHexGrid) {
		lHexGrid = aHexGrid;
	}
	
	public ArrayList<Roadway> getRoadways(){
		return lHexGrid.getRoadways();
	}
}
