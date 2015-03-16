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
		Weapon greatAxe = new Weapon("Great Axe");
		Weapon greatSword = new Weapon("Great Sword");
		Weapon morningStar = new Weapon("Morning Star");
		Weapon crossbow = new Weapon("Crossbow");
		
		ArrayList<Weapon> orderWeapons = new ArrayList<Weapon>();
		
		orderWeapons.add(greatAxe);
		orderWeapons.add(greatAxe);
		orderWeapons.add(greatSword);
		orderWeapons.add(morningStar);
		orderWeapons.add(crossbow);
		
		Native order = new Native("Order", orderWeapons);
		
		natives.add(order);
	}
	
	public ArrayList<Native> getNatives() {
		return natives;
	}

}
