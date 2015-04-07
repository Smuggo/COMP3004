package model;

import game.GameManager;
import game.GameState;
import game.chit.ActionChit;
import game.chit.ActionChitFactory;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;
import game.entity.Hero;
import game.entity.Native;
import game.entity.Player;
import game.item.ArmourFactory;
import game.item.TreasureFactory;
import game.item.WeaponFactory;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import action.ActionManager;
import config.Config.ActionState;
import config.Config.ActionType;
import config.Config.FightType;
import config.Config.MoveType;
import config.Config.SearchType;
import config.Config.TurnStage;
import config.ImageMap;
import network.NetworkManager;
import network.packet.PlayerPacket;
import view.ViewManager;

public class ViewModel {

	private ViewManager lViewManager;
	private NetworkManager lNetworkManager;
	private GameManager lGameManager;
	private GameState lGameState;
	private ActionManager lActionManager;
	private Map<String, Roadway> lHiddenRoadways;
	private TreasureFactory lTreasureFactory;
	private ActionChitFactory lActionChitFactory;
	private WeaponFactory lWeaponFactory;
	private ArmourFactory lArmourFactory;
	
	private int lLocalPlayerNumber;
	private boolean doneInit = false;
	
	public ViewModel(NetworkManager aNetworkManager){
		lActionChitFactory = new ActionChitFactory();
		lWeaponFactory = new WeaponFactory();
		lArmourFactory = new ArmourFactory();
		lGameManager = new GameManager(this, lActionChitFactory, lWeaponFactory, lArmourFactory);
		lActionManager = new ActionManager();
		lHiddenRoadways = new HashMap<String, Roadway>();
		lTreasureFactory = new TreasureFactory();
	}
	
	public Dimension getScreenDimensions(){
		return lViewManager.getScreenDimensions();
	}
	
	public void setViewManager(ViewManager aViewManager){
		lViewManager = aViewManager;
	}
	
	public ViewManager getViewManager(){
		return lViewManager;
	}
	
	public void setNetworkManager(NetworkManager aNetworkManager){
		lNetworkManager = aNetworkManager;
	}
	
	
	public void requestNewGame(){
		lViewManager.newGame();
	}
	
	public void requestMainMenu(){
		lViewManager.newMenu();
	}
	
	public void requestJoinGame(){
		lViewManager.newJoinGame();
	}
	
	public void requestLoadGame(){
		lViewManager.newLoadGame();
	}
	
	public void requestCharacterSelection(){
		lViewManager.showCharacterList();
	}
	
	public void requestVictoryPoints(Hero character){
		lViewManager.setVictoryPoints(character);
	}
	
	public void requestServerMenu(int aPortNumber, String aNickName){
		lViewManager.newServerMenu();
		lNetworkManager.openServer(aPortNumber);
		boolean lSucceeded = lNetworkManager.connectToServer("localhost", aPortNumber, aNickName);
		
		System.out.println("Connected to self:" + lSucceeded);
	}
	
	
	public void notifyMenuNewClient(PlayerPacket aClientPacket){
		lViewManager.notifyMenuNewClient(aClientPacket);
	}
	
	public void requestCloseServer(){
		lViewManager.closeServerMenu();
		lNetworkManager.closeServer();
	}
	
	public Map<String, Hero> getCharacters(){
		return lGameManager.getCharacters();
	}
	
	
	public void connectToServer(String aIpAddress, int aPortNumber, String aNickname){
		boolean lSucceeded = lNetworkManager.connectToServer(aIpAddress, aPortNumber, aNickname);
		if(lSucceeded){
			lViewManager.newClientLobby();
			lNetworkManager.notifyClientWaitForGameStart();
		}else{
			lViewManager.newJoinGame();
		}
	}
	
	
	public void startGame(){
		
		if(lLocalPlayerNumber == 1 && !doneInit){
			lNetworkManager.createNewMap(lGameManager.getGrid());
			lNetworkManager.notifyClientsGameStarting();
			doneInit = true;
			return;
		}
		
		updateLocalGameState(lNetworkManager.refreshGameState());
		
		if(lLocalPlayerNumber != 1){
			lGameManager.createNewMap(lHiddenRoadways);
		}
		
		Dimension lMapSize = lGameState.getHexGrid().getCanvasSize();
		
		lViewManager.clearMenu();

		lViewManager.createCharacterView();
		
		lViewManager.clearMenu();
		lViewManager.newGameBoard(lMapSize);
	}
	
	
	public void notifyClientsGameStarting(){
		lNetworkManager.notifyClientsGameStarting();
	}
	
