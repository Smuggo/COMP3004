package game.entity;

import java.awt.image.BufferedImage;

public class Hero {
	//Represents a character
	private int[] victoryConditions;
	private int fame;
	private int notoriety;
	private int gold;
	
	private String name;
	
	private boolean available;
	private boolean hidden;

	private BufferedImage characterSheet;
	private BufferedImage characterChit;
	
	public Hero(String n, BufferedImage sheet, BufferedImage charChit){
		name = n;
		available = true;
		characterSheet = sheet;
		characterChit = charChit;
		victoryConditions = new int[5];
		
		fame = 0;
		notoriety = 0;
		gold = 0;
		
		hidden = false;
	}
	
	public String  getName()           { return name; }
	public boolean getAvailable() 	   { return available; }
	public boolean getHidden()         { return hidden; }
	public BufferedImage getCharSheet(){ return characterSheet; }
	public BufferedImage getCharChit() { return characterChit; }
	public int[] getVictoryConditions(){ return victoryConditions; }
	public int getFame()               { return fame; }
	public int getNotoriety()          { return notoriety; }
	public int getGold()               { return gold; }
	
	public void setAvailalbe(boolean nAvailable) { available = nAvailable; }
	public void setVictoryConditions(int[] vCond){ victoryConditions = vCond; }
	public void setFame(int nFame)               { fame = nFame; }
	public void setNotoriety(int nNotoriety)     { notoriety = nNotoriety; }
	public void setGold(int nGold)               { gold = nGold; }
	public void setHidden(boolean nHidden)       { hidden = nHidden; }
}
