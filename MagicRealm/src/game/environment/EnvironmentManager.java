package game.environment;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Map;

import model.ViewModel;
import config.Config;
import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.NativeGroup;
import config.ImageMap;
import config.Config.HextileType;
import game.chit.ChitFactory;
//import game.dwelling.DwellingFactory;
import game.entity.Monster;
import game.entity.MonsterFactory;
import game.entity.Native;
import game.entity.NativeFactory;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.environment.hex.HexGridFactory;
import game.environment.hex.Hextile;
import game.environment.hex.Roadway;
import game.environment.hex.XMLParser;

public class EnvironmentManager {

	private HexGrid lHexGrid;
	private ChitFactory lChitFactory;
	//private DwellingFactory lDwellingFactory;
	private ArrayList<Hextile> hextiles;
	private ViewModel lModel;
	
	public EnvironmentManager(ViewModel aModel) {
		lModel = aModel;
	}
	
	/*public Dimension createNewMap(ImageMap aImageMap){
		// Create hextiles logic
		hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap);
	}*/
	
	
	public Dimension createNewMap(ImageMap aImageMap, Map<String, Roadway> aHiddenRoadways){
		
		// Set hextiles logic
		hextiles = XMLParser.newGameHexs("HexTiles.xml", aImageMap, aHiddenRoadways);
		
		// Create hextile chits
		lChitFactory = new ChitFactory(hextiles, lModel);
				
		
		//HexGrid lNewHexGrid = HexGridFactory.newHexGrid(hextiles);
		lHexGrid = HexGridFactory.newHexGrid(hextiles);
		System.out.println("lHexGrid address = " + lHexGrid);

		return lHexGrid.getCanvasSize();
	}
	
	// 3.6 REVEALING DWELLINGS //
	public void setDwellingTypesAndGhosts() {
		MonsterFactory mF = new MonsterFactory();
		ArrayList<Monster> ghosts = mF.createTwoGhosts();
		
		// Create Native Groups Based on Dwellings
		NativeFactory nF = new NativeFactory();
		
		for (int i = 0; i < hextiles.size(); i++) {
			if (hextiles.get(i).getHextileType() == HextileType.VALLEY) {
				if (hextiles.get(i).getWarningChit().getName() == "DANK V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.CHAPEL);
					hextiles.get(i).getClearing(5).setNativeGroup(nF.getNativeGroup(NativeGroup.ORDER));
				}
				else if (hextiles.get(i).getWarningChit().getName() == "RUINS V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.GUARD);
					hextiles.get(i).getClearing(5).setNativeGroup(nF.getNativeGroup(NativeGroup.GUARD));
				}
				else if (hextiles.get(i).getWarningChit().getName() == "SMOKE V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.HOUSE);
					hextiles.get(i).getClearing(5).getOwnedHextile().setContainsDwelling(true);
					hextiles.get(i).getClearing(5).setNativeGroup(nF.getNativeGroup(NativeGroup.SOLDIERS));
				}
				else if (hextiles.get(i).getWarningChit().getName() == "STINK V") {
					hextiles.get(i).getClearing(5).setDwellingType(DwellingType.INN);
					hextiles.get(i).getClearing(5).setNativeGroup(nF.getNativeGroup(NativeGroup.ROGUES));
				}
				else if (hextiles.get(i).getWarningChit().getName() == "BONES V") {
					hextiles.get(i).getClearing(5).addMonsters(ghosts);
					for(int j = 0; j < ghosts.size(); j++){
						ghosts.get(j).setClearing(hextiles.get(i).getClearing(5));
						lHexGrid.addMonster(ghosts.get(j));
					}
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

	public void randomlyGenerateMap() {
		// TODO Auto-generated method stub
		//System.out.println(hextiles);
		
		// Remove all hextile to hextile roadways because the XML connect them before anything
		//breakHextileToHextilePaths();
		
		// Clear all positions
		//clearXandYLocations();
		
		// Create new hextile positions, rotations, and hextile-to-hextile roadways
		//placeHextiles();
	}
	
	public void breakHextileToHextilePaths() {
		// For all clearings in a hextile
		for(int i = 0; i < hextiles.size(); i++) {
			for(int j = 0; j < hextiles.get(i).getRoadways().size(); j++) {
				// Check if any roadways connects to a different hextile
				if (!hextiles.get(i).getRoadways().get(j).getInterconnected()) {
					hextiles.get(i).getRoadways().get(j).setTailClearing(null);
				}				
			}
		}
	}
	
	public void clearXandYLocations() {
		// For all clearings in a hextile
		for(int i = 0; i < hextiles.size(); i++) {
			if (hextiles.get(i).getName().equals("Borderland")) {
				hextiles.get(i).setXLocation(0);
				hextiles.get(i).setYLocation(0);
			}
			
			// Place the other so far off the board they will not cause problems hopefully
			else {
				hextiles.get(i).setXLocation(-10);
				hextiles.get(i).setYLocation(-10);
			}
			
			lHexGrid = HexGridFactory.newHexGrid(hextiles);
		}
	}
	
	// This is going to be challenging
	public void placeHextiles() {
		ArrayList<Integer[]> possibleLocations = new ArrayList<Integer[]>();
		
		// First location is special
		Integer location[] = new Integer[2];
		location[0] = 2;
		location[1] = 3;
		possibleLocations.add(location);
	}
}
