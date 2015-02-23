package action;

import java.awt.Graphics;

import game.GameState;
import game.entity.Player;
import game.environment.hex.Clearing;
import config.Config.ActionState;

public class ActionManager {
	
	ActionList lActionList;
	
	ActionState lActionState;
	
	public ActionManager(){
		lActionList = new ActionList();
		lActionState = ActionState.NOTHING;
	}
	
	public void setState(ActionState aActionState){
		lActionState = aActionState;
	}
	
	public ActionState getState(){
		return lActionState;
	}
	
	public ActionList getActionList(){
		return lActionList;
	}
	
	public void createNewTurn(Clearing aStartingClearing, Player aPlayer){
		lActionList.newTurn(aStartingClearing, aPlayer);
	}
	
	public void createNewTurn(GameState aGameState, int aPlayerNum, Player aPlayer){
		createNewTurn(aGameState.getClearingByPlayer(aPlayerNum), aPlayer);
		lActionList.setTurn(aGameState.getDay());
	}
}
