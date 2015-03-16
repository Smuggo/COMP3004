package game.item;

import java.io.Serializable;

import config.Config.WeaponType;

public class Weapon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2084818781627706079L;

	private WeaponType lWeaponType;
	
	//Unalerted Values
	private char lDamageU;
	private int lSpeedU;
	private int lAsteriskU;
	
	//Alerted Values
	private char lDamageA;
	private int lSpeedA;
	private int lAsteriskA;
	
	//Static stuff
	private int lLength;
	private int lPrice;
	
	public Weapon(WeaponType aWeaponType, char aDamageU, int aSpeedU, int aAsteriskU,
			char aDamageA, int aSpeedA, int aAsteriskA,
			int aLength, int aPrice){
		lWeaponType = aWeaponType;
		
		lDamageU = aDamageU;
		lSpeedU = aSpeedU;
		lAsteriskU = aAsteriskU;
		
		lDamageA = aDamageA;
		lSpeedA = aSpeedA;
		lAsteriskA = aAsteriskA;
		
		lLength = aLength;
		lPrice = aPrice;
	}
	
	public char getUnalertedDamage(){ return lDamageU; }
	public int getUnalertedSpeed(){ return lSpeedU; }
	public int getUnalertedAsterisk(){ return lAsteriskU; }
	
	public char getAlertedDamage(){ return lDamageA; }
	public int getAlertedSpeed(){ return lSpeedA; }
	public int getAlertedAsterisk(){ return lAsteriskA; }
	
	public int getLength(){ return lLength; }
	public int getPrice(){ return lPrice; }
}
