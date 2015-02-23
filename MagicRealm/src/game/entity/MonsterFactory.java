package game.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import config.Config.MonsterType;

public class MonsterFactory {
	//Instantiates the monsters
	private Map<String, Monster> monsters = new HashMap<String, Monster>();
	
	public MonsterFactory(){
		monsters.put("GHOST", new Monster("GHOST", "MEDIUM", 'H', 4, 4, 'L', 2, 2, false, MonsterType.GHOST));
		monsters.put("WOLF", new Monster("WOLF", "MEDIUM", 'M', 5, 3, 'L', 3, 4, false, null));
		monsters.put("OGRE", new Monster("OGRE", "MEDIUM", 'T', 5, 5, 'H', 5, 4, false, null));
		monsters.put("VIPER", new Monster("VIPER", "MEDIUM", 'M', 4, 4, 'L', 2, 2, true,null));
		monsters.put("GOBLIN - AXE", new Monster("GOBLIN - AXE", "MEDIUM", 'L', 4, 3, 'M', 4, 4, false,null));
	}
	
	public ArrayList<Monster> createTwoGhosts() {
		ArrayList<Monster> ghosts = new ArrayList<>();
		MonsterFactory monsterFactory = new MonsterFactory();
		Map<String, Monster> theMonsters = monsterFactory.getMonsters();
		Monster ghost = theMonsters.get("GHOST");
		ghosts.add(ghost);
		ghosts.add(ghost);
		return ghosts;
	}
	
	public Map<String, Monster> getMonsters(){ return monsters; }
}