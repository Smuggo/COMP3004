package game.entity;

import java.awt.image.BufferedImage;

public class Character {
	//Represents a character
	private String name;
	private boolean available;
	
	private BufferedImage characterSheet;
	
	public Character(String n, BufferedImage sheet){
		name = n;
		available = true;
		characterSheet = sheet;
	}
	
	public String  getName()           { return name; }
	public boolean getAvailable() 	   { return available; }
	public BufferedImage getCharSheet(){ return characterSheet; }
	
	public void setAvailalbe(boolean nAvailable) { available = nAvailable; }
}
