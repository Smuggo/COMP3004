package game.chit;

import java.util.ArrayList;

public class ChitFactory {
	//Initializes all the sound, warning, and treasure chits
	
	private ArrayList<Chit> valleyWarningChits   = new ArrayList<>();
	private ArrayList<Chit> woodsWarningChits    = new ArrayList<>();
	private ArrayList<Chit> caveWarningChits    = new ArrayList<>();
	private ArrayList<Chit> mountainWarningChits = new ArrayList<>();
	private ArrayList<Chit> soundChits = new ArrayList<>();
	private ArrayList<Chit> treasureChits = new ArrayList<>();
	
	Chit lostCity;
	Chit lostCastle;
	
	public ChitFactory(){
		//VALLEY WARNING CHITS
		valleyWarningChits.add(new Chit("BONES V"));
		valleyWarningChits.add(new Chit("DANK V"));
		valleyWarningChits.add(new Chit("RUINS V"));
		valleyWarningChits.add(new Chit("SMOKE V"));
		valleyWarningChits.add(new Chit("STINK V"));
		
		//WOODS WARNING CHITS
		woodsWarningChits.add(new Chit("BONES W"));
		woodsWarningChits.add(new Chit("DANK W"));
		woodsWarningChits.add(new Chit("RUINS W"));
		woodsWarningChits.add(new Chit("SMOKE W"));
		woodsWarningChits.add(new Chit("STINK W"));
		
		//CAVE WARNING CHITS
		caveWarningChits.add(new Chit("BONES C"));
		caveWarningChits.add(new Chit("DANK C"));
		caveWarningChits.add(new Chit("RUINS C"));
		caveWarningChits.add(new Chit("SMOKE C"));
		caveWarningChits.add(new Chit("STINK C"));
		
		//MOUNTAIN WARNING CHITS
		mountainWarningChits.add(new Chit("BONES M"));
		mountainWarningChits.add(new Chit("DANK M"));
		mountainWarningChits.add(new Chit("RUINS M"));
		mountainWarningChits.add(new Chit("SMOKE M"));
		mountainWarningChits.add(new Chit("STINK M"));
		
		//SOUND WARNING CHITS
		soundChits.add(new Chit("FLUTTER"));
		soundChits.add(new Chit("HOWL"));
		soundChits.add(new Chit("PATTER"));
		soundChits.add(new Chit("ROAR"));
		soundChits.add(new Chit("SLITHER"));
		
		//TREASURE SITE CHITS
		treasureChits.add(new Chit("ALTAR"));
		treasureChits.add(new Chit("CAIRNS"));
		treasureChits.add(new Chit("HOARD"));
		treasureChits.add(new Chit("LAIR"));
		treasureChits.add(new Chit("POOL"));
		treasureChits.add(new Chit("SHRINE"));
		treasureChits.add(new Chit("STATUE"));
		treasureChits.add(new Chit("VAULT"));

		//LOST CITY & LOST CASTLE
		lostCity = new Chit("LOST CITY");
		lostCastle = new Chit("LOST CASTLE");
	}
	
	public ArrayList<Chit> getValleyChits()   { return valleyWarningChits; }
	public ArrayList<Chit> getWoodsChits()	  { return woodsWarningChits; }
	public ArrayList<Chit> getCaveChits() 	  { return caveWarningChits; }
	public ArrayList<Chit> getMountainChits() { return mountainWarningChits; }
	public ArrayList<Chit> getSoundChits() 	  { return soundChits; }
	public ArrayList<Chit> getSiteChits()	  { return treasureChits; }
	public Chit getLostCity() 				  {return lostCity; }
	public Chit getLostCastle() 			  { return lostCastle; }
}
