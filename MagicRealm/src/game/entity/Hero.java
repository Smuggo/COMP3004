package game.entity;

import java.awt.image.BufferedImage;

public class Hero {
	//Represents a character
	private int[] victoryConditions;
	
	private String name;
	
	private boolean available;

	private BufferedImage characterSheet;
	
	public Hero(String n, BufferedImage sheet){
		name = n;
		available = true;
		characterSheet = sheet;
		victoryConditions = new int[5];
	}
	
	public String  getName()           { return name; }
	public boolean getAvailable() 	   { return available; }
	public BufferedImage getCharSheet(){ return characterSheet; }
	public int[] getVictoryConditions(){ return victoryConditions; }
	
	public void setAvailalbe(boolean nAvailable) { available = nAvailable; }
	public void setVictoryConditions(int[] vCond){ victoryConditions = vCond; }
}
