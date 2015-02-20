package game.entity;

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
		//for(int i = 0; i < aRoadways.size(); i++){
		//	if(aRoadways.get(i).getRoadwayType().equals(RoadwayType.HIDDEN_PATH) || aRoadways.get(i).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE))
		//		System.out.println(i);
		//}
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
}
