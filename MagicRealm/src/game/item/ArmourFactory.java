package game.item;

import java.util.HashMap;
import java.util.Map;

import config.Config.ArmourType;

public class ArmourFactory {
	private Map<ArmourType, Armour> lArmourMap = new HashMap<ArmourType, Armour>();
	
	public ArmourFactory(){
		lArmourMap.put(ArmourType.SUIT_OF_ARMOUR, new Armour(ArmourType.SUIT_OF_ARMOUR, 'H', 17, 12));
		lArmourMap.put(ArmourType.BREASTPLATE, new Armour(ArmourType.BREASTPLATE, 'M', 9, 6));
		lArmourMap.put(ArmourType.HELMET, new Armour(ArmourType.HELMET, 'M', 5, 3));
		lArmourMap.put(ArmourType.SHIELD, new Armour(ArmourType.SHIELD, 'M', 7, 5));
	}
	
	public Armour getArmour(ArmourType aArmourType){
		return lArmourMap.get(aArmourType);
	}
}
