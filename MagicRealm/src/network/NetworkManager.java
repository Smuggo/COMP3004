package network;

import java.awt.Point;

import action.ActionList;
import game.GameState;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;
import model.ViewModel;
import network.client.Client;
import network.client.ClientApp;
import network.packet.PlayerPacket;
import network.server.ServerApp;

public class NetworkManager {
	
	private ServerApp lServerApp;
	
	private ViewModel lViewModel;
	
	Thread lServerThread;
	Client lLocalClient;
	int lLocalPlayerNumber;
	
	public NetworkManager(){
	}
	
	
	public void openServer(int aPortNumber){
		lServerApp = new ServerApp(aPortNumber, this);
		lServerThread = new Thread(lServerApp);
		lServerThread.start();
	}
	
	
	public void setViewModel(ViewModel aViewModel){
		lViewModel = aViewModel;
	}
	
	
	public void notifyNewClient(PlayerPacket aClientPacket){
		lViewModel.notifyMenuNewClient(aClientPacket);
	}
	
	public void closeServer(){
		if(lServerThread != null && lServerThread.isAlive()){
			lServerThread.interrupt();
		}
	}
	
	public boolean connectToServer(String aIpAddress, int aPortNumber, String aNickname){
		lLocalClient = ClientApp.connect(aIpAddress, aPortNumber, this);
		
		if (lLocalClient == null){
			return false;
		}
		
		PlayerPacket lPlayerPacket = new PlayerPacket(aNickname);
		lLocalPlayerNumber = lLocalClient.sendPlayerPacket(lPlayerPacket);
		lViewModel.setLocalPlayerNumber(lLocalPlayerNumber);
		
		return true;
	}
	
	public void notifyServerFull(){
		//TODO: fill out notifyServerFull
	}
	
	public void notifyClientsGameStarting(){
		lLocalClient.waitForGameStart();
		lServerApp.notifyClientsGameStarting();
		
	}
	
	public boolean notifyClientWaitForGameStart(){
		return lLocalClient.waitForGameStart();
	}
	
	public void gameStarted(){
		lViewModel.startGame();
	}
	
	public GameState refreshGameState(){
		return lLocalClient.requestGameState();
	}
	
	public void updatePlayerClicked(Point aPoint){
		lLocalClient.updateStateWithPlayerClicked(lLocalPlayerNumber, aPoint);
	}
	
	public void updatePlayerCharacter(GameState aGameState, int aLocalPlayerNumber){
		lLocalClient.setPlayerCharacter(aGameState, aLocalPlayerNumber);
	}
	
	public void updateLocalGameState(GameState aGameState){
		lViewModel.updateLocalGameState(aGameState);
	}
	
	public void createNewMap(HexGrid aHexGrid){
		if(lLocalPlayerNumber == 1){
			lLocalClient.createNewMap(aHexGrid);
		}
	}
	
	public Clearing setPlayerStartingLocation(String aDwelling){
		return lLocalClient.setPlayerStartingLocation(aDwelling, lLocalPlayerNumber);
	}
	
	
	public void sendActions(ActionList aActionList){
		lLocalClient.sendActionList(aActionList, lLocalPlayerNumber);
	}
}
