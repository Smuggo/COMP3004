package game.entity;

import java.util.HashMap;
import java.util.Map;

public class MonsterFactory {
	//Instantiates the monsters
	private Map<String, Monster> monsters = new HashMap<String, Monster>();
	
	public MonsterFactory(){
		monsters.put("GHOST", new Monster("GHOST", "MEDIUM", 'H', 4, 4, 'L', 2, 2, false));
		monsters.put("WOLF", new Monster("WOLF", "MEDIUM", 'M', 5, 3, 'L', 3, 4, false));
		monsters.put("OGRE", new Monster("OGRE", "MEDIUM", 'T', 5, 5, 'H', 5, 4, false));
		monsters.put("VIPER", new Monster("VIPER", "MEDIUM", 'M', 4, 4, 'L', 2, 2, true));
		monsters.put("GOBLIN - AXE", new Monster("GOBLIN - AXE", "MEDIUM", 'L', 4, 3, 'M', 4, 4, false));
	}
	
	public Map<String, Monster> getMonsters(){ return monsters; }
}