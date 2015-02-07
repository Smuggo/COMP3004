package game;

import java.util.Map;
import java.awt.Dimension;

import game.environment.EnvironmentManager;
import game.environment.hex.HexGrid;
import game.entity.HeroFactory;
import game.entity.Hero;

public class GameManager {
	
	private EnvironmentManager lEnvironmentManager;
	private HeroFactory   characters;
	
	public GameManager(){
		lEnvironmentManager = new EnvironmentManager();
		characters = new HeroFactory();
	}

	public Dimension createNewMap(){
		return lEnvironmentManager.createNewMap();
	}
	
	public HexGrid getGrid(){
		return lEnvironmentManager.getHexGrid();
	}
	
	public Map<String, Hero> requestCharacters(){
		return characters.getCharacters();
	}
}
