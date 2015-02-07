package game.entity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class HeroFactory {
	//Initializes player characters
	private Map<String, Hero> characters = new HashMap<String, Hero>();
	
	public HeroFactory(){
		try {
			characters.put("Captain", new Hero("Captain",
											   ImageIO.read(new File("media/images/characterdetail/captain.jpg")),
											   ImageIO.read(new File("media/images/characters/captain.png"))));
			characters.put("Swordsman", new Hero("Swordsman", 
											     ImageIO.read(new File("media/images/characterdetail/swordsman.jpg")),
											     ImageIO.read(new File("media/images/characters/swordsman.png"))));
			characters.put("Amazon", new Hero("Amazon", 
											  ImageIO.read(new File("media/images/characterdetail/amazon.jpg")),
											  ImageIO.read(new File("media/images/characters/amazon.png"))));
			characters.put("Dwarf", new Hero("Dwarf", 
											 ImageIO.read(new File("media/images/characterdetail/dwarf.jpg")),
											 ImageIO.read(new File("media/images/characters/dwarf.png"))));
			characters.put("Elf", new Hero("Elf", 
					 						 ImageIO.read(new File("media/images/characterdetail/elf.jpg")),
					 						 ImageIO.read(new File("media/images/characters/elf.png"))));
			characters.put("Black Knight", new Hero("Black Knight", 
					 					   ImageIO.read(new File("media/images/characterdetail/black_knight.jpg")),
					 					   ImageIO.read(new File("media/images/characters/black_knight.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Hero> getCharacters(){ return characters; }
}
