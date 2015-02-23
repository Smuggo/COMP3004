package action;

import game.environment.hex.Clearing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import config.Config.ActionType;
import config.Config.SearchType;

public class ActionList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -810203100502782508L;

	private List<Action> lActions;
	private int lActionPoints;
	private int lTurn;
	private int currentAction;
	
	private Clearing lStartingClearing;
	private Clearing lCurrentClearing;
	
	public boolean incomplete;
	
	public ActionList(){
		lActions = new ArrayList<Action>();
		lActionPoints = 4;
		lTurn = 0;
		currentAction = 0;
		incomplete = true;
	}
	
	
	public void addMoveAction(Clearing newClearing){
		Action newAction = new Action();
		newAction.createMoveAction(lCurrentClearing, newClearing);
		lActions.add(newAction);
		lCurrentClearing = newClearing;
	}
	
	public void addHideAction(){
		Action newAction = new Action();
		newAction.createHideAction();
		lActions.add(newAction);
	}
	
	public void addRestAction(){
		Action newAction = new Action();
		newAction.createRestAction();
		lActions.add(newAction);
	}
	
	public void addSearchAction(SearchType aSearchType){
		Action newAction = new Action();
		newAction.createSearchAction(lCurrentClearing);
		newAction.setSearchType(aSearchType);
		lActions.add(newAction);
	}
	
	
	public void setActionPoints(int aPoints){
		lActionPoints = aPoints;
	}

	public void modifyActionPoints(int aModifyValue){
		lActionPoints+= aModifyValue;
		if(lActionPoints <= 0){
			complete();
		}
	}
	
	public int getActionPoints(){
		return lActionPoints;
	}
	
	public void newTurn(Clearing aStartingClearing){
		lActions = new ArrayList<Action>();
		lStartingClearing = aStartingClearing;
		lCurrentClearing = aStartingClearing;
		lTurn++;
	}
	
	public Clearing getCurrentClearing(){
		return lCurrentClearing;
	}
	
	public void setTurn(int aTurn){
		lTurn = aTurn;
	}
	

	public void drawMovements(Graphics g){
		for(int i = 0; i < lActions.size(); i++){
			Action lAction = lActions.get(i);
			if(lAction.getActionType().equals(ActionType.MOVE)){
				Clearing c1 = lAction.getClearingStart();
				Clearing c2 = lAction.getClearingEnd();
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(3, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
				g2.setColor(new Color(255-(i*5),255-(i*15),0));
				g2.drawLine(c1.getRotPosition().x,c1.getRotPosition().y, c2.getRotPosition().x,c2.getRotPosition().y);
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(1));
			}
		}

	}
	
	public List<Action> getActions(){
		return lActions;
	}
	
	public void removeAction(){
		if(lActions.get(lActions.size()-1).getActionType() == ActionType.MOVE){
			Action lTempAction = null;
			
			for(int i = 0; i < lActions.size() - 1; i++){
				if(lActions.get(i).getActionType() == ActionType.MOVE)
					lTempAction = lActions.get(i);
			}
			if(lTempAction != null && lActions.size() - 1 != 0)
				lCurrentClearing = lTempAction.getClearingEnd();
			else
				lCurrentClearing = lStartingClearing;
		}
		lActions.remove(lActions.size()-1);
		
	}
	
	public int getTurn(){
		return lTurn;
	}
	
	public int getCurrentAction(){
		return currentAction;
	}
	
	public boolean incomplete(){
		return incomplete;
	}
	
	public void complete(){
		incomplete = false;
	}
	
	public void nextAction(){
		currentAction++;
	}
}
