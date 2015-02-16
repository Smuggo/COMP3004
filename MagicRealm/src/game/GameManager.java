package game;

import java.util.Map;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import config.Config.ImageType;
import config.ImageMap;
import game.environment.EnvironmentManager;
import game.environment.hex.HexGrid;
import game.entity.HeroFactory;
import game.entity.Hero;

public class GameManager {
	
	private EnvironmentManager lEnvironmentManager;
	private HeroFactory   characters;
	private ImageMap gameImages;
	
	public GameManager(){
		lEnvironmentManager = new EnvironmentManager();
		characters = new HeroFactory();
		gameImages = new ImageMap();
	}

	public Dimension createNewMap(){
		return lEnvironmentManager.createNewMap();
	}
	
	public HexGrid getGrid(){
		return lEnvironmentManager.getHexGrid();
	}
	
	public Map<String, Hero> getCharacters(){
		return characters.getCharacters();
	}
	
	public BufferedImage getImage(ImageType aImageType){
		return gameImages.getImage(aImageType);
	}
}
