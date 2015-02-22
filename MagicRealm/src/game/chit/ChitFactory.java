package game.chit;

import java.util.ArrayList;

//import view.internals.game.ChitList;
import config.Config;
import game.environment.hex.Hextile;

// source for game pieces: http://www.globetrotter-games.com/index.htm?E&game/eMRealm.htm
/*
 	10 site chits (8 orange+2 red(lost city+castle) / blank) (small sq)
  	6 number chits 1-6 (red/blank, small sq)
	10 sound chits (2x5) (red/blank) (small sq) 
  	1 day (=turn) chit (red/blank) (small sq)
  	1 monster roll chit (red/blank) (small sq)
  	20 warning chits (5x4) (yellow/blank) (smoke/ruins/dank/bones/stink - M/V/W/C)
  	6 visitor/mission counters (orange/white) (small sq)
  		(scholar/pillage, shaman/escort party, warlock/food ale,
  		revolt/quest, conquer/war, crone/raid)
*/

public class ChitFactory {
	// Initializes all the vistor/mission, sound, warning, and site chits
	private ArrayList<Chit> valleyWarningChits = new ArrayList<>();
	private ArrayList<Chit> woodsWarningChits = new ArrayList<>();
	private ArrayList<Chit> caveWarningChits = new ArrayList<>();
	private ArrayList<Chit> mountainWarningChits = new ArrayList<>();
	//private ArrayList<Chit> warningChits = new ArrayList<>();
	private ArrayList<Chit> soundChits = new ArrayList<>();
	private ArrayList<Chit> siteChits = new ArrayList<>();
	//private ArrayList<Chit> visitorMissionChits = new ArrayList<>();
	
	
	// Chit Groups combinations for setting up the game
	private ArrayList<Chit> siteSoundChits = new ArrayList<>();				// all site and all sound chits
	private ArrayList<Chit> lostCityChits = new ArrayList<>(); 				// 5 site/sound chits
	private ArrayList<Chit> lostCastleChits = new ArrayList<>(); 			// 5 site/sound chits
	private ArrayList<Chit> siteSoundLostCityChits = new ArrayList<>();		// 4 site/sound chits & lost city chit
	private ArrayList<Chit> siteSoundLostCastleChits = new ArrayList<>();	// 4 site/sound chits & lost castle chit
	
	ArrayList<Hextile> hextiles;
	
	final static int numLostCityChits = 5;
	final static int numLostCastleChits = 5;
	final static int numChitsMixedWithLostCity = 4;
	final static int numChitsMixedWithLostCastle = 4;

	Chit lostCity;
	Chit lostCastle;

	public ChitFactory(ArrayList<Hextile> allHextiles) {
		hextiles = allHextiles;
		// LOST CITY & LOST CASTLE
		lostCity = new Chit("LOST CITY");
		lostCastle = new Chit("LOST CASTLE");
		
		initializeArrayLists();
		System.out.println("Chit Factory Created");
	}
	
	private void initializeArrayLists() {
		// 3.5.2 Preparation //
		
		// VALLEY WARNING CHITS
		valleyWarningChits.add(new Chit("BONES V"));
		valleyWarningChits.add(new Chit("DANK V"));
		valleyWarningChits.add(new Chit("RUINS V"));
		valleyWarningChits.add(new Chit("SMOKE V"));
		valleyWarningChits.add(new Chit("STINK V"));

		// WOODS WARNING CHITS
		woodsWarningChits.add(new Chit("BONES W"));
		woodsWarningChits.add(new Chit("DANK W"));
		woodsWarningChits.add(new Chit("RUINS W"));
		woodsWarningChits.add(new Chit("SMOKE W"));
		woodsWarningChits.add(new Chit("STINK W"));

		// CAVE WARNING CHITS
		caveWarningChits.add(new Chit("BONES C"));
		caveWarningChits.add(new Chit("DANK C"));
		caveWarningChits.add(new Chit("RUINS C"));
		caveWarningChits.add(new Chit("SMOKE C"));
		caveWarningChits.add(new Chit("STINK C"));

		// MOUNTAIN WARNING CHITS
		mountainWarningChits.add(new Chit("BONES M"));
		mountainWarningChits.add(new Chit("DANK M"));
		mountainWarningChits.add(new Chit("RUINS M"));
		mountainWarningChits.add(new Chit("SMOKE M"));
		mountainWarningChits.add(new Chit("STINK M"));

		// SOUND CHITS
		soundChits.add(new Chit("FLUTTER", 2));
		soundChits.add(new Chit("FLUTTER", 3));
		soundChits.add(new Chit("HOWL", 4));
		soundChits.add(new Chit("HOWL", 5));
		soundChits.add(new Chit("PATTER", 2));
		soundChits.add(new Chit("PATTER", 5));
		soundChits.add(new Chit("ROAR", 4));		
		soundChits.add(new Chit("ROAR", 6));
		soundChits.add(new Chit("SLITHER", 3));
		soundChits.add(new Chit("SLITHER", 6));
		
		// SITE CHITS
		siteChits.add(new Chit("ALTAR", 1));
		siteChits.add(new Chit("STATUE", 2));
		siteChits.add(new Chit("VAULT", 3));
		siteChits.add(new Chit("LAIR", 3));
		siteChits.add(new Chit("SHRINE", 4));
		siteChits.add(new Chit("CAIRNS", 5));
		siteChits.add(new Chit("HOARD", 6));
		siteChits.add(new Chit("POOL", 6));
		
		createSiteSoundChits();
		
		/*// VISTOR/MISSION CHITS
		visitorMissionChits.add(new Chit("SCHOLAR", "PILLAGE"));
		visitorMissionChits.add(new Chit("SHAMAN", "ESCORT PARTY"));
		visitorMissionChits.add(new Chit("WARLOCK", "FOOD ALE"));
		visitorMissionChits.add(new Chit("CRONE", "RAID"));
		visitorMissionChits.add(new Chit("CONQUEST", "WAR"));
		visitorMissionChits.add(new Chit("REVOLT", "QUEST"));*/
	}
	
