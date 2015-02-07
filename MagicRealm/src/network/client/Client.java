package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import network.NetworkManager;
import network.packet.PlayerPacket;

public class Client implements Runnable {

	private Socket socket;
	ObjectOutputStream lOutputStream;
	ObjectInputStream lInputStream;
	boolean lIsStreamsOpened;
	NetworkManager lNetworkManager;
	boolean lWaiting;
	
	
	public Client(Socket s, NetworkManager aNetworkManager)
	{
		socket = s;
		lIsStreamsOpened = false;
		lWaiting = false;
		lNetworkManager = aNetworkManager;
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
				Thread.sleep(1000);
				if(lWaiting){
					if(checkGameStarted()){
						lWaiting = false;
						lNetworkManager.gameStarted();
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
			String lRequest = "Server Test";
			lOutputStream.writeObject(lRequest);
			lOutputStream.reset();	
			
			System.out.println((String)lInputStream.readObject());
			return true;
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean sendPlayerPacket(PlayerPacket aPlayerPacket){
		try{
			
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
			
			System.out.println((String)lInputStream.readObject());
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean waitForGameStart(){
		lWaiting = true;
		return lWaiting;
	}
	
	public boolean checkGameStarted(){
		try{
			
			String lRequest = "GameStart";
			lOutputStream.writeObject(lRequest);
			lOutputStream.flush();
			lOutputStream.reset();
			
			return (boolean) lInputStream.readObject();
			
			
		}
		catch (Exception e)
		{
			//Dump stack
			System.out.println("Client Error:");
			e.printStackTrace();
		}
		return false;
	}

	
	
	public boolean isStreamsOpened(){
		return lIsStreamsOpened;			
	}
	
	

}
