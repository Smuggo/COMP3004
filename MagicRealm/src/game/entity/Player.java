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
	
	public Player(String aUserName){
		lUserName = aUserName;
		lChosenHero = null;
		lastClick = new Point(20,20);
	}
	
	public String getUserName(){ return lUserName; }
	public Hero   getChosenHero(){ return lChosenHero; }
	
	public void setHero(Hero aHero){ lChosenHero = aHero; }
	
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
