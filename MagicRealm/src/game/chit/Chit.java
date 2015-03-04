package game.chit;

import game.GameState;
import game.item.Treasure;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import config.Config.ChitType;;

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
	ChitType chitType;
	ArrayList<Treasure> lSiteTreasures;
	
	public Chit(String chitDescription){
		//faceUpTitle = null;
		//faceDownTitle = null;
		lSiteTreasures = null;
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
	public ArrayList<Treasure> getTreasures(){ return lSiteTreasures; }
	public ChitType getChitType(){ return chitType; }
	public void setChitType(ChitType aChitType){ chitType = aChitType; }
	
	public void setTreasures(ArrayList<Treasure> aTreasureList){
		lSiteTreasures = aTreasureList;
	}
	
	public void draw(GameState aState, Graphics g, int aGridx, int aGridy){
		if(aState != null){
			int lChitx;
			int lChity;
			
			if(clearingNumber == -1){
				lChitx = aState.getHexGrid().getHex(aGridx, aGridy).getCenter().x + 65;
				lChity = aState.getHexGrid().getHex(aGridx, aGridy).getCenter().y + 155;
				g.setColor(Color.WHITE);
				g.fillRect(lChitx, lChity, 55, 55);
				g.setColor(Color.BLACK);
				g.drawString(name, lChitx, lChity+20);
			}
			else{
				lChitx = aState.getHexGrid().getHex(aGridx, aGridy).getHextile().getClearing(clearingNumber).getRotPosition().x - 25;
				lChity = aState.getHexGrid().getHex(aGridx, aGridy).getHextile().getClearing(clearingNumber).getRotPosition().y - 25;
				g.setColor(Color.WHITE);
				g.fillRect(lChitx, lChity, 55, 55);
				g.setColor(Color.BLACK);
				if(aState.getHexGrid().getHex(aGridx, aGridy).getHextile().getOtherChit() != null){
					g.drawString(aState.getHexGrid().getHex(aGridx, aGridy).getHextile().getOtherChit().getName(), lChitx, lChity+20);
					g.drawString(Integer.toString(aState.getHexGrid().getHex(aGridx, aGridy).getHextile().getOtherChit().getClearingNumber()), lChitx+25, lChity+40);
				}
			}
		}
	}
}
