package game.item;

import java.io.Serializable;
import java.util.ArrayList;

import config.Config.ArmourType;
import config.Config.FightType;

public class Armour implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7766854143499104326L;

	private ArmourType lArmourType;
	private ArrayList<FightType> lProtectsFrom;
	private char lProtection;
	private int lPriceIntact;
	private int lPriceDamaged;
	private boolean lDamaged;
	
	public Armour(ArmourType aArmourType, ArrayList<FightType> aProtectsFrom, char aProtection, int aPriceIntact, int aPriceDamaged){
		lArmourType = aArmourType;
		lProtection = aProtection;
		lProtectsFrom = aProtectsFrom;
		lPriceIntact = aPriceIntact;
		lPriceDamaged = aPriceDamaged;
		lDamaged = false;
	}
	
	public ArmourType getArmourType(){ return lArmourType; }
	public ArrayList<FightType> getProtectsFrom(){ return lProtectsFrom; }
	public char getProtection(){ return lProtection; }
	public int getPriceIntact(){ return lPriceIntact; }
	public int getPriceDamaged(){ return lPriceDamaged; }
	public boolean isDamaged(){ return lDamaged; }
	
	public void setProtectsFrom(FightType aFightType){ //Specifically for shields; other armour doesn't change.
		lProtectsFrom.clear();
		lProtectsFrom.add(aFightType);
	}
	public void setDamaged(boolean aDamaged){
		lDamaged = aDamaged;
	}
}
