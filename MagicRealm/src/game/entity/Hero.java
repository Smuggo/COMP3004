package game.entity;

import game.environment.hex.Clearing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import config.Config.CharacterImageType;
import config.Config.DwellingType;

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
	
	private Clearing lClearing;

	private boolean hidden;

	private CharacterImageType characterSheet;
	private CharacterImageType characterChit;
	private DwellingType[] startingLocations;
	
	public Hero(String n, CharacterImageType charPage, CharacterImageType charChit, DwellingType[] aStartingLocations){
		name = n;
		characterSheet = charPage;
		characterChit = charChit;
		startingLocations = aStartingLocations;
		victoryConditions = new int[5];
		
		fame = 0;
		notoriety = 0;
		gold = 0;
		
		hidden = false;
	}
	
	public void draw(Graphics g, Player aPlayer){
		if(lClearing != null){
			FontMetrics fm = g.getFontMetrics();
			String string = name+" : "+aPlayer.getUserName();
			Rectangle2D rect = fm.getStringBounds(string, g);
			int x = (int)lClearing.getRotPosition().getX();
			int y = (int)lClearing.getRotPosition().getY();
			
			g.setColor(new Color(20,20,20));
			g.fillRect(x-3, y - fm.getAscent(), (int) rect.getWidth()+6, (int) rect.getHeight());
			g.setColor(new Color(0,200,200));
			g.drawString(string, x, y);
			g.setColor(Color.black);
		}
	}
	
	public String  getName()           { return name; }
	public boolean getHidden()         { return hidden; }
	public CharacterImageType getCharSheet(){ return characterSheet; }
	public CharacterImageType getCharChit() { return characterChit; }
	public int[] getVictoryConditions(){ return victoryConditions; }
	public int getFame()               { return fame; }
	public int getNotoriety()          { return notoriety; }
	public int getGold()               { return gold; }
	public DwellingType[] getStartingLocations() { return startingLocations; }
	public Clearing getClearing() { return lClearing; }
	
	public void setVictoryConditions(int[] vCond){ victoryConditions = vCond; }
	public void setFame(int nFame)               { fame = nFame; }
	public void setNotoriety(int nNotoriety)     { notoriety = nNotoriety; }
	public void setGold(int nGold)               { gold = nGold; }
	public void setHidden(boolean nHidden)       { hidden = nHidden; }
	public void setClearing(Clearing aClearing)  { lClearing = aClearing; }
}
