package network.client;

import game.GameState;
import game.chit.ActionChit;
import game.entity.Hero;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTable;

import config.Config.FightType;
import config.Config.MoveType;
import config.Config.TurnStage;
import network.NetworkManager;
import network.packet.PlayerPacket;
import action.ActionList;

public class Client implements Runnable {

	private Socket socket;
	ObjectOutputStream lOutputStream;
	ObjectInputStream lInputStream;
	boolean lIsStreamsOpened;
	NetworkManager lNetworkManager;
	boolean lWaiting;
	boolean gameRunning;
	boolean streamBusy;
	
	
	public Client(Socket s, NetworkManager aNetworkManager)
	{
		socket = s;
		lIsStreamsOpened = false;
		lWaiting = false;
		gameRunning = false;
		lNetworkManager = aNetworkManager;
		streamBusy = false;
	}
	
	@Override
	public void run()
	{
		
		try
		{
			lOutputStream = new ObjectOutputStream(socket.getOutputStream());
			lInputStream = new ObjectInputStream(socket.getInputStream());	
			lIsStreamsOpened = true;
			
			while (true)
			{						
				Thread.sleep(100);
				if(lWaiting){
					if(checkGameStarted()){
						lWaiting = false;
						lNetworkManager.gameStarted();
						gameRunning = true;
					}
				}else{
					if(gameRunning && !streamBusy){
						lNetworkManager.updateLocalGameState(requestGameState());
					}
				}
			}
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Error in Client Run");
			e.printStackTrace();
			try{
				socket.close();
			}catch(Exception o){
				//stuff 
			}
		} 
	}
	
	public boolean sendPing(){
		try{
			
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			String lRequest = "Server Test";
			lOutputStream.writeObject(lRequest);
			lOutputStream.reset();	
			
			System.out.println((String)lInputStream.readObject());
			streamBusy=false;
			return true;
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	
	public int sendPlayerPacket(PlayerPacket aPlayerPacket){
		try{
			
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			if(!isStreamsOpened()){
				while(!isStreamsOpened()){
					Thread.sleep(50);
				}
			}
			
			
			String lRequest = "PlayerPacket";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerPacket);
			lOutputStream.flush();			
			
			lOutputStream.reset();	
			
			int r = (Integer)lInputStream.readObject();
			streamBusy=false;
			return r;
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return 0;
	}
	
	public GameState requestGameState(){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "GameState";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();		
			lOutputStream.reset();
			
			GameState gamestate = (GameState)lInputStream.readObject();
			streamBusy=false;
			return gamestate;
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return null;
	}
	
	public boolean waitForGameStart(){
		lWaiting = true;
		return lWaiting;
	}
	
	public boolean setPlayerCharacter(Hero aHero, int aLocalPlayerNumber){
		try {
			
			while(streamBusy){
				Thread.sleep(1);
			}
			
			streamBusy=true;
			String lRequest = "UpdatePlayerCharacter";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aHero);
			lOutputStream.flush();
			lOutputStream.writeObject(aLocalPlayerNumber);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean r = (boolean) lInputStream.readObject();
			streamBusy=false;
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public void updateCharacterTable(JTable aCharacterTable){
	}
	
	public boolean checkGameStarted(){
		try{

			
			String lRequest = "GameStart";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			
			boolean r = (boolean) lInputStream.readObject();
			streamBusy=false;
			return r;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public boolean updateStateWithPlayerClicked(int aPlayer, Point aPoint){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "PlayerPointClicked";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayer);
			lOutputStream.flush();
			lOutputStream.writeObject(aPoint);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean r = (boolean) lInputStream.readObject();
			streamBusy=false;	
			return r;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public boolean createNewMap(HexGrid aHexGrid){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "CreateMap";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aHexGrid);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean r = (boolean) lInputStream.readObject();
			streamBusy=false;	
			return r;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public Clearing setPlayerStartingLocation(String aDwelling, int aPlayer){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "SetStartingLocation";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aDwelling);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayer);
			lOutputStream.flush();
			lOutputStream.reset();
			
			Clearing r = (Clearing) lInputStream.readObject();
			streamBusy=false;
			return r;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return null;
	}

	
	
	public boolean isStreamsOpened(){
		return lIsStreamsOpened;			
	}
	
	public boolean sendActionList(ActionList aActionList, int aPlayer){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "ActionList";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aActionList);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayer);
			lOutputStream.flush();
			lOutputStream.reset();
			
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy=false;
			return returnv;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public boolean sendContinueActions(){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			
			streamBusy=true;
			
			String lRequest = "ContinueActions";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy=false;
			return returnv;
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	public boolean sendEnableCheat(){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy=true;
			
			String lRequest = "EnableCheating";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy=false;
			return returnv;
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy=false;
		return false;
	}
	
	//Sets the server to be in EVENING_COMBAT_START
	public boolean startCombat(){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
			
			String lRequest = "StartCombat";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;
	}
	
	//Assigns the fight choice of the hero (smash, thrust, swing)
	public boolean assignFight(int aPlayerNum, FightType aFightType){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
			
			String lRequest = "AssignFight";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.writeObject(aFightType);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;
	}
	
	//Assign the move choice of the player (duck, charge, dodge)
	public boolean assignMove(int aPlayerNum, MoveType aMoveType){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			
			streamBusy = true;
		
			String lRequest = "AssignMove";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.writeObject(aMoveType);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;
	}
	
	//Assigns the fight chit the hero chooses
	public boolean assignFightChit(int aPlayerNum, ActionChit aActionChit){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
		
			String lRequest = "AssignFightChit";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.writeObject(aActionChit);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;		
	}
	
	//Assigns the move chit the hero uses
	public boolean assignMoveChit(int aPlayerNum, ActionChit aActionChit){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
		
			String lRequest = "AssignMoveChit";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.writeObject(aActionChit);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;		
	}
	
	//Sets the direction the hero will be blocking
	public boolean setBlockingDirection(int aPlayerNum, FightType aFightType){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
		
			String lRequest = "SetBlockingDirection";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.writeObject(aFightType);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;
	}
	
	//Sets the hero to be waiting for other player's to finish their choices for combat
	public boolean setToWaiting(int aPlayerNum){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
		
			String lRequest = "SetToWaiting";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aPlayerNum);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;	
	}
	
	//Set the turn stage: BIRDSONG, EVENING, ETC.
	public boolean setTurnStage(TurnStage aTurnStage){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
		
			String lRequest = "SetTurnStage";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aTurnStage);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;	
	}
	
	//Refresh combat
	public boolean refreshCombat(){
		try{
			while(streamBusy){
				Thread.sleep(1);
			}
			streamBusy = true;
			
			String lRequest = "RefreshCombat";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			boolean returnv = (boolean) lInputStream.readObject();
			streamBusy = false;
			return returnv;
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		streamBusy = false;
		return false;
	}
}
