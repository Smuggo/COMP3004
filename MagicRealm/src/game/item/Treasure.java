package game.item;

public class Treasure {
	private String lName;
	private boolean lGreatTreasure;
	private boolean lLargeTreasure;
	private char lWeight;
	private int lFameReward;
	private int lFameValue;
	private int lNotValue;
	private int lGoldValue;
	
	public Treasure(String aName, boolean aGreatTreasure, boolean aLargeTreasure, char aWeight, int aFameReward, int aFameValue, int aNotValue, int aGoldValue){
		lName = aName;
		lGreatTreasure = aGreatTreasure;
		lLargeTreasure = aLargeTreasure;
		lWeight = aWeight;
		lFameReward = aFameReward;
		lFameValue = aFameValue;
		lNotValue = aNotValue;
		lGoldValue = aGoldValue;
	}
	
	public String getName(){ return lName; }
	public boolean isGreatTreasure(){ return lGreatTreasure; }
	public boolean isLargeTreasure(){ return lLargeTreasure; }
	public char getWeight(){ return lWeight; }
	public int getFameReward() { return lFameReward; }
	public int getFameValue(){ return lFameValue; }
	public int getNotValue(){ return lNotValue; }
	public int getGoldValue(){ return lGoldValue; }
}
