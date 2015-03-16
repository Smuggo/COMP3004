package game.entity;

import game.GameManager;
import game.chit.ActionChit;
import game.environment.hex.Clearing;
import game.environment.hex.Roadway;
import game.item.Armour;
import game.item.Treasure;
import game.item.Weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import view.internals.game.TradeMenu;
import action.Action;
import action.ActionList;
import config.Config.ActionType;
import config.Config.CharacterImageType;
import config.Config.ChitType;
import config.Config.CombatStage;
import config.Config.DelayPrompt;
import config.Config.DwellingType;
import config.Config.FightType;
import config.Config.MoveType;
import config.Config.RoadwayType;
import config.Config.SearchType;

public class Hero implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -909098114209082150L;

	// Represents a character
	private int[] victoryConditions;
	private int fame;
	private int notoriety;
	private int gold;
	private int turnAmazonBonus;
	private int turnElfBonus;

	private String name;

	private Clearing lClearing;

	private boolean hidden;
	private boolean lViewingHidden; // Can the player see hidden enemies
	private boolean lBlocking;
	private boolean lBlocked;

	private CharacterImageType characterSheet;
	private CharacterImageType characterChit;
	private DwellingType[] startingLocations;

	private ActionList lActionList;
	private boolean needsActionInput;
	
	private Map<String, Roadway> lHiddenRoadways;
	private ArrayList<Treasure> lOwnedTreasures;

	private ArrayList<ActionChit> lFightChits;
	private ArrayList<ActionChit> lMoveChits;
	
	private ActionChit lFightChoice;
	private ActionChit lMoveChoice;
	
	private CombatStage lCombatStage;
	
	private FightType lFightType;
	
	private MoveType lMoveType;
	
	private Weapon lWeapon;
	
	private Armour lHelmet;
	private Armour lBody;
	private Armour lShield;
	
	public Hero(String n, CharacterImageType charPage,
			CharacterImageType charChit, DwellingType[] aStartingLocations) {
		name = n;
		characterSheet = charPage;
		characterChit = charChit;
		startingLocations = aStartingLocations;
		victoryConditions = new int[5];
		lHiddenRoadways = new HashMap<String, Roadway>();

		fame = 0;
		notoriety = 0;
		gold = 0;
		turnAmazonBonus = 0;
		turnElfBonus = 0;

		hidden = false;
		lViewingHidden = false;
		lBlocking = false;
		needsActionInput = false;
		
		lOwnedTreasures = new ArrayList<Treasure>();
		lFightChits = new ArrayList<ActionChit>();
		lMoveChits = new ArrayList<ActionChit>();
		
		lCombatStage = CombatStage.OUTOFCOMBAT;
		lFightChoice = null;
		lMoveChoice = null;
		lFightType = null;
		lMoveType = null;
		
		lWeapon = null;
		lHelmet = null;
		lBody = null;
		lShield = null;
	}

	public void draw(GameManager aManager, Graphics g, Player aPlayer) {
		if (lClearing != null) {
			int x = (int) lClearing.getRotPosition().getX();
			int y = (int) lClearing.getRotPosition().getY();
			
			if(!hidden)
				g.drawImage(getScaledImage(aManager.getCharacterImage(characterChit), 50, 50), x - 25, y - 25, Color.WHITE, null);
			else
				g.drawImage(getScaledImage(aManager.getCharacterImage(characterChit), 50, 50), x - 25, y - 25, Color.GREEN, null);
		}
	}

	//GETTERS
	//----------------------------------------------------------------
	public String getName() {
		return name;
	}

	public boolean getHidden() {
		return hidden;
	}

	public boolean getViewingHidden() {
		return lViewingHidden;
	}
	
	public boolean getBlocking() {
		return lBlocking;
	}
	
	public boolean getBlocked() {
		return lBlocked;
	}

	public CharacterImageType getCharSheet() {
		return characterSheet;
	}

	public CharacterImageType getCharChit() {
		return characterChit;
	}

	public int[] getVictoryConditions() {
		return victoryConditions;
	}

	public int getFame() {
		return fame;
	}

	public int getNotoriety() {
		return notoriety;
	}

	public int getGold() {
		return gold;
	}

	public DwellingType[] getStartingLocations() {
		return startingLocations;
	}

	public Clearing getClearing() {
		return lClearing;
	}
	
	public Map<String, Roadway> getHiddenRoadways() {
		return lHiddenRoadways;
	}
	
	public ArrayList<ActionChit> getFightChits(){
		return lFightChits;
	}
	
	public ArrayList<ActionChit> getMoveChits(){
		return lMoveChits;
	}
	
	public CombatStage getCombatState(){
		return lCombatStage;
	}
	
	public ActionChit getFightChoice(){
		return lFightChoice;
	}
	
	public ActionChit getMoveChoice(){
		return lMoveChoice;
	}
	
	public FightType getFightType(){
		return lFightType;
	}
	
	public MoveType getMoveType(){
		return lMoveType;
	}
	
	public Weapon getWeapon(){
		return lWeapon;
	}
	
	public Armour getHelmet(){
		return lHelmet;
	}
	
	public Armour getBody(){
		return lBody;
	}
	
	public Armour getShield(){
		return lShield;
	}
	//----------------------------------------------------------------

	//SETTERS
	//----------------------------------------------------------------
	public void setVictoryConditions(int[] vCond) {
		victoryConditions = vCond;
	}

	public void setFame(int nFame) {
		fame = nFame;
	}

	public void setNotoriety(int nNotoriety) {
		notoriety = nNotoriety;
	}

	public void setGold(int nGold) {
		gold = nGold;
	}

	public void setHidden(boolean nHidden) {
		hidden = nHidden;
	}

	public void setClearing(Clearing aClearing) {
		lClearing = aClearing;
	}

	public void setViewingHidden(boolean aViewingHidden) {
		lViewingHidden = aViewingHidden;
	}
	public void setHiddenRoadways(Map<String, Roadway> aHiddenRoadways) {
		lHiddenRoadways = aHiddenRoadways;
	}
	public void setBlocking(boolean aBlocking){
		lBlocking = aBlocking;
	}
	
	public void setBlocked(boolean aBlocked){
		lBlocked = aBlocked;
	}
	
	public void setActionChits(ArrayList<ActionChit> aActionChits){
		for(ActionChit aChit: aActionChits){
			if(aChit.getType().equals("FIGHT"))
				lFightChits.add(aChit);
			else if(aChit.getType().equals("MOVE"))
				lMoveChits.add(aChit);
		}
	}
	
	public void setCombatStage(CombatStage aCombatStage){
		lCombatStage = aCombatStage;
	}
	
	public void setFightChoice(ActionChit aFightChoice){
		lFightChoice = aFightChoice;
	}
	
	public void setMoveChoice(ActionChit aMoveChoice){
		lMoveChoice = aMoveChoice;
	}
	
	public void setFightType(FightType aFightType){
		lFightType = aFightType;
	}
	
	public void setMoveType(MoveType aMoveType){
		lMoveType = aMoveType;
	}
	
	public void setWeapon(Weapon aWeapon){
		lWeapon = aWeapon;
	}
	
	public void setHelmet(Armour aHelmet){
		lHelmet = aHelmet;
	}
	
	public void setBody(Armour aBody){
		lBody = aBody;
	}
	
	public void setShield(Armour aShield){
		lShield = aShield;
	}
	//----------------------------------------------------------------

	public DelayPrompt executeAction(Action aAction) {
		ActionType aActionType = aAction.getActionType();
		Random lRandomGenerator = new Random();
		int lRoll1 = lRandomGenerator.nextInt(6) + 1;
		int lRoll2 = lRandomGenerator.nextInt(6) + 1;

		int lFinalRoll;
		if (lRoll1 >= lRoll2)
			lFinalRoll = lRoll1;
		else
			lFinalRoll = lRoll2;
		
		if(name.equals("Dwarf")){
			lFinalRoll = lRoll1;
		}
		
		if(aAction.getRoll() != -1){
			System.out.println("CHEATING ROLL: " + aAction.getRoll());
			lFinalRoll = aAction.getRoll();
		}

		// Movement
		if (aActionType.equals(ActionType.MOVE)) {
			if(needsActionInput){
				needsActionInput = false;
				System.out.println("Moving");
				String lTempRoadName1 = aAction.getClearingStart().getOwnedHextile().getName() + " " +
						aAction.getClearingStart().getNumber() + "-" +
						aAction.getClearingEnd().getNumber();
				String lTempRoadName2 = aAction.getClearingStart().getOwnedHextile().getName() + " " +
						aAction.getClearingEnd().getNumber() + "-" +
						aAction.getClearingStart().getNumber();
				
				if(lHiddenRoadways.containsKey(lTempRoadName1)){
					if(!lHiddenRoadways.get(lTempRoadName1).getDiscovered()){
						aAction.setResult("FAILED TO MOVE");
						System.out.println("FAILED TO MOVE");
						lBlocked = true;
					}
				}
				else if(lHiddenRoadways.containsKey(lTempRoadName2)){
					if(!lHiddenRoadways.get(lTempRoadName2).getDiscovered()){
						aAction.setResult("FAILED TO MOVE");
						System.out.println("FAILED TO MOVE");
						lBlocked = true;
					}
				}
				
				if(!lBlocked && !lBlocked){
					aAction.setResult("MOVE SUCCESSFUL");
					lClearing = aAction.getClearingEnd();
				}
			}else{
				needsActionInput = true;
				return DelayPrompt.MOVING;
			}
			
			if(!lBlocked){
				lClearing = aAction.getClearingEnd();
			}
		}

		// Hiding
		else if (aActionType.equals(ActionType.HIDE)) {
			if(needsActionInput){
				if (lFinalRoll == 6) {
					aAction.setResult("FAILED TO HIDE; DIE1 = " + lRoll1
							+ " DIE2 = " + lRoll2);
					System.out.println("FAILED TO HIDE; DIE1 = " + lRoll1
							+ " DIE2 = " + lRoll2);
					hidden = false;
				} else {
					aAction.setResult("HIDE SUCCESS; DIE1 = " + lRoll1
							+ " DIE2 = " + lRoll2);
					System.out.println("HIDE SUCCESS; DIE1 = " + lRoll1
							+ " DIE2 = " + lRoll2);
					hidden = true;
				}
				needsActionInput = false;
			}else{
				needsActionInput = true;
				return DelayPrompt.HIDING;
			}
		}
		
		// Trade
		else if (aActionType.equals(ActionType.TRADE)) {
			if(needsActionInput){
				if(lClearing.getDwellingType() == DwellingType.INN){
					aAction.setResult("TRADE SUCCESS");
					// Open Trade Menu
					// return DelayPrompt.OPENTRADEMENU;
					//TradeMenu tradeMenu = new TradeMenu();
					//tradeMenu.setVisible(true);
				} else {
					aAction.setResult("TRADE FAIL");
				}
				needsActionInput = false;
			}else{
				needsActionInput = true;
				return DelayPrompt.TRADING;
			}
		}

		// Searching
		else if (aActionType.equals(ActionType.SEARCH)) {
			if(needsActionInput){
				needsActionInput = false;
				if (aAction.getSearchType() == SearchType.PEER) {
					if (lFinalRoll == 1) {
						aAction.setResult("ERROR SEARCHING");
						System.out.println("NOT IMPLEMENTED: 1");
					} else if (lFinalRoll == 2) {
						for(int i = 0; i < lClearing.getRoadways().size(); i++){
							if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.HIDDEN_PATH)){
								lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
								System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
								aAction.setResult("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
							}
						}
					} else if (lFinalRoll == 3) {
						for(int i = 0; i < lClearing.getRoadways().size(); i++){
							if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.HIDDEN_PATH)){
								lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
								System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
								aAction.setResult("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
							}
						}
						lViewingHidden = true;
						
					} else if (lFinalRoll == 4) {
						lViewingHidden = true;
	
					} else if (lFinalRoll == 5) {
						System.out.println("NOT IMPLEMENTED: 5");
						aAction.setResult("ERROR SEARCHING");
					} else {
						System.out.println("FAILED TO FIND ANYTHING.");
						aAction.setResult("FAILED TO FIND ANYTHING");
					}
				} else if (aAction.getSearchType() == SearchType.LOCATE) {
					if (lFinalRoll == 1) {
						System.out.println("NOT IMPLEMENTED: 1");
						aAction.setResult("ERROR LOCATING");
					} else if (lFinalRoll == 2) {
						for(int i = 0; i < lClearing.getRoadways().size(); i++){
							if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE)){
								lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
								System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
								aAction.setResult("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
							}
						}
	
					} else if (lFinalRoll == 3) {
						for(int i = 0; i < lClearing.getRoadways().size(); i++){
							if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE)){
								lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
								System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
								aAction.setResult("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
							}
						}
	
					} else if (lFinalRoll == 4) {
						System.out.println("NOT IMPLEMENTED: 4");
						aAction.setResult("Error");
					} else {
						System.out.println("FAILED TO LOCATE ANYTHING.");
						aAction.setResult("FAILED TO LOCATE ANYTHING");
					}
				} else if (aAction.getSearchType() == SearchType.LOOT) {
				if (lFinalRoll == 1) {
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(1 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(0));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(0).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(0).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(0);
							}
						}
					}
				} else if (lFinalRoll == 2) {
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(2 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(1));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(1).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(1).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(1);
							}
						}
					}
				} else if (lFinalRoll == 3) {
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(3 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(2));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(2).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(2).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(2);
							}
						}
					}
				} else if (lFinalRoll == 4) {
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(4 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(3));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(3).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(3).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(3);
							}
						}
					}
				} else if (lFinalRoll == 5){
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(5 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(4));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(4).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(4).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(4);
							}
						}
					}
				} else {
					if(lClearing.getOwnedHextile().getOtherChit() != null){
						if(lClearing.getOwnedHextile().getOtherChit().getChitType() != null){
							if(6 <= lClearing.getOwnedHextile().getOtherChit().getTreasures().size()){
								lOwnedTreasures.add(lClearing.getOwnedHextile().getOtherChit().getTreasures().get(5));
								fame += lOwnedTreasures.get(lOwnedTreasures.size()-1).getFameValue();
								notoriety += lOwnedTreasures.get(lOwnedTreasures.size()-1).getNotValue();
								System.out.println("Obtained: " +lClearing.getOwnedHextile().getOtherChit().getTreasures().get(5).getName());
								aAction.setResult("OBTAINED: " + lClearing.getOwnedHextile().getOtherChit().getTreasures().get(5).getName());
								lClearing.getOwnedHextile().getOtherChit().getTreasures().remove(5);
							}
						}
					}
				}
			}
	
			} else{
				needsActionInput = true;
				return DelayPrompt.SEARCHING;
			}
		}
		return null;

	}
		
	
	public boolean getNeedsActionInput(){
		return needsActionInput;
	}

	public DelayPrompt executeTurn() {
		while(lActionList.incomplete()){
			if(lActionList.getCurrentAction() < lActionList.getActions().size()){
				Action lAction = lActionList.getActions().get(lActionList.getCurrentAction());
				
				
				if (lActionList.getActionPoints() >= lAction.getCost()) {
					System.out.println(lActionList.getActionPoints());
					DelayPrompt r = executeAction(lAction);
					if(needsActionInput){
						return r;
					}
					if(name.equals("Amazon") && lAction.getActionType().equals(ActionType.MOVE) && lActionList.getTurn() != turnAmazonBonus){
						turnAmazonBonus = lActionList.getTurn();
					}else if(name.equals("Elf") && lAction.getActionType().equals(ActionType.HIDE) && lActionList.getTurn() != turnElfBonus){
						turnElfBonus = lActionList.getTurn();
					}else{
						lActionList.modifyActionPoints(-lAction.getCost());
					}
					
					lActionList.nextAction();
				}else{
					lActionList.complete();
				}
			}else{
				lActionList.complete();
			}
			System.out.println(lActionList.incomplete());
		}
		return null;
	}

	public void addActionList(ActionList aActionList) {
		lActionList = aActionList;
	}
	
	public ActionList getActionList(){
		return lActionList;
	}

	public boolean hasUpToDateActionSheet(int aTurn) {
		if (lActionList != null) {
			if (lActionList.getTurn() == aTurn) {
				return true;
			}
		}
		return false;
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage symbol = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = symbol.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return symbol;
	}
	
	public String getExecutedResult(){
		return lActionList.getExecutedResult();
	}
}