	public void addChitsManually() {
		// Create basic Chit groups
		siteSoundLostCityChits.add(lostCity);
		siteSoundLostCityChits.addAll(siteSoundChits);
		
		siteSoundLostCastleChits.add(lostCastle);
		siteSoundLostCastleChits.addAll(siteSoundChits);
	}
	
	public void addChitManually(Chit aChit, Hextile hextile, boolean lostCity, boolean lostCastle, boolean warningChit) {
		if (lostCity) {
			lostCityChits.add(aChit);
			siteSoundChits.remove(aChit);
			siteSoundLostCityChits.remove(aChit);
			siteSoundLostCastleChits.remove(aChit);
		}
		else if (lostCastle) {
			lostCastleChits.add(aChit);
			siteSoundChits.remove(aChit);
			siteSoundLostCityChits.remove(aChit);
			siteSoundLostCastleChits.remove(aChit);
		}
		// Add to hextile
		else {
			if (warningChit) {
				hextile.setWarningChit(aChit);
				
				// Remove chit from its pile
				for (int i = 0; i < valleyWarningChits.size(); i++) {
					if (valleyWarningChits.get(i) == aChit) {
						valleyWarningChits.remove(aChit);
					}
				}
				for (int i = 0; i < woodsWarningChits.size(); i++) {
					if (woodsWarningChits.get(i) == aChit) {
						woodsWarningChits.remove(aChit);
					}
				}
				for (int i = 0; i < caveWarningChits.size(); i++) {
					if (caveWarningChits.get(i) == aChit) {
						caveWarningChits.remove(aChit);
					}
				}
				for (int i = 0; i < mountainWarningChits.size(); i++) {
					if (mountainWarningChits.get(i) == aChit) {
						mountainWarningChits.remove(aChit);
					}
				}
			}
			else {
				hextile.setOtherChit(aChit);
				
				// Remove chit
				for (int i = 0; i < siteSoundLostCityChits.size(); i++) {
					if (siteSoundLostCityChits.get(i) == aChit) {
						siteSoundLostCityChits.remove(aChit);
						siteSoundLostCastleChits.remove(aChit);
					}
				}
				for (int i = 0; i < siteSoundLostCastleChits.size(); i++) {
					if (siteSoundLostCastleChits.get(i) == aChit) {
						siteSoundLostCityChits.remove(aChit);
						siteSoundLostCastleChits.remove(aChit);
					}
				}
			}
		}
		//printChitLocations(hextiles);
	}
	
	public void addChitsRandomly() {
		randomlyCreateLostCityAndLostCastleChits();
		randomlyAddChitsToHextiles(hextiles);
		
		//printChitLocations(hextiles);
		
		// Should be empty
		System.out.println("");
		//printAllArraylists();
		//printChitLocations(hextiles);
	}
	
	private void createSiteSoundChits() {
		int siteChitsSize = siteChits.size();
		int soundChitsSize = soundChits.size();
		
		// Combine site and sound chits into one group
		for (int i = 0; i < siteChitsSize; i++) {
			siteSoundChits.add(siteChits.get(0));
			siteChits.remove(0);
		}
		for (int i = 0; i < soundChitsSize; i++) {
			siteSoundChits.add(soundChits.get(0));
			soundChits.remove(0);
		}
	}
	
