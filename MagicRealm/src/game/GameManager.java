package game;

import game.environment.EnvironmentManager;
import game.environment.hex.HexGrid;

public class GameManager {
	
	private EnvironmentManager lEnvironmentManager;
	
	public GameManager(){
		lEnvironmentManager = new EnvironmentManager();
	}

	public void createNewMap(){
		lEnvironmentManager.createNewMap();
	}
	
	public HexGrid getGrid(){
		return lEnvironmentManager.getHexGrid();
	}
}
