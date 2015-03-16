package game.entity;

import game.chit.ActionChitFactory;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;
import game.item.ArmourFactory;
import game.item.WeaponFactory;

import java.util.HashMap;
import java.util.Map;

import config.Config.ArmourType;
import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.WeaponType;

public class HeroFactory {
	// Initializes player characters
	private Map<String, Hero> characters = new HashMap<String, Hero>();

	public HeroFactory(ActionChitFactory aChitFactory, WeaponFactory aWeaponFactory, ArmourFactory aArmourFactory) {
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
		characters.get("Swordsman").setWeapon(aWeaponFactory.getWeapon(WeaponType.THRUSTING_SWORD));
		
		characters.get("Dwarf").setActionChits(aChitFactory.getDwarfChits());
		characters.get("Dwarf").setWeapon(aWeaponFactory.getWeapon(WeaponType.GREAT_AXE));
		characters.get("Dwarf").setHelmet(aArmourFactory.getArmour(ArmourType.HELMET));
		
		characters.get("Black Knight").setActionChits(aChitFactory.getBKnightChits());
		characters.get("Black Knight").setWeapon(aWeaponFactory.getWeapon(WeaponType.MACE));
		characters.get("Black Knight").setBody(aArmourFactory.getArmour(ArmourType.SUIT_OF_ARMOUR));
		characters.get("Black Knight").setShield(aArmourFactory.getArmour(ArmourType.SHIELD));
		
		characters.get("Amazon").setActionChits(aChitFactory.getAmazonChits());
		characters.get("Amazon").setWeapon(aWeaponFactory.getWeapon(WeaponType.SHORT_SWORD));
		characters.get("Amazon").setHelmet(aArmourFactory.getArmour(ArmourType.HELMET));
		characters.get("Amazon").setBody(aArmourFactory.getArmour(ArmourType.BREASTPLATE));
		characters.get("Amazon").setShield(aArmourFactory.getArmour(ArmourType.SHIELD));
		
		characters.get("Captain").setActionChits(aChitFactory.getCaptainChits());
		characters.get("Captain").setWeapon(aWeaponFactory.getWeapon(WeaponType.SHORT_SWORD));
		characters.get("Captain").setHelmet(aArmourFactory.getArmour(ArmourType.HELMET));
		characters.get("Captain").setBody(aArmourFactory.getArmour(ArmourType.BREASTPLATE));
		characters.get("Captain").setShield(aArmourFactory.getArmour(ArmourType.SHIELD));
	}

	public Map<String, Hero> getCharacters() {
		return characters;
	}
}
