package game.entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import config.Config.ImageType;

public class Hero implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -909098114209082150L;
	
	//Represents a character
	private int[] victoryConditions;
	private int fame;
	private int notoriety;
	private int gold;
	
	private String name;

	private boolean hidden;

	private ImageType characterSheet;
	private ImageType characterChit;
	
	public Hero(String n, ImageType charPage, ImageType charChit){
		name = n;
		characterSheet = charPage;
		characterChit = charChit;
		victoryConditions = new int[5];
		
		fame = 0;
		notoriety = 0;
		gold = 0;
		
		hidden = false;
	}
	
	public String  getName()           { return name; }
	public boolean getHidden()         { return hidden; }
	public ImageType getCharSheet(){ return characterSheet; }
	public ImageType getCharChit() { return characterChit; }
	public int[] getVictoryConditions(){ return victoryConditions; }
	public int getFame()               { return fame; }
	public int getNotoriety()          { return notoriety; }
	public int getGold()               { return gold; }
	
	public void setVictoryConditions(int[] vCond){ victoryConditions = vCond; }
	public void setFame(int nFame)               { fame = nFame; }
	public void setNotoriety(int nNotoriety)     { notoriety = nNotoriety; }
	public void setGold(int nGold)               { gold = nGold; }
	public void setHidden(boolean nHidden)       { hidden = nHidden; }
}
