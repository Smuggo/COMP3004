package game.entity;

import game.environment.hex.Clearing;

import java.awt.Point;
import java.io.Serializable;

public class Player implements Serializable{
	

	private static final long serialVersionUID = -1317880341447120558L;
	
	private String lUserName;
	private Hero lChosenHero;
	public Point lastClick;
	private int turnOrder;
	private int lPlayerOpponent; //Player number of the person this player is in combat with
	private boolean lInCombat;   //Status of if the player is in combat or not
	
	public Player(String aUserName){
		lUserName = aUserName;
		lChosenHero = null;
		lastClick = new Point(20,20);
		lPlayerOpponent = -1;
		lInCombat = false;
	}
	
	public String getUserName(){ return lUserName; }
	public Hero   getChosenHero(){ return lChosenHero; }
	public int    getOpponent(){ return lPlayerOpponent; }
	public boolean isInCombat(){ return lInCombat; }
	
	public void setHero(Hero aHero){ lChosenHero = aHero; }
	public void setOpponent(int aOpponent){ lPlayerOpponent = aOpponent; }
	public void setInCombat(boolean aInCombat){ lInCombat = aInCombat; }
	
	public String toString(){
		String playerString = "";
		if(lChosenHero != null)
			playerString = lUserName + lChosenHero.getName();
		return playerString;
	}
	
	public void setTurnOrder(int aTurnOrder){
		turnOrder = aTurnOrder;
	}
	public int getTurnOrder(){
		return turnOrder;
	}
}
