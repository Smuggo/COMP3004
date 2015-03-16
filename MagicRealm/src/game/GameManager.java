package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import model.ViewModel;
import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.MonsterType;
import config.ImageMap;
import game.chit.ActionChitFactory;
import game.environment.EnvironmentManager;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;
import game.entity.HeroFactory;
import game.entity.Hero;
import game.item.ArmourFactory;
import game.item.WeaponFactory;

public class GameManager {
	
	private EnvironmentManager lEnvironmentManager;
	private HeroFactory   characters;
	private ImageMap gameImages;
	
	public GameManager(ViewModel aModel, ActionChitFactory aActionChitFactory, WeaponFactory aWeaponFactory,
			ArmourFactory aArmourFactory){
		lEnvironmentManager = new EnvironmentManager(aModel);
		characters = new HeroFactory(aActionChitFactory, aWeaponFactory, aArmourFactory);
		gameImages = new ImageMap();
	}

	public Dimension createNewMap(Map<String, Roadway> aHiddenRoadways){
		return lEnvironmentManager.createNewMap(gameImages, aHiddenRoadways);
	}
	public HexGrid getGrid(){
		return lEnvironmentManager.getHexGrid();
	}
	
	public Map<String, Hero> getCharacters(){
		return characters.getCharacters();
	}
	
	public BufferedImage getCharacterImage(CharacterImageType aImageType){
		return gameImages.getCharacterImage(aImageType);
	}
	
	public BufferedImage getDwellingImage(DwellingType aDwellingType){
		return gameImages.getDwellingImage(aDwellingType);
	}
	
	// Return monster image
	public BufferedImage getMonsterImage(MonsterType aMonsterType){
		return gameImages.getMonsterImage(aMonsterType);
	}
	
	public BufferedImage getHexImage(String aHexType){
		return gameImages.getHexImage(aHexType);
	}
	
	public ImageMap getGameImages(){
		return gameImages;
	}
	
	public EnvironmentManager getEnvironmentManager() {
		return lEnvironmentManager;
	}

	public Dimension getMapDimension() {
		return lEnvironmentManager.getHexGrid().getCanvasSize();
	}
}
