package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Config;
import config.Config.ActionType;
import config.Config.CombatStage;
import config.Config.DelayPrompt;
import config.Config.FightType;
import config.Config.MoveType;
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
	private int lPlayersInCombat; //The amount of players who are participating in combat that day
	
	public GameState(){
		lVersion = 1;
		lTurn = 1;
		lPlayers = new ArrayList<Player>();
		lTurnState = TurnState.SELECTING;
		lTurnPlayerExecuting = 0;
		lDelayPrompt = null;
		lCheating = false;
		lTurnStage = TurnStage.BIRDSONG;
		lPlayersInCombat = 0;
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
		System.out.println("game state: setHexGrid address" + aHexGrid);
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
				lDelayPrompt = currentPlayer.getChosenHero().executeTurn(this);
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
		}
	}
	
	//Sets up who is in combat with who
	public void startCombat(){
		for(int i = 0; i < lPlayers.size(); i++){
			for(int j = 0; j < lPlayers.size(); j++){
				if(j != i){
					System.out.println(lPlayers.get(i).getChosenHero().getClearing().getIdentifier().equals(lPlayers.get(j).getChosenHero().getClearing().getIdentifier()));
					if(lPlayers.get(i).getChosenHero().getClearing().getIdentifier().equals(lPlayers.get(j).getChosenHero().getClearing().getIdentifier())){ //Two players in combat with each other
						lPlayers.get(i).getChosenHero().setCombatOpponent(lPlayers.get(j).getChosenHero());
						lPlayers.get(i).setOpponent(j);
						lPlayers.get(i).setInCombat(true);					
						lPlayersInCombat += 1;
					}
				}
			}
		}
		System.out.println(lPlayersInCombat + " players in combat");
		lTurnState = TurnState.COMBAT;
		lTurnStage = TurnStage.EVENING;
	}
	
	//Keeps the states of the heroes opponents up to date, checks to see if combat should be resolved
	public void refreshCombat(){
		int lPlayersWaiting = 0; //Amount of players who are waiting to resolve combat
		
		//Go through all players and refresh their opponents, check to see if any players are done with combat selection
		for(int i = 0; i < lPlayers.size(); i++){
			if(lPlayers.get(i).isInCombat()){
				lPlayers.get(i).getChosenHero().setCombatOpponent(lPlayers.get(lPlayers.get(i).getOpponent()).getChosenHero());
				if(lPlayers.get(i).getChosenHero().getCombatStage() != null){
					if(lPlayers.get(i).getChosenHero().getCombatStage().equals(CombatStage.WAITING))
						lPlayersWaiting++;
				}
			}
		}
		//If all players are finished selecting their options for combat, resolve combat
		
		System.out.println(lPlayersWaiting + " players waiting and "+ lPlayersInCombat + " players in combat");
		if(lPlayersWaiting == lPlayersInCombat || lPlayersInCombat == 0)
			resolveCombat();
	}
	
	//Go through all participants in combats that day, see who wins and loses the fights
	public void resolveCombat(){
		for(int i = 0; i < lPlayers.size(); i++){
			if(lPlayers.get(i).isInCombat()){
				
				int lOpponent = lPlayers.get(i).getOpponent();
				Hero lFirstHero = lPlayers.get(i).getChosenHero();
				Hero lOpponentHero = lPlayers.get(lOpponent).getChosenHero();
				
				if(lFirstHero.getFightChoice().getTime() > lOpponentHero.getFightChoice().getTime()){ //First hero attacks faster
					handleFirstHeroCombat(lFirstHero, lOpponentHero);
					
					if(lOpponentHero.isAlive())
						handleOpponentHeroCombat(lFirstHero, lOpponentHero);
				} else if (lFirstHero.getFightChoice().getTime() < lOpponentHero.getFightChoice().getTime()){ //Other hero attacks faster
					handleOpponentHeroCombat(lFirstHero, lOpponentHero);
					
					if(lFirstHero.isAlive())
						handleFirstHeroCombat(lFirstHero, lOpponentHero);
				} else{
					handleFirstHeroCombat(lFirstHero, lOpponentHero);
					handleOpponentHeroCombat(lFirstHero, lOpponentHero);
				}
				
				lPlayers.get(i).setInCombat(false);
				lPlayers.get(lPlayers.get(i).getOpponent()).setInCombat(false);
			}
		}
		lTurnStage = TurnStage.MIDNIGHT;
		lPlayersInCombat = 0;
		newTurn();
	}
	
	//Reset values for a new turn
	public void newTurn(){
		for(int i = 0; i < lPlayers.size(); i++){
			lPlayers.get(i).setInCombat(false);
			lPlayers.get(i).getChosenHero().setHidden(false);
			lPlayers.get(i).getChosenHero().setViewingHidden(false);
			lPlayers.get(i).getChosenHero().setBlocked(false);
		}
		lTurnStage = TurnStage.BIRDSONG;
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
	
	public int getPlayersInCombat(){ 
		return lPlayersInCombat; 
	}
	
	public void setPlayersInCombat(int aPlayersInCombat){
		lPlayersInCombat = aPlayersInCombat;
	}
	
	private void handleFirstHeroCombat(Hero lFirstHero, Hero lOpponentHero){
		boolean lDamageDone = false;
		
		if(lFirstHero.getFightType().equals(FightType.THRUST) && lOpponentHero.getMoveType().equals(MoveType.CHARGE)){ //Thrust beats charge
			if(lOpponentHero.getShield() != null){
				if(lOpponentHero.getShield().getProtectsFrom().get(0).equals(FightType.THRUST)){ //Shield blocks thrust
					if(!lOpponentHero.getShield().isDamaged()){
						System.out.println("First player damages opponent's shield.");
						lOpponentHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("First player destroys opponent's sheild.");
						lOpponentHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lOpponentHero.getBreastplate() != null && !lDamageDone){ //Hits breastplate
				if(!lOpponentHero.getBreastplate().isDamaged()){
					System.out.println("First player damages opponent's breastplate.");
					lOpponentHero.getBreastplate().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's breastplate.");
					lOpponentHero.setBreastplate(null);
					lDamageDone = true;
				}
			}
			if(lOpponentHero.getSuit() != null && !lDamageDone){ //Hits suit of armour
				if(!lOpponentHero.getSuit().isDamaged()){
					System.out.println("First player damages opponent's suit of armour.");
					lOpponentHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's suit of armour.");
					lOpponentHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){ //Hits player
				System.out.println("First player kills second player.");
				lOpponentHero.setAlive(false);
			}
		} else if(lFirstHero.getFightType().equals(FightType.SWING) && lOpponentHero.getMoveType().equals(MoveType.DODGE)){ //Swing beats dodge
			if(lOpponentHero.getShield() != null){
				if(lOpponentHero.getShield().getProtectsFrom().get(0).equals(FightType.SWING)){ //Shield blocks swing
					if(!lOpponentHero.getShield().isDamaged()){
						System.out.println("First player damages opponent's shield.");
						lOpponentHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("First player destroys opponent's sheild.");
						lOpponentHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lOpponentHero.getBreastplate() != null && !lDamageDone){ //Breastplate blocks swing
				if(!lOpponentHero.getBreastplate().isDamaged()){
					System.out.println("First player damages opponent's breastplate.");
					lOpponentHero.getBreastplate().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's breastplate.");
					lOpponentHero.setBreastplate(null);
					lDamageDone = true;
				}
			}
			if(lOpponentHero.getSuit() != null && !lDamageDone){ //Suit of armour blocks swing
				if(!lOpponentHero.getSuit().isDamaged()){
					System.out.println("First player damages opponent's suit of armour.");
					lOpponentHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's suit of armour.");
					lOpponentHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){ //Hits player
				System.out.println("First player kills second player.");
				lOpponentHero.setAlive(false);
			}
		} else if(lFirstHero.getFightType().equals(FightType.SMASH) && lOpponentHero.getMoveType().equals(MoveType.DUCK)){ //Smash beats duck
			if(lOpponentHero.getShield() != null){
				if(lOpponentHero.getShield().getProtectsFrom().get(0).equals(FightType.SMASH)){ //Shield blocks smash
					if(!lOpponentHero.getShield().isDamaged()){
						System.out.println("First player damages opponent's shield.");
						lOpponentHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("First player destroys opponent's shield.");
						lOpponentHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lOpponentHero.getHelmet() != null && !lDamageDone){ //Breastplate blocks smash
				if(!lOpponentHero.getHelmet().isDamaged()){
					System.out.println("First player damages opponent's breastplate.");
					lOpponentHero.getHelmet().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's breastplate.");
					lOpponentHero.setHelmet(null);
					lDamageDone = true;
				}
			}
			if(lOpponentHero.getSuit() != null && !lDamageDone){ //Suit of armour blocks smash
				if(!lOpponentHero.getSuit().isDamaged()){
					System.out.println("First player damages opponent's suit of armour.");
					lOpponentHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("First player destroys opponent's suit of armour.");
					lOpponentHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){
				System.out.println("First player kills second player.");
				lOpponentHero.setAlive(false);
			}
		} else{ //Otherwise move can be dodged
			if(lFirstHero.getFightChoice().getTime() > lOpponentHero.getMoveChoice().getTime()){
				if(lOpponentHero.getShield() != null){
					if(lOpponentHero.getShield().getProtectsFrom().get(0).equals(lFirstHero.getFightType())){
						if(!lOpponentHero.getShield().isDamaged()){
							System.out.println("First player damages opponent's shield.");
							lOpponentHero.getShield().setDamaged(true);
							lDamageDone = true;
						} else{
							System.out.println("First player destroys opponent's shield.");
							lOpponentHero.setShield(null);
							lDamageDone = true;
						}
					}
				}
				if(lOpponentHero.getBreastplate() != null && !lDamageDone && !lFirstHero.getFightType().equals(FightType.SMASH)){
					if(!lOpponentHero.getBreastplate().isDamaged()){
						System.out.println("First player damages opponent's breastplate.");
						lOpponentHero.getBreastplate().setDamaged(true);
						lDamageDone = true;
					} else {
						System.out.println("First player destroys opponent's breastplate.");
						lOpponentHero.setBreastplate(null);
						lDamageDone = true;
					}
				}
				if(lOpponentHero.getHelmet() != null && !lDamageDone && lFirstHero.getFightType().equals(FightType.SMASH)){
					if(!lOpponentHero.getHelmet().isDamaged()){
						System.out.println("First player damages opponent's helmet.");
						lOpponentHero.getHelmet().setDamaged(true);
						lDamageDone = true;
					} else {
						System.out.println("First player detroys opponent's breastplate.");
						lOpponentHero.setHelmet(null);
						lDamageDone = true;
					}
				}
				if(lOpponentHero.getSuit() != null && !lDamageDone){
					if(!lOpponentHero.getSuit().isDamaged()){
						System.out.println("First player damages opponent's suit of armour.");
						lOpponentHero.getSuit().setDamaged(true);
						lDamageDone = true;
					} else{
						System.out.println("First player destroys opponent's suit of armour.");
						lOpponentHero.setSuit(null);
						lDamageDone = true;
					}
				}
				if(!lDamageDone){
					System.out.println("Opponent dies.");
					lOpponentHero.setAlive(false);
				}
			} else {
				System.out.println("Opponent dodged attack.");
			}
		}
	}
	
	private void handleOpponentHeroCombat(Hero lFirstHero, Hero lOpponentHero){
		boolean lDamageDone = false;
		
		if(lOpponentHero.getFightType().equals(FightType.THRUST) && lFirstHero.getMoveType().equals(MoveType.CHARGE) && lOpponentHero.isAlive()){ //Thrust beats charge
			if(lFirstHero.getShield() != null){
				if(lFirstHero.getShield().getProtectsFrom().get(0).equals(FightType.THRUST)){ //Shield blocks thrust
					if(!lFirstHero.getShield().isDamaged()){
						System.out.println("Opponent damages first player's shield.");
						lFirstHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("Opponent destroys first player's sheild.");
						lFirstHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lFirstHero.getBreastplate() != null && !lDamageDone){ //Hits breastplate
				if(!lFirstHero.getBreastplate().isDamaged()){
					System.out.println("Opponent damages first player's breastplate.");
					lFirstHero.getBreastplate().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent destroys first player's breastplate.");
					lFirstHero.setBreastplate(null);
					lDamageDone = true;
				}
			}
			if(lFirstHero.getSuit() != null && !lDamageDone){ //Hits suit of armour
				if(!lFirstHero.getSuit().isDamaged()){
					System.out.println("Opponent damages first player's suit of armour.");
					lFirstHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent destroys first player's suit of armour.");
					lFirstHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){ //Hits player
				System.out.println("Opponent kills first player.");
				lFirstHero.setAlive(false);
			}
		} else if(lOpponentHero.getFightType().equals(FightType.SWING) && lFirstHero.getMoveType().equals(MoveType.DODGE) && lOpponentHero.isAlive()){ //Swing beats dodge
			if(lFirstHero.getShield() != null){
				if(lFirstHero.getShield().getProtectsFrom().get(0).equals(FightType.SWING)){ //Shield blocks swing
					if(!lFirstHero.getShield().isDamaged()){
						System.out.println("Opponent damages first player's shield.");
						lFirstHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("Opponent destroys first player's sheild.");
						lFirstHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lFirstHero.getBreastplate() != null && !lDamageDone){ //Breastplate blocks swing
				if(!lFirstHero.getBreastplate().isDamaged()){
					System.out.println("Opponent damages first player's breastplate.");
					lFirstHero.getBreastplate().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent destroys first player's breastplate.");
					lFirstHero.setBreastplate(null);
					lDamageDone = true;
				}
			}
			if(lFirstHero.getSuit() != null && !lDamageDone){ //Suit of armour blocks swing
				if(!lFirstHero.getSuit().isDamaged()){
					System.out.println("Opponent damages first player's suit of armour.");
					lFirstHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent destroys first player's suit of armour.");
					lFirstHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){ //Hits player
				System.out.println("Opponent kills first player.");
				lFirstHero.setAlive(false);
			}
		} else if(lOpponentHero.getFightType().equals(FightType.SMASH) && lFirstHero.getMoveType().equals(MoveType.DUCK) && lOpponentHero.isAlive()){ //Smash beats duck
			if(lFirstHero.getShield() != null){
				if(lFirstHero.getShield().getProtectsFrom().get(0).equals(FightType.SMASH)){ //Shield blocks smash
					if(!lFirstHero.getShield().isDamaged()){
						System.out.println("Opponent damages first player's shield.");
						lFirstHero.getShield().setDamaged(true);
						lDamageDone = true;
					}
					else{
						System.out.println("Opponent destroys first player's shield.");
						lFirstHero.setShield(null);
						lDamageDone = true;
					}
				}
			}
			if(lFirstHero.getHelmet() != null && !lDamageDone){ //Breastplate blocks smash
				if(!lFirstHero.getHelmet().isDamaged()){
					System.out.println("Opponent damages first player's breastplate.");
					lFirstHero.getHelmet().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent player destroys first player's breastplate.");
					lFirstHero.setHelmet(null);
					lDamageDone = true;
				}
			}
			if(lFirstHero.getSuit() != null && !lDamageDone){ //Suit of armour blocks smash
				if(!lFirstHero.getSuit().isDamaged()){
					System.out.println("Opponent damages first player's suit of armour.");
					lFirstHero.getSuit().setDamaged(true);
					lDamageDone = true;
				}
				else{
					System.out.println("Opponent destroys first player's suit of armour.");
					lFirstHero.setSuit(null);
					lDamageDone = true;
				}
			}
			if(!lDamageDone){
				System.out.println("Opponent kills first player.");
				lFirstHero.setAlive(false);
			}
		} else if (lOpponentHero.isAlive()){ //Otherwise move can be dodged
			if(lOpponentHero.getFightChoice().getTime() > lFirstHero.getMoveChoice().getTime()){
				if(lFirstHero.getShield() != null){
					if(lFirstHero.getShield().getProtectsFrom().get(0).equals(lOpponentHero.getFightType())){
						if(!lFirstHero.getShield().isDamaged()){
							System.out.println("Opponent damages first player's shield.");
							lFirstHero.getShield().setDamaged(true);
							lDamageDone = true;
						} else{
							System.out.println("Opponent destroys first player's shield.");
							lFirstHero.setShield(null);
							lDamageDone = true;
						}
					}
				}
				if(lFirstHero.getBreastplate() != null && !lDamageDone && !lOpponentHero.getFightType().equals(FightType.SMASH)){
					if(!lFirstHero.getBreastplate().isDamaged()){
						System.out.println("Opponent damages first player's breastplate.");
						lFirstHero.getBreastplate().setDamaged(true);
						lDamageDone = true;
					} else {
						System.out.println("Opponent destroys first player's breastplate.");
						lFirstHero.setBreastplate(null);
						lDamageDone = true;
					}
				}
				if(lFirstHero.getHelmet() != null && !lDamageDone && lOpponentHero.getFightType().equals(FightType.SMASH)){
					if(!lFirstHero.getHelmet().isDamaged()){
						System.out.println("Opponent damages first player's helmet.");
						lFirstHero.getHelmet().setDamaged(true);
						lDamageDone = true;
					} else {
						System.out.println("Opponent detroys first player's breastplate.");
						lFirstHero.setHelmet(null);
						lDamageDone = true;
					}
				}
				if(lFirstHero.getSuit() != null && !lDamageDone){
					if(!lFirstHero.getSuit().isDamaged()){
						System.out.println("Opponent damages first player's suit of armour.");
						lFirstHero.getSuit().setDamaged(true);
						lDamageDone = true;
					} else{
						System.out.println("Opponent destroys first player's suit of armour.");
						lFirstHero.setSuit(null);
						lDamageDone = true;
					}
				}
				if(!lDamageDone){
					System.out.println("Opponent kills first player.");
					lFirstHero.setAlive(false);
				}
			} else {
				System.out.println("First player dodged attack.");
			}
		}
	}
}
