package action;

import java.io.Serializable;

import game.environment.hex.Clearing;
import config.Config.ActionType;

public class Action implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4900065088380619703L;

	private ActionType lActionType;
	
	private int cost;
	
	//Move
	Clearing lClearingStart;
	Clearing lClearingEnd;
	
	//Hide
	
	//Rest
	
	//Search
	
	
	//Do not make Movement actions with this constructor
	public Action(){

	}
	
	public void createMoveAction(Clearing aClearingStart, Clearing aClearingEnd){
		lActionType = ActionType.MOVE;
		cost = 1;
		lClearingStart = aClearingStart;
		lClearingEnd = aClearingEnd;
	}
	
	public void createHideAction(){
		lActionType = ActionType.HIDE;
		cost = 1;
	}
	
	public void createRestAction(){
		lActionType = ActionType.REST;
		cost = 1;
	}
	
	public void createSearchAction(){
		lActionType = ActionType.SEARCH;
		cost = 1;
	}
	
	public int getCost(){
		return cost;
	}
	
	public ActionType getActionType(){
		return lActionType;
	}
	

	
	
}
