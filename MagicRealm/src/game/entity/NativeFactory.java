package game.entity;

import game.item.Armour;
import game.item.ArmourFactory;
import game.item.Weapon;
import game.item.WeaponFactory;

import java.util.ArrayList;

import config.Config.ArmourType;
import config.Config.NativeGroup;
import config.Config.WeaponType;

//CHAPEL (Order)
	//2 Treasures
	//2 Great axes (H) 1 Great sword (H) 1 Morning star (H) 1 Crossbow (H) — — — 2
	//3 warhorses
//HOUSE (Soldiers)
	//2 Treasures
	//3 Short swords (L) 2 Thrusting swords (L) 2 Staffs (L) 3 — 2 — —
//INN (Rogues)
	//2 Treasures — — — — — — 
	//6 workhorses
//GUARD house (Guard) 2 — 2 Maces (M) 2 Axes (M) 1 Broadsword (M) 1 1 — — — 


//helmets
//breast plates
// shields
//suits of armor
public class NativeFactory {
	
	private ArrayList<Native> natives = new ArrayList<Native>();
	
	public NativeFactory() {
		
		// ///////////////Create Weapons .//////////////////
		WeaponFactory weaponFactory = new WeaponFactory();
		
		Weapon greatAxe = weaponFactory.getWeapon(WeaponType.GREAT_AXE);
		Weapon shortSword = weaponFactory.getWeapon(WeaponType.SHORT_SWORD);
		Weapon thrustingSword = weaponFactory.getWeapon(WeaponType.THRUSTING_SWORD);
		Weapon mace = weaponFactory.getWeapon(WeaponType.MACE);
		
		// Create Native Weapon Sets 
		ArrayList<Weapon> orderWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> soldiersWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> roguesWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> guardWeapons = new ArrayList<Weapon>();
	
		
		// Add Weapons to Native Weapon ArrayList
		orderWeapons.add(greatAxe);
		orderWeapons.add(greatAxe);
		/*
		orderWeapons.add(greatSword);
		orderWeapons.add(morningStar);
		orderWeapons.add(crossbow);
		*/
		
		
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(thrustingSword);
		soldiersWeapons.add(thrustingSword);
		/*
		soldiersWeapons.add(staff);
		soldiersWeapons.add(staff);
		*/
		
		// Rogues don't have any weapons
		
		guardWeapons.add(mace);
		/*
		guardWeapons.add(axe);
		guardWeapons.add(broadSword);*/
		
		
		
		/////////////// Create Armor///////////////
		
		ArmourFactory armourFactory = new ArmourFactory();
		
		Armour helmet = armourFactory.getArmour(ArmourType.HELMET);
		Armour suit = armourFactory.getArmour(ArmourType.SUIT_OF_ARMOUR);
		Armour breastplate = armourFactory.getArmour(ArmourType.BREASTPLATE);
		Armour shield = armourFactory.getArmour(ArmourType.SHIELD);
		
		// Armor Sets
		
		ArrayList<Armour> orderArmours = new ArrayList<Armour>();
		ArrayList<Armour> soldiersArmours = new ArrayList<Armour>();
		ArrayList<Armour> roguesArmours = new ArrayList<Armour>();
		ArrayList<Armour> guardAmours = new ArrayList<Armour>();
		
		// Order's Armor
		orderArmours.add(suit);
		orderArmours.add(suit);
		
		// Soldier's Armor
		soldiersArmours.add(helmet);
		soldiersArmours.add(helmet);
		soldiersArmours.add(helmet);
		soldiersArmours.add(shield);
		soldiersArmours.add(shield);
		
		// Rogue's Armor (None)
		
		// Guard's Armor
		guardAmours.add(helmet);
		guardAmours.add(breastplate);
		
		// Create Natives
		Native order = new Native(NativeGroup.ORDER, orderWeapons, orderArmours);
		Native soldiers = new Native(NativeGroup.SOLDIERS, soldiersWeapons, soldiersArmours); 
		Native rogues = new Native(NativeGroup.ROGUES, roguesWeapons, roguesArmours); 
		Native guard = new Native(NativeGroup.GUARD, guardWeapons, guardAmours); 
		
		// Add all Natives to Native ArrayList
		natives.add(order);
		natives.add(soldiers);
		natives.add(rogues);
		natives.add(guard);
	}
	
	public ArrayList<Native> getNatives() {
		return natives;
	}
	
	public Native getNativeGroup(NativeGroup nG) {
		for (int i = 0; i < natives.size(); i++) {
			if (natives.get(i).getNativeGroup() == nG)
				return natives.get(i);
		}
		return null;
	}

}
