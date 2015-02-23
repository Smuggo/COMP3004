package model;

import game.GameManager;
import game.GameState;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.entity.Hero;
import game.entity.Player;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import action.ActionManager;
import config.Config.ActionState;
import config.Config.ActionType;
import config.Config.SearchType;
import config.ImageMap;
import network.NetworkManager;
import network.packet.PlayerPacket;
import view.ViewManager;

public class ViewModel {

	ViewManager lViewManager;
	NetworkManager lNetworkManager;
	GameManager lGameManager;
	GameState lGameState;
	ActionManager lActionManager;
	
	boolean isServer;
	int lLocalPlayerNumber;
	boolean doneInit = false;
	
	public ViewModel(NetworkManager aNetworkManager){
		isServer = false;
		lGameManager = new GameManager();
		lActionManager = new ActionManager();
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
			lGameManager.createNewMap();
		}
		
		Dimension lMapSize = lGameState.getHexGrid().getCanvasSize();
		
		lViewManager.clearMenu();

		lViewManager.createCharacterView();
		
		lViewManager.clearMenu();
		lViewManager.newGameBoard(lMapSize);
		
		lGameManager.setHiddenRoads();
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
		
		if(!aGameState.equals(lGameState)){
			if(lGameState!= null && aGameState != null && lGameState.getDay() != aGameState.getDay()){
				lActionManager.createNewTurn(aGameState, lLocalPlayerNumber);
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
		lActionManager.createNewTurn(lNetworkManager.setPlayerStartingLocation(aDwelling));
	}
	
	public void setLocalActionState(ActionState aActionState){
		lActionManager.setState(aActionState);
	}
	
	public ActionManager getActionManager(){
		return lActionManager;
	}
	
	public void sendActions(){
		lNetworkManager.sendActions(lActionManager.getActionList());
		lActionManager.createNewTurn(lActionManager.getActionList().getCurrentClearing());
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
	
	public void enableOrDisablePlayer(boolean aButtonState){
		lViewManager.enableOrDisablePlayer(aButtonState);
	}

	public void promptCheatMode() {
		lGameManager.createNewMap();
		lViewManager.clearMenu();
		
		if (lLocalPlayerNumber == 1) {
			lViewManager.showChitPlacementSelection();
		}

	}
	
	public void hideConfirmed(){
		lNetworkManager.sendContinueActions();
	}
	
	public void addClearingChits(Clearing aClearing){
		lViewManager.addClearingChits(aClearing);
	}
	
	public void enableCheat(){
		lNetworkManager.enableCheat();
	}
	
}
