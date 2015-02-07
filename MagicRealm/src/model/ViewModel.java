package model;

import game.GameManager;
import game.environment.hex.HexGrid;
import game.entity.Hero;

import java.awt.Dimension;
import java.util.Map;

import network.NetworkManager;
import network.packet.PlayerPacket;
import view.ViewManager;

public class ViewModel {

	ViewManager lViewManager;
	NetworkManager lNetworkManager;
	GameManager lGameManager;
	boolean isServer;
	
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
}
