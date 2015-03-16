package game.entity;

import game.item.Weapon;

import java.util.ArrayList;

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

public class NativeFactory {
	
	private ArrayList<Native> natives = new ArrayList<Native>();
	
	public NativeFactory() {
		
		// Create Weapons
		/*Weapon greatAxe = new Weapon("Great Axe");
		Weapon greatSword = new Weapon("Great Sword");
		Weapon morningStar = new Weapon("Morning Star");
		Weapon crossbow = new Weapon("Crossbow");
		Weapon shortSword = new Weapon("Short Sword");
		Weapon thrustingSword = new Weapon("Thrusting Sword");
		Weapon staff = new Weapon("Staff");
		Weapon mace = new Weapon("Mace");
		Weapon axe = new Weapon("Axe");
		Weapon broadSword = new Weapon("Broad Sword");*/
		
		// Create Native Weapon Sets 
		ArrayList<Weapon> orderWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> soldiersWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> roguesWeapons = new ArrayList<Weapon>();
		ArrayList<Weapon> guardWeapons = new ArrayList<Weapon>();
		
		// Add Weapons to Native Weapon ArrayList
		/*orderWeapons.add(greatAxe);
		orderWeapons.add(greatAxe);
		orderWeapons.add(greatSword);
		orderWeapons.add(morningStar);
		orderWeapons.add(crossbow);
		
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(shortSword);
		soldiersWeapons.add(thrustingSword);
		soldiersWeapons.add(thrustingSword);
		soldiersWeapons.add(staff);
		soldiersWeapons.add(staff);
		
		// Rogues don't have any weapons
		//roguesWeapons.add(shortSword);
		
		guardWeapons.add(mace);
		guardWeapons.add(axe);
		guardWeapons.add(broadSword);*/
		
		// Create Natives
		Native order = new Native("Order", orderWeapons);
		Native soldiers = new Native("Soldiers", soldiersWeapons); 
		Native rogues = new Native("Rogues", roguesWeapons); 
		Native guard = new Native("Guard", guardWeapons); 
		
		// Add all Natives to Native ArrayList
		natives.add(order);
		natives.add(soldiers);
		natives.add(rogues);
		natives.add(guard);
	}
	
	public ArrayList<Native> getNatives() {
		return natives;
	}

}
