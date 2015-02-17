package game.chit;

import config.Config;

public class Chit {
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
}
