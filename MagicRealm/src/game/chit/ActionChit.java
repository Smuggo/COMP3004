package game.chit;

public class ActionChit {
	public String lType; //MOVE, FIGHT, MAGIC
	public char lStrength; //L (LIGHT), M(MEDIUM), H(HEAVY), T(TREMENDOUS)
	public int lTime; //How long it takes the action to perform
	public int lEffort; //How much effort the action takes to perform
	public boolean lFatigued;
	public boolean lWounded;
	
	public ActionChit(String aType, char aStrength, int aTime, int aEffort){
		lType = aType;
		lStrength = aStrength;
		lTime = aTime;
		lEffort = aEffort;
		lFatigued = false;
		lWounded = false;
	}
	
	public String getType(){ return lType; }
	public char getStrength(){ return lStrength; }
	public int getTime(){ return lTime; }
	public int getEffort(){ return lEffort; }
	
	public boolean isFatigued(){ return lFatigued; }
	public boolean isWounded(){ return lWounded; }
	
	//Fatigued and wounded are the only things that will ever change
	public void setFatigued(boolean aFatigued){ lFatigued = aFatigued; }
	public void setWounded(boolean aWounded){ lWounded = aWounded; }
}