	private void randomlyCreateLostCityAndLostCastleChits() {
		// 3.5.3 The Lost City and Lost Castle Sections //
		
		// Shuffle site and sound chits together thoroughly
		randomizeChitArrayList(siteSoundChits);
		
		// Randomly choose 10 chits
		// Place 5 chits into the Lost City
		// Place 5 chits into the Lost Castle
		for (int i = 0; i < numLostCityChits; i++) {
			lostCityChits.add(siteSoundChits.get(0));
			siteSoundChits.remove(0);
		}
		
		for (int i = 0; i < numLostCastleChits; i++) {
			lostCastleChits.add(siteSoundChits.get(0));
			siteSoundChits.remove(0);
		}
		
	}
	
	private void randomlyAddChitsToHextiles(ArrayList<Hextile> hextiles) {
		// Randomize all chits groups to be places on hextiles
		randomizeAllWarningChitArrayLists();
		randomizeChitArrayList(siteSoundLostCityChits);
		randomizeChitArrayList(siteSoundLostCastleChits);
		
		// 3.5.4 Map Chits //
		
		// Create ArrayList of 4 randomly selected siteSound Chits & the Lost City Chit
		// These Chits will be added to the Cave Tiles Randomly Later
		// Note: The Lost City is located in one of the caves
		for (int i = 0; i < numChitsMixedWithLostCity; i++) {
			siteSoundLostCityChits.add(siteSoundChits.get(0));
			siteSoundChits.remove(0);
		}
		siteSoundLostCityChits.add(lostCity);
		

		// Create ArrayList of remaining 4 randomly siteSound Chits & the Lost Castle Chit
		// These Chits will be added to the Mountains Tiles Randomly Later
		// Note: The Lost Castle is located in one of the mountains
		for (int i = 0; i < numChitsMixedWithLostCastle; i++) {
			siteSoundLostCastleChits.add(siteSoundChits.get(0));
			siteSoundChits.remove(0);
		}
		siteSoundLostCastleChits.add(lostCastle);
		
		// Loop Through the all hextiles and place appropriate chit(s) on each
		for (int i = 0; i < hextiles.size(); i++) {
			// Place 1 Cave Warning Chit and 1 siteSoundLostCityChit on each of the 5 Cave Hextiles
			if (hextiles.get(i).getHextileType() == Config.HextileType.CAVE) {
				hextiles.get(i).setChits(caveWarningChits.get(0), siteSoundLostCityChits.get(0));
				caveWarningChits.remove(0);
				siteSoundLostCityChits.remove(0);
			}
			// Place 1 Mountain Warning Chit and 1 siteSoundLostCastleChits on each of the 5 Cave Hextiles
			else if (hextiles.get(i).getHextileType() == Config.HextileType.MOUNTAIN) {
				hextiles.get(i).setChits(mountainWarningChits.get(0), siteSoundLostCastleChits.get(0));
				mountainWarningChits.remove(0);
				siteSoundLostCastleChits.remove(0);
			}
			// Place 1 Valley Warning Chit on each of the 5 Valley Hextiles
			else if (hextiles.get(i).getHextileType() == Config.HextileType.VALLEY) {
				hextiles.get(i).setChits(valleyWarningChits.get(0), null);
				valleyWarningChits.remove(0);
			}
			// Place 1 WOODS Warning Chit on each of the 5 WOODS Tiles at Random
			else if (hextiles.get(i).getHextileType() == Config.HextileType.WOODS) {
				hextiles.get(i).setChits(woodsWarningChits.get(0), null);
				woodsWarningChits.remove(0);
			}
			else {
				System.out.println("Error: addChitsToHextiles in ChitFactory, unknown HextileType for " + hextiles.get(i).getName() + " = " + hextiles.get(i).getHextileType());
			}
		}
	}
	
	// Randomize Helper Functions
	
	private void randomizeAllWarningChitArrayLists() {
		randomizeChitArrayList(caveWarningChits);
		randomizeChitArrayList(mountainWarningChits);
		randomizeChitArrayList(valleyWarningChits);
		randomizeChitArrayList(woodsWarningChits);
	}
	
