package model;

import game.GameManager;
import game.GameState;
import game.environment.hex.HexGrid;
import game.entity.Hero;
import game.entity.Player;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import network.NetworkManager;
import network.packet.PlayerPacket;
import view.ViewManager;

public class ViewModel {

	ViewManager lViewManager;
	NetworkManager lNetworkManager;
	GameManager lGameManager;
	GameState lGameState;
	boolean isServer;
	int lLocalPlayerNumber;
	
	public ViewModel(NetworkManager aNetworkManager){
		isServer = false;
		lGameManager = new GameManager();
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
	
	public Map<String, Hero> requestCharacters(){
		return lGameManager.requestCharacters();
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
		updateLocalGameState(lNetworkManager.refreshGameState());
		lGameManager.createNewMap();
		
		lViewManager.clearMenu();
		lViewManager.createCharacterView();
		
		Dimension lMapSize = lGameManager.createNewMap();
		
		lViewManager.clearMenu();
		lViewManager.newGameBoard(lMapSize);
	}
	
	
	public void notifyClientsGameStarting(){
		lNetworkManager.notifyClientsGameStarting();
	}
	
	public HexGrid requestGrid(){
		return lGameManager.getGrid();
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
	
	public void updateGameState(){
		lNetworkManager.updateGameState(lGameState, lLocalPlayerNumber);
	}
	
	public void updateLocalGameState(GameState aGameState){
		lGameState = aGameState;
		lViewManager.gameStateUpdated();
	}
	
	public GameState getGameState(){
		return lGameState;
	}
	
	public int getLocalPlayerNum() { return lLocalPlayerNumber; }
}
