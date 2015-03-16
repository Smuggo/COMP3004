package game.item;

import java.io.Serializable;

import config.Config.ArmourType;

public class Armour implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7766854143499104326L;

	private ArmourType lArmourType;
	private char lProtection;
	private int lPriceIntact;
	private int lPriceDamaged;
	private boolean lDamaged;
	
	public Armour(ArmourType aArmourType, char aProtection, int aPriceIntact, int aPriceDamaged){
		lArmourType = aArmourType;
		lProtection = aProtection;
		lPriceIntact = aPriceIntact;
		lPriceDamaged = aPriceDamaged;
		lDamaged = false;
	}
	
	public ArmourType getArmourType(){ return lArmourType; }
	public char getProtection(){ return lProtection; }
	public int getPriceIntact(){ return lPriceIntact; }
	public int getPriceDamaged(){ return lPriceDamaged; }
	public boolean isDamaged(){ return lDamaged; }
	
	public void setDamaged(boolean aDamaged){
		lDamaged = aDamaged;
	}
}