	private void randomizeChitArrayList(ArrayList<Chit> chitArrayList) {
		int arrayListSize = chitArrayList.size();
		ArrayList<Chit> randomChitArrayList = new ArrayList<>();
		
		// Choose a random chit, add it to a new arraylist, and remove that chit from the original arraylist
		for (int i = 0; i < arrayListSize; i++) {
			int randomNum = (int)(Math.random()*chitArrayList.size()); 
			randomChitArrayList.add(chitArrayList.get(randomNum));
			chitArrayList.remove(randomNum);
		}
		
		// Add new randomly order chits back into original arraylist
		chitArrayList.addAll(randomChitArrayList);
	}
	
	// Printing Functions for Testing
	
	private void printChitArrayList(ArrayList<Chit> chitArrayList) {
		System.out.println("Chit Array has " + chitArrayList.size() + " chits.");
		System.out.print("Chit names are:");
		
		for (int i = 0; i < chitArrayList.size(); i++) {
			System.out.print(" " + chitArrayList.get(i).name);
		}
		System.out.println(".");
	}
	
	private void printChitLocations(ArrayList<Hextile> hextiles) {
		System.out.print("In the Lost City you will find");
		for (int i = 0; i < lostCityChits.size(); i++) {
			System.out.print(" " + lostCityChits.get(i).getName());
		}
		System.out.println(".");
		
		System.out.print("In the Lost Castle you will find");
		for (int i = 0; i < lostCastleChits.size(); i++) {
			System.out.print(" " + lostCastleChits.get(i).getName());
		}
		System.out.println(".");
		System.out.println("");
		
		for (int i = 0; i < hextiles.size(); i++) {
			if (hextiles.get(i).getWarningChit() == null && (hextiles.get(i).getOtherChit() == null)) {
				System.out.println("My name is " + hextiles.get(i).getName() + " and I have a " + hextiles.get(i).getWarningChit() + " warning chit and a " + hextiles.get(i).getOtherChit() + " other chit.");
			} 
			else if (hextiles.get(i).getWarningChit() == null) {
				System.out.println("My name is " + hextiles.get(i).getName() + " and I have a " + hextiles.get(i).getWarningChit() + " warning chit and a " + hextiles.get(i).getOtherChit().getName() + " other chit.");
			}
			else if (hextiles.get(i).getOtherChit() == null) {
				System.out.println("My name is " + hextiles.get(i).getName() + " and I have a " + hextiles.get(i).getWarningChit().getName() + " warning chit and a " + hextiles.get(i).getOtherChit() + " other chit.");
			}
			else {
				System.out.println("My name is " + hextiles.get(i).getName() + " and I have a " + hextiles.get(i).getWarningChit().getName() + " warning chit and a " + hextiles.get(i).getOtherChit().getName() + " other chit.");
			}
		}
	}
	
	private void printAllArraylists() {
		printChitArrayList(soundChits);
		printChitArrayList(siteChits);
		printChitArrayList(siteSoundChits);
		printChitArrayList(valleyWarningChits);
		printChitArrayList(woodsWarningChits);
		printChitArrayList(caveWarningChits);
		printChitArrayList(mountainWarningChits);
		printChitArrayList(siteSoundLostCityChits);
		printChitArrayList(siteSoundLostCastleChits);
	}
	
	// Reset all chits and create new starting piles
	
	private void resetChits() {
		// TODO: Empty all arraylist
		
		// Remake the array lists
		initializeArrayLists();
		
		// Remove chits from hextiles
		for (int i = 0; i < hextiles.size(); i++) {
			hextiles.get(i).removeChits();
		}
	}
	
	// Getter Function for ChitFactory Chit Array Lists

	public ArrayList<Chit> getValleyChits() {
		return valleyWarningChits;
	}

	public ArrayList<Chit> getWoodsChits() {
		return woodsWarningChits;
	}

	public ArrayList<Chit> getCaveChits() {
		return caveWarningChits;
	}

	public ArrayList<Chit> getMountainChits() {
		return mountainWarningChits;
	}

	public ArrayList<Chit> getSoundChits() {
		return soundChits;
	}

	public ArrayList<Chit> getSiteChits() {
		return siteChits;
	}
	
	public ArrayList<Chit> getSiteSoundChits() {
		return siteSoundChits;
	}

	public Chit getLostCity() {
		return lostCity;
	}

	public Chit getLostCastle() {
		return lostCastle;
	}
	
	public ArrayList<Chit> getLostCityChits() {
		return lostCityChits;
	}
	
	public ArrayList<Chit> getLostCastleChits() {
		return lostCastleChits;
	}
	
	public ArrayList<Chit> getSiteSoundLostCityChits() {
		return siteSoundLostCityChits;
	}
	
	public ArrayList<Chit> getSiteSoundLostCastleChits() {
		return siteSoundLostCastleChits;
	}
}