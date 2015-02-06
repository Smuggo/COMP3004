package game;

import java.awt.Dimension;

import game.environment.EnvironmentManager;
import game.environment.hex.HexGrid;

public class GameManager {
	
	private EnvironmentManager lEnvironmentManager;
	
	public GameManager(){
		lEnvironmentManager = new EnvironmentManager();
	}

	public Dimension createNewMap(){
		return lEnvironmentManager.createNewMap();
	}
	
	public HexGrid getGrid(){
		return lEnvironmentManager.getHexGrid();
	}
}
