package config;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.MonsterType;
import config.Config.NativeGroup;

public class ImageMap {

	private Map<CharacterImageType, BufferedImage> characterImageMap = new HashMap<CharacterImageType, BufferedImage>();
	private Map<Config.DwellingType, BufferedImage> dwellingImageMap = new HashMap<Config.DwellingType, BufferedImage>();
	private Map<Config.MonsterType, BufferedImage> monsterImageMap = new HashMap<Config.MonsterType, BufferedImage>();
	private Map<Config.NativeGroup, BufferedImage> nativeGroupImageMap = new HashMap<Config.NativeGroup, BufferedImage>();
	private Map<String, BufferedImage> gridImageMap = new HashMap<String, BufferedImage>();

	public ImageMap(){
		try {
			characterImageMap.put(CharacterImageType.captainPage, ImageIO.read(new File ("media/images/characterdetail/captain.jpg")));
			characterImageMap.put(CharacterImageType.captainChit, ImageIO.read(new File ("media/images/characters/captain.png")));
			characterImageMap.put(CharacterImageType.swordsmanPage, ImageIO.read(new File("media/images/characterdetail/swordsman.jpg")));
			characterImageMap.put(CharacterImageType.swordsmanChit, ImageIO.read(new File("media/images/characters/swordsman.png")));
			characterImageMap.put(CharacterImageType.amazonPage, ImageIO.read(new File ("media/images/characterdetail/amazon.jpg")));
			characterImageMap.put(CharacterImageType.amazonChit, ImageIO.read(new File ("media/images/characters/amazon.png")));
			characterImageMap.put(CharacterImageType.dwarfPage, ImageIO.read(new File ("media/images/characterdetail/dwarf.jpg")));
			characterImageMap.put(CharacterImageType.dwarfChit, ImageIO.read(new File ("media/images/characters/dwarf.png")));
			characterImageMap.put(CharacterImageType.elfPage, ImageIO.read(new File ("media/images/characterdetail/elf.jpg")));
			characterImageMap.put(CharacterImageType.elfChit, ImageIO.read(new File ("media/images/characters/elf.png")));
			characterImageMap.put(CharacterImageType.bKnightPage, ImageIO.read(new File ("media/images/characterdetail/black_knight.jpg")));
			characterImageMap.put(CharacterImageType.bKnightChit, ImageIO.read(new File ("media/images/characters/black_knight.png")));

			dwellingImageMap.put(DwellingType.GUARD, ImageIO.read(new File("media/images/dwellings/guard.gif")));
			dwellingImageMap.put(DwellingType.CHAPEL, ImageIO.read(new File("media/images/dwellings/chapel.gif")));
			dwellingImageMap.put(DwellingType.HOUSE, ImageIO.read(new File("media/images/dwellings/house.gif")));
			dwellingImageMap.put(DwellingType.INN, ImageIO.read(new File("media/images/dwellings/inn.gif")));
			
			monsterImageMap.put(MonsterType.GHOST, ImageIO.read(new File("media/images/monsters/ghost.gif")));
			
			nativeGroupImageMap.put(NativeGroup.ORDER, ImageIO.read(new File("media/images/natives/knight.gif")));
			nativeGroupImageMap.put(NativeGroup.SOLDIERS, ImageIO.read(new File("media/images/natives/pikeman.gif")));
			nativeGroupImageMap.put(NativeGroup.ROGUES, ImageIO.read(new File("media/images/natives/assassin.gif")));
			nativeGroupImageMap.put(NativeGroup.GUARD, ImageIO.read(new File("media/images/natives/great_swordsman.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getCharacterImage (CharacterImageType aCharacterImageType){ return characterImageMap.get(aCharacterImageType); }
	public BufferedImage getDwellingImage (DwellingType aDwellingType) { return dwellingImageMap.get(aDwellingType); }
	public BufferedImage getMonsterImage (MonsterType aMonsterType) { return monsterImageMap.get(aMonsterType); }
	public BufferedImage getHexImage(String aHexType){ return gridImageMap.get(aHexType);}
	
	public void addHexImage(String aHexType, BufferedImage aHexImage){
		gridImageMap.put(aHexType, aHexImage);
	}

	public BufferedImage getNativeGroupImage(NativeGroup nativeGroup){ return nativeGroupImageMap.get(nativeGroup); }
}
