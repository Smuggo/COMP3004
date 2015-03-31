package network;

import java.awt.Point;

import config.Config.FightType;
import config.Config.MoveType;
import config.Config.TurnStage;
import action.ActionList;
import game.GameState;
import game.chit.ActionChit;
import game.entity.Hero;
import game.entity.Player;
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
	
	public void updatePlayerCharacter(Hero aHero, int aLocalPlayerNumber){
		lLocalClient.setPlayerCharacter(aHero, aLocalPlayerNumber);
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
	
	public void sendContinueActions(){
		lLocalClient.sendContinueActions();
	}
	
	public void enableCheat(){
		lLocalClient.sendEnableCheat();
	}
	
	//Starts combat
	public void startCombat(){
		lLocalClient.startCombat();
	}
	
	//Assign the type of attack the hero performs
	public boolean assignFightChoice(int aPlayerNum, FightType aFightType){
		return lLocalClient.assignFight(aPlayerNum, aFightType);
	}
	
	//Assign the type of move the hero performs
	public boolean assignMoveChoice(int aPlayerNum, MoveType aMoveType){
		return lLocalClient.assignMove(aPlayerNum, aMoveType);
	}
	
	//Assign the fight chit the hero will be using
	public boolean assignFightChit(int aPlayerNum, ActionChit aActionChit){
		return lLocalClient.assignFightChit(aPlayerNum, aActionChit);
	}
	
	//Assign the move chit the hero will be using
	public boolean assignMoveChit(int aPlayerNum, ActionChit aActionChit){
		return lLocalClient.assignMoveChit(aPlayerNum, aActionChit);
	}
	
	//Set the player to be waiting for other players
	public boolean setToWaiting(int aPlayerNum){
		return lLocalClient.setToWaiting(aPlayerNum);
	}
	
	//Sets the blocking direction of the shield
	public boolean setBlockingDirection(int aPlayerNum, FightType aFightType){
		return lLocalClient.setBlockingDirection(aPlayerNum, aFightType);
	}
	
	//Set the turn stage: BIRDSONG, EVENING, ETC.
	public boolean setTurnStage(TurnStage aTurnStage){
		return lLocalClient.setTurnStage(aTurnStage);
	}
	
	//Refresh combat
	public boolean refreshCombat(){
		return lLocalClient.refreshCombat();
	}
}
