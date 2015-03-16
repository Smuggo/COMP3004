package game.item;

import java.util.HashMap;
import java.util.Map;

import config.Config.WeaponType;

public class WeaponFactory {
	public Map<WeaponType, Weapon> lWeaponMap = new HashMap<WeaponType, Weapon>();
	
	public WeaponFactory(){
		lWeaponMap.put(WeaponType.MACE, new Weapon(WeaponType.MACE, 'M', 0, 0, 'M', 3, 1, 1, 6));
		lWeaponMap.put(WeaponType.GREAT_AXE, new Weapon(WeaponType.GREAT_AXE, 'H', 0, 0, 'H', 4, 1, 2, 4));
		lWeaponMap.put(WeaponType.THRUSTING_SWORD, new Weapon(WeaponType.THRUSTING_SWORD, 'L', 4, 0, 'L', 0, 1, 4, 6));
		lWeaponMap.put(WeaponType.SHORT_SWORD, new Weapon(WeaponType.SHORT_SWORD, 'L', 0, 0, 'L', 0, 1, 3, 4));
	}
	
	public Weapon getWeapon(WeaponType aWeaponType) { 
		return lWeaponMap.get(aWeaponType); 
	}
}