	public HexGrid requestGrid(){
		return lGameState.getHexGrid();
	}
	
	public void refreshGameState(){
		lGameState = lNetworkManager.refreshGameState();
	}
	
	public void updatePlayerClicked(Point aPoint){
		lNetworkManager.updatePlayerClicked(aPoint);
	}
	
	public void setLocalPlayerNumber(int aPlayerNum){
		lLocalPlayerNumber = aPlayerNum;
	}
	
	public void updatePlayerCharacter(Hero aHero){
		lNetworkManager.updatePlayerCharacter(aHero, lLocalPlayerNumber);
	}
	
	public void requestStartingLocation(){
		lViewManager.showStartingLocations();
	}
	
	public void requestPlayerMenu(){
		lViewManager.showPlayerMenu();
	}
	
	public void requestTradeMenu(Hero aHero, Native aNativeGroup){
		lViewManager.showTradeMenu(aHero, aNativeGroup);
	}
	
	public void requestBuyMenu(Hero aHero, Native aNativeGroup){
		lViewManager.showBuyMenu(aHero, aNativeGroup);
	}
	
	public void requestSellMenu(Hero aHero, Native aNativeGroup){
		lViewManager.showSellMenu(aHero, aNativeGroup);
	}
	
	public void requestChitList(){
		lViewManager.showChitList();
	}
	
	public void updateLocalGameState(GameState aGameState){
		ArrayList<Player> tempList = new ArrayList<>();
		
		if(lGameState!= null && aGameState != null){	
			for(int i = 1; i < aGameState.getPlayers().size()+1; i++){
				if(!lGameState.getPlayer(i).toString().equals(aGameState.getPlayer(i).toString())){
					System.out.println(lGameState.getPlayer(i).toString());
					System.out.println(aGameState.getPlayer(i).toString());
					tempList.add(aGameState.getPlayer(i));
				}
			}
			
			if(tempList.size() != 0)
				lViewManager.updatePlayerTable(tempList);
		}
		
		//Checks if it's evening, if it is the combat menu opens up for all players in combat.
		//lNetworkManager.startCombat() is so requestCombatMenu isn't called continuously
		if(aGameState.getTurnStage().equals(TurnStage.EVENING)){
			if(aGameState.getPlayer(lLocalPlayerNumber).isInCombat())
				requestCombatMenu();

			lNetworkManager.startCombat();
			lNetworkManager.setTurnStage(TurnStage.EVENING_IN_COMBAT);
			refreshGameState();
		}
		
		//Refreshes combat, getting all players up to date with the other hero's status
		if(aGameState.getTurnStage().equals(TurnStage.EVENING_IN_COMBAT))
			lNetworkManager.refreshCombat();
		
		if(!aGameState.equals(lGameState)){
			//Checks to see if it's a new turn, resets game state for a new turn if so
			if(lGameState!= null && aGameState != null && lGameState.getDay() != aGameState.getDay()){
				lActionManager.createNewTurn(aGameState, lLocalPlayerNumber, aGameState.getPlayer(lLocalPlayerNumber));
				
				if(lViewManager.getCombatMenu() != null)
					lViewManager.getCombatMenu().dispose();
				
				lGameState = aGameState;
				lViewManager.newTurn();
			}
			lGameState = aGameState;
			lViewManager.gameStateUpdated();
		}
	}
	
	public GameState getGameState(){ return lGameState; }
	
	public GameManager getGameManager(){ return lGameManager; }
	
	public int getLocalPlayerNum() {
		return lLocalPlayerNumber;
	}
	
	public ImageMap getImageMap(){
		return lGameManager.getGameImages();
	}
	
