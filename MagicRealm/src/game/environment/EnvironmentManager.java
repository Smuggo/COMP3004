package game.environment;

import java.awt.Dimension;
import java.util.ArrayList;

import config.Config;
import config.ImageMap;
import game.chit.ChitFactory;
import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;
import game.environment.hex.Hextile;
import game.environment.hex.XMLParser;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	private ChitFactory lChitFactory;
	private ArrayList<Hextile> hextiles;
	
	public EnvironmentManager(){
		
	}

	public Dimension createNewMap(ImageMap aImageMap){
		// Create hextiles logic
		hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap);
		
		// Attach hextile chits to hextiles		
		// Create hextile chits
		lChitFactory = new ChitFactory(hextiles);
		
		//lChitFactory.addChitsRandomly();
				
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
	
	public ChitFactory getChitFactory() {
		return lChitFactory;
	}
	
	public ArrayList<Hextile> getHextiles() {
		return hextiles;
	}
}
