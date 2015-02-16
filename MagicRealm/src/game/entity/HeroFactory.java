package game.entity;

import java.util.HashMap;
import java.util.Map;
import config.Config.ImageType;

public class HeroFactory {
	// Initializes player characters
	private Map<String, Hero> characters = new HashMap<String, Hero>();

	public HeroFactory() {
		characters.put("Captain", new Hero("Captain", ImageType.captainPage,
				ImageType.captainChit));
		characters.put("Swordsman", new Hero("Swordsman",
				ImageType.swordsmanPage, ImageType.swordsmanChit));
		characters.put("Amazon", new Hero("Amazon", ImageType.amazonPage,
				ImageType.amazonChit));
		characters.put("Dwarf", new Hero("Dwarf", ImageType.dwarfPage,
				ImageType.dwarfChit));
		characters.put("Elf", new Hero("Elf", ImageType.elfPage,
				ImageType.elfChit));
		characters.put("Black Knight", new Hero("Black Knight",
				ImageType.bKnightPage, ImageType.bKnightChit));
	}

	public Map<String, Hero> getCharacters() {
		return characters;
	}
}
