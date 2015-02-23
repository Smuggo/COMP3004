package game.entity;

import game.GameManager;
import game.environment.hex.Clearing;
import game.environment.hex.Roadway;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import action.Action;
import action.ActionList;
import config.Config.ActionType;
import config.Config.CharacterImageType;
import config.Config.DelayPrompt;
import config.Config.DwellingType;
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

		hidden = false;
		lViewingHidden = false;
		lBlocking = false;
		needsActionInput = false;
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
		
		if(aAction.getRoll() != -1){
			System.out.println("CHEATING ROLL: " + aAction.getRoll());
			lFinalRoll = aAction.getRoll();
		}

		// Movement
		if (aActionType.equals(ActionType.MOVE)) {
			String lTempRoadName1 = aAction.getClearingStart().getOwnedHextile().getName() + " " +
					aAction.getClearingStart().getNumber() + "-" +
					aAction.getClearingEnd().getNumber();
			String lTempRoadName2 = aAction.getClearingStart().getOwnedHextile().getName() + " " +
					aAction.getClearingEnd().getNumber() + "-" +
					aAction.getClearingStart().getNumber();
			
			if(lHiddenRoadways.containsKey(lTempRoadName1)){
				if(!lHiddenRoadways.get(lTempRoadName1).getDiscovered()){
					System.out.println("FAILED TO MOVE");
					lBlocked = true;
				}
			}
			else if(lHiddenRoadways.containsKey(lTempRoadName2)){
				if(!lHiddenRoadways.get(lTempRoadName2).getDiscovered()){
					System.out.println("FAILED TO MOVE");
					lBlocked = true;
				}
			}
			
			if(!lBlocked && !lBlocked){
				lClearing = aAction.getClearingEnd();
			}
		}

		// Hiding
		else if (aActionType.equals(ActionType.HIDE)) {
			if(needsActionInput){
				if (lFinalRoll == 6) {
					System.out.println("FAILED TO HIDE; DIE1 = " + lRoll1
							+ " DIE2 = " + lRoll2);
					hidden = false;
				} else {
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

		// Searching
		else if (aActionType.equals(ActionType.SEARCH)) {
			if (aAction.getSearchType() == SearchType.PEER) {
				if (lFinalRoll == 1) {
					System.out.println("NOT IMPLEMENTED: 1");
				} else if (lFinalRoll == 2) {
					for(int i = 0; i < lClearing.getRoadways().size(); i++){
						if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.HIDDEN_PATH)){
							lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
							System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
						}
					}
				} else if (lFinalRoll == 3) {
					for(int i = 0; i < lClearing.getRoadways().size(); i++){
						if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.HIDDEN_PATH)){
							lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
							System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
						}
					}
					lViewingHidden = true;
					
				} else if (lFinalRoll == 4) {
					lViewingHidden = true;

				} else if (lFinalRoll == 5) {
					System.out.println("NOT IMPLEMENTED: 5");
				} else {
					System.out.println("FAILED TO FIND ANYTHING.");
				}
			} else if (aAction.getSearchType() == SearchType.LOCATE) {
				if (lFinalRoll == 1) {
					System.out.println("NOT IMPLEMENTED: 1");
				} else if (lFinalRoll == 2) {
					for(int i = 0; i < lClearing.getRoadways().size(); i++){
						if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE)){
							lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
							System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
						}
					}

				} else if (lFinalRoll == 3) {
					for(int i = 0; i < lClearing.getRoadways().size(); i++){
						if(aAction.getClearingStart().getRoadways().get(i).getRoadwayType().equals(RoadwayType.SECRET_PASSAGE)){
							lHiddenRoadways.get(aAction.getClearingStart().getRoadways().get(i).getName()).setDiscovered(true);
							System.out.println("DISCOVERED: " + aAction.getClearingStart().getRoadways().get(i).getName());
						}
					}

				} else if (lFinalRoll == 4) {
					System.out.println("NOT IMPLEMENTED: 4");
				} else {
					System.out.println("FAILED TO LOCATE ANYTHING.");
				}
			} else if (aAction.getSearchType() == SearchType.LOOT) {
				if (lFinalRoll == 1) {

				} else if (lFinalRoll == 2) {

				} else if (lFinalRoll == 3) {

				} else if (lFinalRoll == 4) {

				} else if (lFinalRoll == 5){

				} else {
					
				}
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
					DelayPrompt r = executeAction(lAction);
					if(needsActionInput){
						return r;
					}
					lActionList.modifyActionPoints(-lAction.getCost());
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
}
