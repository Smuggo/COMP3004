package game.entity;

import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.RoadwayType;

public class HeroFactory {
	// Initializes player characters
	private Map<String, Hero> characters = new HashMap<String, Hero>();

	public HeroFactory() {
		characters.put("Captain", new Hero("Captain", CharacterImageType.captainPage, CharacterImageType.captainChit, 
				new DwellingType[]{DwellingType.GUARD, DwellingType.HOUSE, DwellingType.INN}));
		characters.put("Swordsman", new Hero("Swordsman", CharacterImageType.swordsmanPage, CharacterImageType.swordsmanChit,
				new DwellingType[]{DwellingType.INN}));
		characters.put("Amazon", new Hero("Amazon", CharacterImageType.amazonPage, CharacterImageType.amazonChit,
				new DwellingType[]{DwellingType.INN}));
		characters.put("Dwarf", new Hero("Dwarf", CharacterImageType.dwarfPage, CharacterImageType.dwarfChit,
				new DwellingType[]{DwellingType.GUARD, DwellingType.INN}));
		characters.put("Elf", new Hero("Elf", CharacterImageType.elfPage, CharacterImageType.elfChit,
				new DwellingType[]{DwellingType.INN}));
		characters.put("Black Knight", new Hero("Black Knight", CharacterImageType.bKnightPage, CharacterImageType.bKnightChit,
				new DwellingType[]{DwellingType.INN}));
	}

	public Map<String, Hero> getCharacters() {
		return characters;
	}
	
	public void setHiddenRoadways(HexGrid aHexGrid){
		int lGridSize = aHexGrid.getRadius()+1;
		Map<String, Roadway> lTempMap = new HashMap<String, Roadway>();
		
		for(int y = 0; y <  lGridSize; y++){
			for(int x = 0; x < lGridSize; x++){
				if(aHexGrid.getHex(x, y) != null && aHexGrid.getHex(x, y).isActive()){
					for(int r = 0; r < aHexGrid.getHex(x, y).getHextile().getRoadways().size(); r++){
						if(aHexGrid.getHex(x, y).getHextile().getRoadways().get(r).getRoadwayType().equals(RoadwayType.HIDDEN_PATH) ||
								aHexGrid.getHex(x, y).getHextile().getRoadways().get(r).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE)){
							String lRoadwayString = aHexGrid.getHex(x, y).getHextile().getName() + 
									aHexGrid.getHex(x, y).getHextile().getRoadways().get(r).getHeadClearing() +
									aHexGrid.getHex(x, y).getHextile().getRoadways().get(r).getTailClearing();
							lTempMap.put(lRoadwayString, aHexGrid.getHex(x, y).getHextile().getRoadways().get(r));
						}
					}
				}
			}
		}
		characters.get("Captain").setHiddenRoadways(lTempMap);
		characters.get("Swordsman").setHiddenRoadways(lTempMap);
		characters.get("Amazon").setHiddenRoadways(lTempMap);
		characters.get("Dwarf").setHiddenRoadways(lTempMap);
		characters.get("Elf").setHiddenRoadways(lTempMap);
		characters.get("Black Knight").setHiddenRoadways(lTempMap);
	}
}
