package game.environment;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Map;

import config.Config;
import config.Config.DwellingType;
import config.ImageMap;
import config.Config.HextileType;
import game.chit.ChitFactory;
import game.dwelling.DwellingFactory;
import game.entity.Monster;
import game.entity.MonsterFactory;
import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;
import game.environment.hex.Hextile;
import game.environment.hex.Roadway;
import game.environment.hex.XMLParser;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	private ChitFactory lChitFactory;
	private DwellingFactory lDwellingFactory;
	private ArrayList<Hextile> hextiles;
	
	public EnvironmentManager() {
		
	}
	
	/*public Dimension createNewMap(ImageMap aImageMap){
		// Create hextiles logic
		hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap);
	}*/
	
	
	public Dimension createNewMap(ImageMap aImageMap, Map<String, Roadway> aHiddenRoadways){
		
		// Set hextiles logic
		hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap, aHiddenRoadways);
		
		// Create hextile chits
		lChitFactory = new ChitFactory(hextiles);
				
		
		HexGrid lNewHexGrid = HexGridFactory.newHexGrid(hextiles);
		setHexGrid(lNewHexGrid);

		return lHexGrid.getCanvasSize();
	}
	
	// 3.6 REVEALING DWELLINGS //
	public void setDwellingTypesAndGhosts() {
		MonsterFactory mF = new MonsterFactory();
		ArrayList<Monster> ghosts = mF.createTwoGhosts();
		
		for (int i = 0; i < hextiles.size(); i++) {
			if (hextiles.get(i).getHextileType() == HextileType.VALLEY) {
				if (hextiles.get(i).getWarningChit().getName() == "DANK V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.CHAPEL);
				}
				else if (hextiles.get(i).getWarningChit().getName() == "RUINS V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.GUARD);
				}
				else if (hextiles.get(i).getWarningChit().getName() == "SMOKE V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.HOUSE);
				}
				else if (hextiles.get(i).getWarningChit().getName() == "STINK V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.INN);
				}
				else if (hextiles.get(i).getWarningChit().getName() == "BONES V") {
					hextiles.get(i).getClearing(5).addMonsters(ghosts);
				}
			}
		}
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
