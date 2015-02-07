package game.entity;

import java.awt.Point;
import java.io.Serializable;

public class Player implements Serializable{
	

	private static final long serialVersionUID = -1317880341447120558L;
	
	String lUserName;
	Hero lChosenHero;
	public Point lastClick;
	
	public Player(String aUserName){
		lUserName = aUserName;
		lChosenHero = null;
		lastClick = new Point(20,20);
	}
	
	public void setHero(Hero aHero){
		lChosenHero = aHero;
	}

}