	public void setPlayerStartingLocation(String aDwelling){
		lActionManager.createNewTurn(lNetworkManager.setPlayerStartingLocation(aDwelling), lGameState.getPlayer(lLocalPlayerNumber));
	}
	
	public void setLocalActionState(ActionState aActionState){
		lActionManager.setState(aActionState);
	}
	
	public ActionManager getActionManager(){
		return lActionManager;
	}
	
	public void sendActions(){
		lNetworkManager.sendActions(lActionManager.getActionList());
		lActionManager.createNewTurn(lActionManager.getActionList().getCurrentClearing(), lGameState.getPlayer(lLocalPlayerNumber));
	}
	
	public void addToActionTable(String aClearingID){
		lViewManager.addToActionTable(aClearingID);
	}
	
	public void requestSearching(){
		lViewManager.showSearching();
	}
	
	public void requestDieRoller(ActionType aActionType, SearchType aSearchType){
		lViewManager.showDieRoller(aActionType, aSearchType);
	}
	
	public void requestSearchChoice(SearchType aSearchType){
		lViewManager.showSearchChoiceSelection(aSearchType);
	}
	
	public void enableOrDisablePlayer(boolean aButtonState){
		lViewManager.enableOrDisablePlayer(aButtonState);
	}

	public void promptCheatMode() {
		lGameManager.createNewMap(lHiddenRoadways);
		lViewManager.clearMenu();
		
		if (lLocalPlayerNumber == 1) {
			// Ask user what map they would like to use before asking for cheat mode
			// the map selector will call cheat mode window
			lViewManager.showMapSelector();
		}

	}
	
	public void hideConfirmed(){
		lNetworkManager.sendContinueActions();
	}
	public void searchConfirmed(){
		lNetworkManager.sendContinueActions();
	}
	public void moveConfirmed(){
		lNetworkManager.sendContinueActions();
	}
	public void tradeConfirmed(){
		lNetworkManager.sendContinueActions();
	}
	
	public void addClearingChits(Clearing aClearing){
		lViewManager.addClearingChits(aClearing);
	}
	
	public void enableCheat(){
		lNetworkManager.enableCheat();
	}

	public Map<String, Roadway> getHiddenRoadways(){
		return lHiddenRoadways;
	}
	
	public void disposeActionDisplay(){
		lViewManager.disposeActionDisplay();
	}
	
	public TreasureFactory getTreasures(){
		return lTreasureFactory;
	}
	
	public void requestCombatMenu(){
		lViewManager.showCombatMenu();
	}
	
	public ActionChitFactory getActionChits(){
		return lActionChitFactory;
	}
	
	public void requestHeroActionChits(){
		lViewManager.showActionChits();
	}
	
	public void selectCombatMovement(){
		lViewManager.setChooseMovement();
	}
	
	public void selectBlockDirection(){
		lViewManager.setChooseBlock();
	}
	
	//Assign a hero's fight choice
	public boolean assignFightChoice(FightType aFightType){
		return lNetworkManager.assignFightChoice(lLocalPlayerNumber, aFightType);
	}
	
	//Assign a hero's move choice
	public boolean assignMoveChoice(MoveType aMoveType){
		return lNetworkManager.assignMoveChoice(lLocalPlayerNumber, aMoveType);
	}
	
	//Assign a hero's fight chit
	public boolean assignFightChit(ActionChit aActionChit){
		return lNetworkManager.assignFightChit(lLocalPlayerNumber, aActionChit);
	}
	
	//Assign a hero's move chit
	public boolean assignMoveChit(ActionChit aActionChit){
		return lNetworkManager.assignMoveChit(lLocalPlayerNumber, aActionChit);
	}
	
	//Set the player to be waiting for other players (Combat)
	public boolean setToWaiting(){
		return lNetworkManager.setToWaiting(lLocalPlayerNumber);
	}
	
	//Sets the blocking direction of the hero's shield
	public boolean setBlockingDirection(FightType aFightType){
		return lNetworkManager.setBlockingDirection(lLocalPlayerNumber, aFightType);
	}
	
	//Sets the turn stage: BIRDSONG, EVENING, etc.
	public boolean setTurnStage(TurnStage aTurnStage){
		return lNetworkManager.setTurnStage(aTurnStage);
	}
}
