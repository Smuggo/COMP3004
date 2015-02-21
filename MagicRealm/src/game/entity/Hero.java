package game.entity;

import game.environment.hex.Clearing;
import game.environment.hex.Roadway;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import action.Action;
import action.ActionList;
import config.Config.ActionType;
import config.Config.CharacterImageType;
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

	private CharacterImageType characterSheet;
	private CharacterImageType characterChit;
	private DwellingType[] startingLocations;

	private ActionList lActionList;
	
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
	}

	public void draw(Graphics g, Player aPlayer) {
		if (lClearing != null) {
			FontMetrics fm = g.getFontMetrics();
			String string = name + " : " + aPlayer.getUserName();
			Rectangle2D rect = fm.getStringBounds(string, g);
			int x = (int) lClearing.getRotPosition().getX();
			int y = (int) lClearing.getRotPosition().getY();

			g.setColor(new Color(20, 20, 20));
			g.fillRect(x - 3, y - fm.getAscent(), (int) rect.getWidth() + 6,
					(int) rect.getHeight());
			g.setColor(new Color(0, 200, 200));
			g.drawString(string, x, y);
			g.setColor(Color.black);
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

	public void executeAction(Action aAction) {
		ActionType aActionType = aAction.getActionType();
		Random lRandomGenerator = new Random();
		int lRoll1 = lRandomGenerator.nextInt(6) + 1;
		int lRoll2 = lRandomGenerator.nextInt(6) + 1;

		int lFinalRoll;
		if (lRoll1 >= lRoll2)
			lFinalRoll = lRoll1;
		else
			lFinalRoll = lRoll2;

		// Movement
		if (aActionType.equals(ActionType.MOVE)) {
			
			lClearing = aAction.getClearingEnd();
		}

		// Hiding
		else if (aActionType.equals(ActionType.HIDE)) {
			if (lFinalRoll == 6) {
				System.out.println("FAILED TO HIDE; DIE1 = " + lRoll1
						+ " DIE2 = " + lRoll2);
			} else {
				System.out.println("HIDE SUCCESS; DIE1 = " + lRoll1
						+ " DIE2 = " + lRoll2);
				hidden = true;
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
	}

	public void executeTurn() {
		for (int i = 0; i < lActionList.getActions().size(); i++) {
			Action lAction = lActionList.getActions().get(i);
			if (lActionList.getActionPoints() >= lAction.getCost()) {
				executeAction(lAction);
				lActionList.modifyActionPoints(-lAction.getCost());
			}
		}
	}

	public void addActionList(ActionList aActionList) {
		lActionList = aActionList;
	}

	public boolean hasUpToDateActionSheet(int aTurn) {
		if (lActionList != null) {
			if (lActionList.getTurn() == aTurn) {
				return true;
			}
		}
		return false;
	}
}
