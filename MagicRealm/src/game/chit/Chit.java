package game.chit;

import game.GameManager;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import config.Config;

public class Chit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1376754877806100989L;
	/*
	 * Represents in game chits, e.g. Bones, Dank, Ruins...
	 */
	String name;
	// faceUpTitle and faceDownTitle apply for vistor/mission chits, which have two sides
	//String faceUpTitle;
	//String faceDownTitle;
	boolean revealed;
	int clearingNumber;
	Config.ChitType chitType;
	
	public Chit(String chitDescription){
		//faceUpTitle = null;
		//faceDownTitle = null;
		name = chitDescription;
		revealed = false;
		clearingNumber = -1;
	}
	
	public Chit(String chitDescription, int number){
		//faceUpTitle = null;
		//faceDownTitle = null;
		name = chitDescription;
		revealed = false;
		clearingNumber = number;
	}
	
	/*public Chit(String face1, String face2){
		faceUpTitle = face1;
		faceDownTitle = face2;
		
	}*/
	
	public String getName() { return name; }
	public boolean isRevealed() { return revealed; } 
	public int getClearingNumber() { return clearingNumber; }
	
	public void draw(GameManager aManager, Graphics g, int aGridx, int aGridy){
		int lChitx;
		int lChity;
		
		if(clearingNumber == -1){
			lChitx = aManager.getGrid().getHex(aGridx, aGridy).getCenter().x;
			lChity = aManager.getGrid().getHex(aGridx, aGridy).getCenter().y;
			g.setColor(Color.WHITE);
			g.fillRect(lChitx, lChity, 55, 55);
			g.setColor(Color.BLACK);
			g.drawString(name, lChitx, lChity+20);
		}
		else{
			lChitx = aManager.getGrid().getHex(aGridx, aGridy).getHextile().getClearing(clearingNumber).getRotPosition().x - 25;
			lChity = aManager.getGrid().getHex(aGridx, aGridy).getHextile().getClearing(clearingNumber).getRotPosition().y - 25;
			g.setColor(Color.WHITE);
			g.fillRect(lChitx, lChity, 55, 55);
			g.setColor(Color.BLACK);
			g.drawString(aManager.getGrid().getHex(aGridx, aGridy).getHextile().getOtherChit().getName(), lChitx, lChity+20);
			g.drawString(Integer.toString(aManager.getGrid().getHex(aGridx, aGridy).getHextile().getOtherChit().getClearingNumber()), lChitx+25, lChity+40);
		}
	}
}
