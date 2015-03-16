package game.entity;

import game.chit.ActionChitFactory;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;

import java.util.HashMap;
import java.util.Map;

import config.Config.CharacterImageType;
import config.Config.DwellingType;

public class HeroFactory {
	// Initializes player characters
	private Map<String, Hero> characters = new HashMap<String, Hero>();

	public HeroFactory(ActionChitFactory aChitFactory) {
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
		
		characters.get("Swordsman").setActionChits(aChitFactory.getSwordsmanChits());
		characters.get("Dwarf").setActionChits(aChitFactory.getDwarfChits());
		characters.get("Black Knight").setActionChits(aChitFactory.getBKnightChits());
		characters.get("Amazon").setActionChits(aChitFactory.getAmazonChits());
		characters.get("Captain").setActionChits(aChitFactory.getCaptainChits());
	}

	public Map<String, Hero> getCharacters() {
		return characters;
	}
}
