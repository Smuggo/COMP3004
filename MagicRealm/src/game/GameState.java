package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Config;
import config.Config.ActionType;
import config.Config.DelayPrompt;
import config.Config.TurnStage;
import config.Config.TurnState;
import action.Action;
import action.ActionList;
import game.entity.Hero;
import game.entity.Monster;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;

public class GameState implements Serializable{

	private static final long serialVersionUID = -1970010135110366533L;
	
	private int lVersion;
	private ArrayList<Player> lPlayers;
	private HexGrid lHexGrid;
	private int lTurn;
	private TurnState lTurnState;
	private DelayPrompt lDelayPrompt;
	private int lTurnPlayerExecuting;
	private boolean lCheating;
	private TurnStage lTurnStage; //The time of day, birdsong, sunrise, daylight, etc.
	
	public GameState(){
		lVersion = 1;
		lTurn = 1;
		lPlayers = new ArrayList<Player>();
		lTurnState = TurnState.SELECTING;
		lTurnPlayerExecuting = 0;
		lDelayPrompt = null;
		lCheating = false;
		lTurnStage = TurnStage.BIRDSONG;
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
	
	public void updatePlayer(Hero aHero, int aPlayerNum){
		lPlayers.get(aPlayerNum).setHero(aHero);
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
		setRandomTurnOrder();
		
		continueExecutingPlayerActionSheets();
		
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
				lDelayPrompt = currentPlayer.getChosenHero().executeTurn();
				if(currentPlayer.getChosenHero().getNeedsActionInput()){
					return;
				}
				if(!currentPlayer.getChosenHero().getActionList().incomplete()){
					lTurnPlayerExecuting++;
				}
			}else{
				lTurnPlayerExecuting++;
			}
		}
		
		if(lTurnPlayerExecuting >= lPlayers.size()){
			startCombat();
			newTurn();
		}
	}
	
	//Sets up who is in combat with who
	public void startCombat(){
		for(int i = 0; i < lPlayers.size(); i++){
			for(int j = 0; j < lPlayers.size(); j++){
				if(j != i){
					if(lPlayers.get(i).getChosenHero().getClearing().equals(lPlayers.get(j).getChosenHero().getClearing())){
						lPlayers.get(i).getChosenHero().setCombatOpponent(lPlayers.get(j).getChosenHero());
						lPlayers.get(i).setOpponent(j);
						lPlayers.get(i).setInCombat(true);
						
						lPlayers.get(j).getChosenHero().setCombatOpponent(lPlayers.get(i).getChosenHero());
						lPlayers.get(j).setOpponent(i);
						lPlayers.get(j).setInCombat(true);
					}
				}
			}
		}
		lTurnStage = TurnStage.EVENING;
	}
	
	//Keeps the states of the heroes opponents up to date
	public void refreshCombat(){
		for(int i = 0; i < lPlayers.size(); i++){
			if(lPlayers.get(i).getInCombat())
				lPlayers.get(i).getChosenHero().setCombatOpponent(lPlayers.get(lPlayers.get(i).getOpponent()).getChosenHero());
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
	
	public TurnState getTurnState(){
		return lTurnState;
	}
	
	public int getPlayerUpdating(){
		while(lTurnPlayerExecuting < lPlayers.size()){
			for(int i = 0; i < lPlayers.size(); i++){
				if(lPlayers.get(i).getTurnOrder() == lTurnPlayerExecuting){
					return i+1;
				}
			}
		}
		System.out.println("Something has gone horribly wrong in gamestate.java");
		return -1;
	}
	
	public DelayPrompt getDelayPrompt(){
		return lDelayPrompt;
	}
	
	public void setDelayPrompt(DelayPrompt aDelayPrompt){
		lDelayPrompt = aDelayPrompt;
	}
	
	public Clearing getClearingByPlayer(int aPlayer){
		return getPlayer(aPlayer).getChosenHero().getClearing();
	}
	
	public void setCheating(boolean aCheating){
		lCheating = aCheating;
	}
	
	public boolean getCheating(){
		return lCheating;
	}
	
	public TurnStage getTurnStage(){
		return lTurnStage;
	}
	
	public void setTurnStage(TurnStage aTurnStage){
		lTurnStage = aTurnStage;
	}
}
