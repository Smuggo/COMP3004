package game.chit;

public class Chit {
	/*
	 * Represents in game chits, e.g. Bones, Dank, Ruins...
	 */
	String name;
	boolean revealed;
	
	public Chit(String chitDescription){
		name = chitDescription;
		revealed = false;
	}
	
	public String getChitDesc() { return name; }
	public boolean isRevealed() { return revealed; } 
}
