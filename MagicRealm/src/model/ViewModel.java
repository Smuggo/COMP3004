package model;

import game.GameManager;
import game.GameState;
import game.chit.ActionChitFactory;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;
import game.entity.Hero;
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
import config.Config.CombatStage;
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
	
	boolean isServer;
	int lLocalPlayerNumber;
	boolean doneInit = false;
	
	public ViewModel(NetworkManager aNetworkManager){
		isServer = false;
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
		isServer = true;
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
		}else{
			lViewManager.newJoinGame();
		}
		lNetworkManager.notifyClientWaitForGameStart();
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
	
	public void requestTradeMenu(){
		lViewManager.showTradeMenu();
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
		
		//Checks if all players are done their turn and then checks if any characters are on the same clearing,
		//if two characters are on the same clearing they enter combat with each other. No options, everybody hates
		//everybody right now.
		if(!aGameState.equals(lGameState)){
			if(lGameState!= null && aGameState != null && lGameState.getDay() != aGameState.getDay() && 
					!aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getCombatOccurred()){
				for(Player aPlayer: aGameState.getPlayers()){
					if(aPlayer != lGameState.getPlayer(lLocalPlayerNumber)){
						if(aPlayer.getChosenHero().getClearing().equals(aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getClearing())){
							aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().setCombatOpponent(aPlayer.getChosenHero());
							aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().setTurnStage(TurnStage.START_COMBAT);
							aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().setCombatOccurred(true);
							System.out.println(aPlayer.getUserName());
							requestCombatMenu();
							break;
						}
					}
				}
			}
			
			//Checks if all members of combat have completed their actions, resolves combat if true
			if(aGameState != null && aGameState != null){
				if(aGameState.getPlayer(lLocalPlayerNumber).getChosenHero() != null){
					if(aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getCombatOccurred()){
						if(aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getCombatStage() != null){
							if(aGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getCombatStage().equals(CombatStage.WAITING)){
								resolveCombat();
							}
						}
					}
				}
			}
			
			//Checks to see if it's a new turn, resets game state for a new turn if so
			if(lGameState!= null && aGameState != null && lGameState.getDay() != aGameState.getDay() && 
					lGameState.getPlayer(lLocalPlayerNumber).getChosenHero().getTurnStage().equals(TurnStage.OUT_OF_COMBAT)){
				lActionManager.createNewTurn(aGameState, lLocalPlayerNumber, aGameState.getPlayer(lLocalPlayerNumber));
				lGameState = aGameState;
				lGameState.getPlayer(lLocalPlayerNumber).getChosenHero().setCombatOccurred(false);
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
			lViewManager.showChitPlacementSelection();
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
	
	//Called when all members of combat are done choosing their moves, decides outcome of fight
	public void resolveCombat(){
		lGameState.resolveCombat(lLocalPlayerNumber);
	}
}
