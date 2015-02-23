package action;

import java.io.Serializable;
import java.util.Random;

import game.environment.hex.Clearing;
import config.Config.ActionType;
import config.Config.SearchType;

public class Action implements Serializable {
	/**
  * 
  */
	private static final long serialVersionUID = -4900065088380619703L;

	private ActionType lActionType;

	private int cost;
	private int lRoll;

	// Move
	private Clearing lClearingStart;
	private Clearing lClearingEnd;

	// Hide

	// Rest

	// Search
	private SearchType lSearchType;

	// Do not make Movement actions with this constructor
	public Action() {

	}

	public void createMoveAction(Clearing aClearingStart, Clearing aClearingEnd) {
		lActionType = ActionType.MOVE;
		cost = 1;
		lRoll = -1;
		lClearingStart = aClearingStart;
		lClearingEnd = aClearingEnd;
	}

	public void createHideAction() {
		lActionType = ActionType.HIDE;
		lRoll = -1;
		cost = 1;
	}

	public void createRestAction() {
		lActionType = ActionType.REST;
		lRoll = -1;
		cost = 1;
	}

	public void createSearchAction(Clearing aClearingStart) {
		lClearingStart = aClearingStart;
		lActionType = ActionType.SEARCH;
		lRoll = -1;
		cost = 1;
	}

	public int getCost() {
		return cost;
	}

	public ActionType getActionType() {
		return lActionType;
	}

	public Clearing getClearingStart() {
		return lClearingStart;
	}

	public Clearing getClearingEnd() {
		return lClearingEnd;
	}

	public void setSearchType(SearchType aSearchType) {
		lSearchType = aSearchType;
	}
	
	public SearchType getSearchType() {
		return lSearchType;
	}
	
	public void setRoll(int aRoll){
		lRoll = aRoll;
	}
	
	public int getRoll(){
		return lRoll;
	}
}