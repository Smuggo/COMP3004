package model;

import game.GameManager;
import game.GameState;
import game.chit.ActionChitFactory;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import game.environment.hex.Roadway;
import game.entity.Hero;
import game.entity.Player;
import game.item.TreasureFactory;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
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

	private ViewManager lViewManager;
	private NetworkManager lNetworkManager;
	private GameManager lGameManager;
	private GameState lGameState;
	private ActionManager lActionManager;
	private Map<String, Roadway> lHiddenRoadways;
	private TreasureFactory lTreasureFactory;
	private ActionChitFactory lActionChitFactory;
	
	boolean isServer;
	int lLocalPlayerNumber;
	boolean doneInit = false;
	
	public ViewModel(NetworkManager aNetworkManager){
		isServer = false;
		lActionChitFactory = new ActionChitFactory();
		lGameManager = new GameManager(this, lActionChitFactory);
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
				requestCombatMenu();
				lActionManager.createNewTurn(aGameState, lLocalPlayerNumber, aGameState.getPlayer(lLocalPlayerNumber));
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
}
