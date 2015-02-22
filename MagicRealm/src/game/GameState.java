package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import config.Config;
import config.Config.ActionType;
import config.Config.TurnState;
import action.Action;
import action.ActionList;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

public class GameState implements Serializable{

	private static final long serialVersionUID = -1970010135110366533L;
	
	private int lVersion;
	private ArrayList<Player> lPlayers;
	private HexGrid lHexGrid;
	private int lTurn;
	private TurnState lTurnState;
	private int lTurnPlayerExecuting;
	private int lTurnPlayerExecutingActionNum;
	
	public GameState(){
		lVersion = 1;
		lTurn = 1;
		lPlayers = new ArrayList<Player>();
		lTurnState = TurnState.SELECTING;
		lTurnPlayerExecuting = 0;
		lTurnPlayerExecutingActionNum = 0;
	}
	
	//Version
	public int getVersion(){return lVersion;}
	public void incrementVersion(){lVersion++;}
	
	//PlayerList
	public ArrayList<Player> getPlayers(){return lPlayers;}
	public int addPlayer(Player aPlayer){
		lPlayers.add(aPlayer);
		return lPlayers.size();
	}
	
	public Player getPlayer(int i){ return lPlayers.get(i-1); } 
	
	public void updatePointClicked(int aPlayer, Point aPoint){
		if(lPlayers == null)
			return;
		lPlayers.get(aPlayer-1).lastClick = aPoint;
	}
	
	public void updatePlayer(Player aPlayer, int aPlayerNum){
		lPlayers.get(aPlayerNum).setHero(aPlayer.getChosenHero());
	}
	
	public HexGrid getHexGrid(){
		return lHexGrid;
	}
	
	public int getDay(){
		return lTurn;
	}
	
	public void setHexGrid(HexGrid aHexGrid){
		lHexGrid = aHexGrid;
	}
	
	public Clearing setStartingLocation(String aDwelling, int aPlayer){
		Clearing lClearing = lHexGrid.getClearingByDwelling(aDwelling);
		getPlayer(aPlayer).getChosenHero().setClearing(lClearing);
		return lClearing;
	}
	
	public void addActionList(ActionList aActionList, int aPlayer){
		getPlayer(aPlayer).getChosenHero().addActionList(aActionList);

		if(allPlayersActed()){
			executePlayerActionSheets();
		}

	}
	
	public boolean allPlayersActed(){
		for(int i = 0; i < lPlayers.size(); i++){
			if(!lPlayers.get(i).getChosenHero().hasUpToDateActionSheet(lTurn)){
				return false;
			}
		}
		return true;
	}
	
	public void setRandomTurnOrder(){
		ArrayList<Integer> avail = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < lPlayers.size(); i++){
			avail.add(i);
		}
		
		for(int i = 0; i < lPlayers.size(); i++){
			int val = r.nextInt(avail.size());
			lPlayers.get(i).setTurnOrder(avail.get(val));
			avail.remove(val);
		}		
	}
	
	public void executePlayerActionSheets(){
		lTurnState = TurnState.EXECUTING;
		lTurnPlayerExecuting = 0;
		lTurnPlayerExecutingActionNum = 0;
		setRandomTurnOrder();
		
		continueExecutingPlayerActionSheets();
		
		newTurn();
	}
	
	public void continueExecutingPlayerActionSheets(){
		Player currentPlayer = null;
		while(lTurnPlayerExecuting < lPlayers.size()){
			for(int i = 0; i < lPlayers.size(); i++){
				if(lPlayers.get(i).getTurnOrder() == lTurnPlayerExecuting){
					currentPlayer = lPlayers.get(i);
					break;
				}
			}
			if(currentPlayer != null){
				currentPlayer.getChosenHero().executeTurn();
				if(!currentPlayer.getChosenHero().getActionList().incomplete()){
					lTurnPlayerExecuting++;
				}
			}else{
				lTurnPlayerExecuting++;
			}
		}
	}
	
	public void newTurn(){
		for(int i = 0; i < lPlayers.size(); i++){
			lPlayers.get(i).getChosenHero().setHidden(false);
			lPlayers.get(i).getChosenHero().setViewingHidden(false);
			lPlayers.get(i).getChosenHero().setBlocked(false);
		}
		lTurn++;	
		lTurnState = TurnState.SELECTING;
	}
	
	public Clearing getClearingByPlayer(int aPlayer){
		return getPlayer(aPlayer).getChosenHero().getClearing();
	}
}
