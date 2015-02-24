package game.item;

import java.util.ArrayList;
import java.util.Random;

public class TreasureFactory {
	ArrayList<Treasure> lTreasureList;
	
	ArrayList<Treasure> lHoardTreasures;
	ArrayList<Treasure> lLairTreasures;
	ArrayList<Treasure> lAltarTreasures;
	ArrayList<Treasure> lShrineTreasures;
	ArrayList<Treasure> lPoolTreasures;
	ArrayList<Treasure> lVaultTreasures;
	ArrayList<Treasure> lCairnsTreasures;
	ArrayList<Treasure> lStatueTreasures;
	
	public TreasureFactory(){
		lTreasureList = new ArrayList<Treasure>();
		lHoardTreasures = new ArrayList<Treasure>();
		lLairTreasures = new ArrayList<Treasure>();
		lAltarTreasures = new ArrayList<Treasure>();
		lShrineTreasures = new ArrayList<Treasure>();
		lPoolTreasures = new ArrayList<Treasure>();
		lVaultTreasures = new ArrayList<Treasure>();
		lCairnsTreasures = new ArrayList<Treasure>();
		lStatueTreasures = new ArrayList<Treasure>();
		
		lTreasureList.add(new Treasure("7-League Boots", false, false, 'N', 0, 0, 2 ,5));
		lTreasureList.add(new Treasure("Alchemist's Mixture", false, false, 'N', 0, 0, 0, 4));
		lTreasureList.add(new Treasure("Amulet", false, false, 'N', 0, 0, 0, 5));
		lTreasureList.add(new Treasure("Ancient Telescope", false, false, 'N', 0, 0, 0, 5));
		lTreasureList.add(new Treasure("Battle Bracelets", false ,true, 'N', 0, 2, 4, 12));
		lTreasureList.add(new Treasure("Beast Pipes", false, false, 'N', 0, -5, 5, 8));
		lTreasureList.add(new Treasure("Bejeweled Dwarf Vest", true, true, 'M', 10, 0, 5, 27));
		lTreasureList.add(new Treasure("Belt of Strength", false, true, 'N', 0, 3, 6, 16));
		lTreasureList.add(new Treasure("Black Book", true, false, 'L', 0, -15, 15, 10));
		lTreasureList.add(new Treasure("Blasted Jewel", true, true, 'H', 0, -15, 15, 30));
		lTreasureList.add(new Treasure("Book of Lore", false, false, 'L', 0, -5, 10, 10));
		lTreasureList.add(new Treasure("Chest", false, true, 'T', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Cloak of Mist", false, false, 'N', 0, 0, 2, 4));
		lTreasureList.add(new Treasure("Cloven Hoof", true, false, 'N', 0, -20, 40, 4));
		lTreasureList.add(new Treasure("Crypt of the Knight", false, true, 'P', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Crystal Ball", false, true, 'M', 0, 0, 5, 20));
		lTreasureList.add(new Treasure("Deft Gloves", false, false, 'N', 5, 0, 6, 10));
		lTreasureList.add(new Treasure("Dragon Essence", true, false, 'N', 0, -10, 20, 3));
		lTreasureList.add(new Treasure("Dragonfang Necklace", true, false, 'N', 12, 0, 12, 8));
		lTreasureList.add(new Treasure("Draught of Speed", false, false, 'N', 0, 0, 0, 6));
		lTreasureList.add(new Treasure("Elusive Cloak", false, false, 'N', 0, 0, 2, 10));
		lTreasureList.add(new Treasure("Elven Slippers", false, false, 'N', 0, 0, 2, 5));
		lTreasureList.add(new Treasure("Enchanted Meadow", false, true, 'D', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Enchanter's Skull", true, true, 'L', 0, -10, 10, 17));
		lTreasureList.add(new Treasure("Eye of the Idol", false, true, 'M', 0, -5, 10, 34));
		lTreasureList.add(new Treasure("Eye of the Moon", false, true, 'M', 0, 0, 13, 13 ));
		lTreasureList.add(new Treasure("Flowers of Rest", false, false, 'P', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Flying Carpet", false, true, 'M', 0, 0, 12, 17));
		lTreasureList.add(new Treasure("Garb of Speed", false, true, 'N', 0, 2, 6, 16));
		lTreasureList.add(new Treasure("Girtle of Energy", false, true, 'N', 0, 2, 4, 13));
		lTreasureList.add(new Treasure("Glimmering Ring", false, true, 'N', 0, 0, 10, 15));
		lTreasureList.add(new Treasure("Gloves of Strength", false, false, 'N', 5, 0, 6, 8));
		lTreasureList.add(new Treasure("Glowing Gem", false, true, 'N', 5, 0, 5, 17));
		lTreasureList.add(new Treasure("Golden Arm Band", false, true, 'L', 3, 0, 0, 15));
		lTreasureList.add(new Treasure("Golden Crown", false, true, 'H', 20, 0, -15, 50));
		lTreasureList.add(new Treasure("Golden Icon", true, true, 'T', 0, -10, 20, 100));
		lTreasureList.add(new Treasure("Good Book", false, false, 'L', 0, 5, 5, 10));
		lTreasureList.add(new Treasure("Gripping Dust", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Handy Gloves", false, false, 'N', 0, 1, 2, 6));
		lTreasureList.add(new Treasure("Hidden Ring", true, true, 'N', 0, -10, 10, 20));
		lTreasureList.add(new Treasure("Imperial Tabard", true, true, 'M', 20, 0, -10, 17));
		lTreasureList.add(new Treasure("Lost Keys", false, false, 'N', 0, 0, 0, 5));
		lTreasureList.add(new Treasure("Lucky Charm", false, true, 'N', 0, 0, 0, 14));
		lTreasureList.add(new Treasure("Magic Spectacles", false, false, 'N', 0, 0, 2, 6));
		lTreasureList.add(new Treasure("Magic Wand", true, true, 'N', 0, -10, 10, 17));
		lTreasureList.add(new Treasure("Map of Lost Castle", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Map of Lost City", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Map of Ruins", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Mouldy Skeleton", false, true, 'P', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Oil of Poison", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Ointment of Bite", false, false, 'N', 0, 0, 0, 5));
		lTreasureList.add(new Treasure("Ointment of Steel", false, false, 'N', 0, 0, 0, 4));
		lTreasureList.add(new Treasure("Penetrating Grease", false, false, 'N', 0, 0, 0, 4));
		lTreasureList.add(new Treasure("Phantom Glass", false, false, 'L', 0, 0, 2, 8));
		lTreasureList.add(new Treasure("Potion of Energy", false, false, 'N', 0, 0, 0, 5));
		lTreasureList.add(new Treasure("Poultice of Health", false, false, 'N', 0, 0, 0, 2));
		lTreasureList.add(new Treasure("Power Boots", false, false, 'N', 0, 0, 3, 8));
		lTreasureList.add(new Treasure("Power Gauntlets", false, false, 'N', 0, 3, 4, 7));
		lTreasureList.add(new Treasure("Quick Boots", false, false, 'N', 0, 1, 2, 8));
		lTreasureList.add(new Treasure("Reflecting Grease", false, false, 'N', 0, 0, 0, 3));
		lTreasureList.add(new Treasure("Regent of Jewels", true, true, 'L', 10, 0, 10, 67));
		lTreasureList.add(new Treasure("Remains of Theif", false, true, 'P', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Royal Sceptre", true, false, 'L', 20, 0, -15, 8));
		lTreasureList.add(new Treasure("Sacred Grail", true, true, 'M', 50, 0, -25, 12));
		lTreasureList.add(new Treasure("Sacred Statue", true, false, 'L', 10, 0, -15, 10));
		lTreasureList.add(new Treasure("Scroll of Alchemy", true, false, 'L', 0, -10, 15, 10));
		lTreasureList.add(new Treasure("Scroll of Nature", false, false, 'L', 0, 0, 5, 10));
		lTreasureList.add(new Treasure("Shielded Lantern", false, false, 'L', 0, 0, 0, 8));
		lTreasureList.add(new Treasure("Shoes of Stealth", false, false, 'N', 0, 0, 2, 7));
		lTreasureList.add(new Treasure("Timeless Jewel", false, true, 'L', 5, 0, 7, 34));
		lTreasureList.add(new Treasure("Toadstool Circle", false, true, 'P', 0, 0, 0, 0));
		lTreasureList.add(new Treasure("Toadstool Ring", false, false, 'N', 0, 0, 3, 9));
		lTreasureList.add(new Treasure("Vial of Healing", false, false, 'N', 0, 0, 0, 2));
		lTreasureList.add(new Treasure("Withered Claw", false, false, 'N', 0, 0, 0, 3));
		
		setupTreasureLists();
	}
	
	public ArrayList<Treasure> getHoardTreasures(){ return lHoardTreasures; }
	public ArrayList<Treasure> getLairTreasures(){ return lLairTreasures; }
	public ArrayList<Treasure> getAltarTreasures(){ return lAltarTreasures; }
	public ArrayList<Treasure> getShrineTreasures(){ return lShrineTreasures; }
	public ArrayList<Treasure> getPoolTreasures(){ return lPoolTreasures; }
	public ArrayList<Treasure> getVaultTreasures(){ return lVaultTreasures; }
	public ArrayList<Treasure> getCairnsTreasures(){ return lCairnsTreasures; }
	public ArrayList<Treasure> getStatueTreasures(){ return lStatueTreasures; }
	
	public void setupTreasureLists(){
		Random lRandom = new Random();
		int lRoll;
		int lLargeTreasures = 0;
		int lSmallTreasures = 0;
		
		//HOARD
		while(lLargeTreasures < 5){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lHoardTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 4){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lHoardTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;

		//LAIR
		while(lLargeTreasures < 3){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lLairTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 4){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lLairTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;
		
		//ALTAR
		while(lLargeTreasures < 4){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lAltarTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		
		//SHRINE
		while(lLargeTreasures < 2){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lShrineTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 2){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lShrineTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;
		
		//POOL
		while(lLargeTreasures < 3){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lPoolTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 6){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lPoolTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;
		
		//VAULT
		while(lLargeTreasures < 5){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lVaultTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		
		//CAIRNS
		while(lLargeTreasures < 1){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lCairnsTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 6){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lCairnsTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;
		
		//STATUE
		while(lLargeTreasures < 1){
			lRoll = lRandom.nextInt(lTreasureList.size());
			
			if(lTreasureList.get(lRoll).isLargeTreasure()){
				lLargeTreasures++;
				lStatueTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		while(lSmallTreasures < 2){
			lRoll = lRandom.nextInt(lTreasureList.size());
			if(!lTreasureList.get(lRoll).isLargeTreasure()){
				lSmallTreasures++;
				lStatueTreasures.add(lTreasureList.get(lRoll));
				lTreasureList.remove(lRoll);
			}
		}
		lLargeTreasures = 0;
		lSmallTreasures = 0;
	}
}
