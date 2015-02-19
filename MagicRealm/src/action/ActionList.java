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

public class ActionList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -810203100502782508L;
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
		lActions = new ArrayList<Action>();
		lStartingClearing = aStartingClearing;
		lCurrentClearing = aStartingClearing;
	}
	
	public Clearing getCurrentClearing(){
		return lCurrentClearing;
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
}
