package game.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import config.Config.ArmourType;
import config.Config.FightType;

public class ArmourFactory {
	private Map<ArmourType, Armour> lArmourMap = new HashMap<ArmourType, Armour>();
	
	public ArmourFactory(){
		ArrayList<FightType> lSuitOfArmour = new ArrayList<FightType>();
		ArrayList<FightType> lBreastplate = new ArrayList<FightType>();
		ArrayList<FightType> lHelmet = new ArrayList<FightType>();
		ArrayList<FightType> lShield = new ArrayList<FightType>();

		lSuitOfArmour.addAll(Arrays.asList(FightType.SMASH, FightType.SWING, FightType.THRUST));
		lBreastplate.addAll(Arrays.asList(FightType.THRUST, FightType.SWING));
		lHelmet.add(FightType.SMASH);
		
		lArmourMap.put(ArmourType.SUIT_OF_ARMOUR, new Armour(ArmourType.SUIT_OF_ARMOUR, lSuitOfArmour,'H', 17, 12));
		lArmourMap.put(ArmourType.BREASTPLATE, new Armour(ArmourType.BREASTPLATE, lBreastplate, 'M', 9, 6));
		lArmourMap.put(ArmourType.HELMET, new Armour(ArmourType.HELMET, lHelmet, 'M', 5, 3));
		lArmourMap.put(ArmourType.SHIELD, new Armour(ArmourType.SHIELD, lShield, 'M', 7, 5));
	}
	
	public Armour getArmour(ArmourType aArmourType){
		return lArmourMap.get(aArmourType);
	}
}
