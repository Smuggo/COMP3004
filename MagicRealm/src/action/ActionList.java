package action;

import game.environment.hex.Clearing;

import java.util.ArrayList;
import java.util.List;

import config.Config.ActionType;

public class ActionList {
	private List<Action> lActions;
	private int lActionPoints;
	
	private Clearing lStartingClearing;
	private Clearing lCurrentClearing;
	
	public ActionList(){
		lActions = new ArrayList<Action>();
		lActionPoints = 4;
	}
	
	
	public void addMoveAction(Clearing newClearing){
		Action newAction = new Action();
		newAction.createMoveAction(lCurrentClearing, newClearing);
		lActions.add(newAction);
		lActionPoints -= newAction.getCost();
		lCurrentClearing = newClearing;
	}
	
	public void addHideAction(){
		Action newAction = new Action();
		newAction.createHideAction();
		lActions.add(newAction);
		lActionPoints -= newAction.getCost();
	}
	
	public void addRestAction(){
		Action newAction = new Action();
		newAction.createRestAction();
		lActions.add(newAction);
		lActionPoints -= newAction.getCost();
	}
	
	public void addSearchAction(){
		Action newAction = new Action();
		newAction.createSearchAction();
		lActions.add(newAction);
		lActionPoints -= newAction.getCost();
	}
	
	
	public void setActionPoints(int aPoints){
		lActionPoints = aPoints;
	}

	public void modifyActionPoints(int aModifyValue){
		lActionPoints+= aModifyValue;
	}
	
	public int getActionPoints(){
		return lActionPoints;
	}
	
	public void newTurn(Clearing aStartingClearing){
		lStartingClearing = aStartingClearing;
		lCurrentClearing = aStartingClearing;
	}
	
	public Clearing getCurrentClearing(){
		return lCurrentClearing;
	}
	
	public void removeAction(){
		if(lActions.get(lActions.size()-1).getActionType() == ActionType.MOVE){
			Action lTempAction = null;
			
			for(int i = 0; i < lActions.size() - 1; i++){
				if(lActions.get(i).getActionType() == ActionType.MOVE)
					lTempAction = lActions.get(i);
			}
			if(lTempAction != null)
				lCurrentClearing = lTempAction.getClearingEnd();
		}
		lActions.remove(lActions.size()-1);
		lActionPoints++;
	}
}
