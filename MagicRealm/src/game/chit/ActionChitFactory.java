package game.chit;

import java.util.ArrayList;

public class ActionChitFactory {
	//Each character has their own separate pool of action chits that they've earned
	private ArrayList<ActionChit> lSwordsmanChits;
	private ArrayList<ActionChit> lDwarfChits;
	private ArrayList<ActionChit> lBKnightChits;
	private ArrayList<ActionChit> lAmazonChits;
	private ArrayList<ActionChit> lCaptainChits;
	
	public ActionChitFactory(){
		 lSwordsmanChits = new ArrayList<ActionChit>();
		//WANDERER CHITS
		lSwordsmanChits.add(new ActionChit("MOVE", 'L', 4, 0));
		lSwordsmanChits.add(new ActionChit("MOVE", 'L', 3, 1));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'L', 3, 1));
		//THIEF CHITS
		lSwordsmanChits.add(new ActionChit("MOVE", 'L', 3, 1));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'L', 2, 2));
		lSwordsmanChits.add(new ActionChit("MOVE", 'L', 2, 2));
		//ADVENTURER CHITS
		lSwordsmanChits.add(new ActionChit("MOVE", 'M', 4, 1));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'M', 3, 2));
		//SWORDSMAN CHITS
		lSwordsmanChits.add(new ActionChit("FIGHT", 'L', 4, 0));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'M', 5, 0));
		lSwordsmanChits.add(new ActionChit("FIGHT", 'L', 2, 2));
		
		lDwarfChits = new ArrayList<ActionChit>();
		//YOUNGSTER
		lDwarfChits.add(new ActionChit("DUCK", 'T', 3, 1));
		lDwarfChits.add(new ActionChit("MOVE", 'H', 6, 0));
		lDwarfChits.add(new ActionChit("FIGHT", 'H', 5, 1));
		//SMITH
		lDwarfChits.add(new ActionChit("MOVE", 'T', 6, 1));
		lDwarfChits.add(new ActionChit("FIGHT", 'H', 6, 0));
		lDwarfChits.add(new ActionChit("FIGHT", 'H', 4, 2));
		//WARRIOR
		lDwarfChits.add(new ActionChit("MOVE", 'H', 5, 1));
		lDwarfChits.add(new ActionChit("FIGHT", 'T', 6, 1));
		lDwarfChits.add(new ActionChit("FIGHT", 'H', 4, 2));
		//DWARF
		lDwarfChits.add(new ActionChit("MOVE", 'T', 5, 2));
		lDwarfChits.add(new ActionChit("FIGHT", 'T', 5, 2));
		lDwarfChits.add(new ActionChit("FIGHT", 'T', 5, 2));

		lBKnightChits = new ArrayList<ActionChit>();
		//SPEARMAN
		lBKnightChits.add(new ActionChit("MOVE", 'M', 5, 0));
		lBKnightChits.add(new ActionChit("MOVE", 'H', 5, 1));
		lBKnightChits.add(new ActionChit("FIGHT", 'H', 5, 1));
		//MERCENARY
		lBKnightChits.add(new ActionChit("MOVE", 'H', 6, 0));
		lBKnightChits.add(new ActionChit("MOVE", 'M', 4, 1));
		lBKnightChits.add(new ActionChit("FIGHT", 'H', 6, 0));
		//HEAVY FOOTMAN
		lBKnightChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		lBKnightChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		lBKnightChits.add(new ActionChit("FIGHT", 'M', 5, 0));
		//BLACK KNIGHT
		lBKnightChits.add(new ActionChit("MOVE", 'H', 4, 2));
		lBKnightChits.add(new ActionChit("FIGHT", 'H', 4, 2));
		lBKnightChits.add(new ActionChit("FIGHT", 'M', 3, 2));
		
		lAmazonChits = new ArrayList<ActionChit>();
		//SCOUT
		lAmazonChits.add(new ActionChit("MOVE", 'M', 4, 0));
		lAmazonChits.add(new ActionChit("MOVE", 'M', 3, 1));
		lAmazonChits.add(new ActionChit("FIGHT", 'L', 4, 0));
		//WARRIOR
		lAmazonChits.add(new ActionChit("MOVE", 'M', 4, 0));
		lAmazonChits.add(new ActionChit("FIGHT", 'M', 5, 0));
		lAmazonChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		//CHAMPION
		lAmazonChits.add(new ActionChit("MOVE", 'M', 3, 1));
		lAmazonChits.add(new ActionChit("FIGHT", 'M', 3, 2));
		lAmazonChits.add(new ActionChit("FIGHT", 'H', 4, 2));
		//AMAZON
		lAmazonChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		lAmazonChits.add(new ActionChit("FIGHT", 'M', 3, 2));
		lAmazonChits.add(new ActionChit("MOVE", 'M', 3, 1));
		
		lCaptainChits = new ArrayList<ActionChit>();
		//SPEARMAN
		lCaptainChits.add(new ActionChit("MOVE", 'M', 4, 1));
		lCaptainChits.add(new ActionChit("MOVE", 'M', 5, 0));
		lCaptainChits.add(new ActionChit("FIGHT", 'H', 5, 1));
		//SOLDIER
		lCaptainChits.add(new ActionChit("FIGHT", 'M', 5, 0));
		lCaptainChits.add(new ActionChit("FIGHT", 'M', 3, 2));
		lCaptainChits.add(new ActionChit("MOVE", 'M', 3, 2));
		//LIEUTENANT
		lCaptainChits.add(new ActionChit("MOVE", 'M', 4, 1));
		lCaptainChits.add(new ActionChit("FIGHT", 'H', 5, 1));
		lCaptainChits.add(new ActionChit("FIGHT", 'M', 4, 1));
		//CAPTAIN
		lCaptainChits.add(new ActionChit("MOVE", 'M', 4, 1));
		lCaptainChits.add(new ActionChit("FIGHT", 'H', 6, 0));
		lCaptainChits.add(new ActionChit("FIGHT", 'M', 4, 1));
	}
	
	public ArrayList<ActionChit> getSwordsmanChits() { return lSwordsmanChits; }
	public ArrayList<ActionChit> getDwarfChits() { return lDwarfChits; }
	public ArrayList<ActionChit> getBKnightChits() { return lBKnightChits; }
	public ArrayList<ActionChit> getAmazonChits() { return lAmazonChits; }
	public ArrayList<ActionChit> getCaptainChits() {return lCaptainChits; }
}
