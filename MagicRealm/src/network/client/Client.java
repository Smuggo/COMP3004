package network.client;

import game.GameState;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTable;

import action.ActionList;
import network.NetworkManager;
import network.packet.PlayerPacket;

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
			
			streamBusy=false;
			return (Integer)lInputStream.readObject();
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
	
	public void setPlayerCharacter(GameState aGameState, int aLocalPlayerNumber){
		try {
			
			while(streamBusy){
				Thread.sleep(1);
			}
			
			streamBusy=true;
			String lRequest = "UpdatePlayerCharacter";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.writeObject(aGameState.getPlayer(aLocalPlayerNumber));
			lOutputStream.flush();
			lOutputStream.writeObject(aLocalPlayerNumber);
			lOutputStream.flush();
			lOutputStream.reset();
			
			streamBusy=false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		streamBusy=false;
	}
	
	public void updateCharacterTable(JTable aCharacterTable){
	}
	
	public boolean checkGameStarted(){
		try{

			
			String lRequest = "GameStart";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			streamBusy=false;
			return (boolean) lInputStream.readObject();
			
			
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
			
			streamBusy=false;
			return (boolean) lInputStream.readObject();
			
			
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
			
			streamBusy=false;
			return (boolean) lInputStream.readObject();
			
			
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
			
			streamBusy=false;
			return (Clearing) lInputStream.readObject();
			
			
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
	

}
