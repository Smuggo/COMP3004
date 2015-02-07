package game.entity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class CharacterFactory {
	//Initializes player characters
	private Map<String, Character> characters = new HashMap<String, Character>();
	
	public CharacterFactory(){
		try {
			characters.put("Captain", new Character("Captain", ImageIO.read(new File("media/images/characterdetail/captain.jpg"))));
			characters.put("Swordsman", new Character("Swordsman", ImageIO.read(new File("media/images/characterdetail/swordsman.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Character> getCharacters(){ return characters; }
}
