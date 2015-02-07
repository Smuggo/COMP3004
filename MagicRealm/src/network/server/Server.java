package network.server;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import network.NetworkManager;
import network.packet.PlayerPacket;

public class Server implements Runnable{

	private Socket cSocket;
	private NetworkManager lNetworkManager;
	private boolean gameRunning;
	
	
	public Server(Socket s, NetworkManager aNetworkManager)
	{
		cSocket = s;
		lNetworkManager = aNetworkManager;
		gameRunning = false;
	}
	
	@Override
	public void run()
	{
		try
		{
			ObjectOutputStream lOutputStream = new ObjectOutputStream(cSocket.getOutputStream());
			ObjectInputStream lInputStream = new ObjectInputStream(cSocket.getInputStream());
			boolean running = true;
			while (running)
			{		
				
				String lRequestHeader = (String)lInputStream.readObject();
			
				if(lRequestHeader.equals("PlayerPacket")){
					
					PlayerPacket lPlayerPacket = (PlayerPacket)lInputStream.readObject();
					lNetworkManager.notifyNewClient(lPlayerPacket);
					
					lOutputStream.writeObject(lRequestHeader);

					
				}
				
				if(lRequestHeader.equals("GameStart")){
					lOutputStream.writeObject(gameRunning);
				}
				
				
				

				lOutputStream.flush();
				lOutputStream.reset();
			}
			lOutputStream.close();
			lInputStream.close();
		} 
		catch (Exception e)
		{
			System.out.println("Connection Lost in Server.java");
		}	
		
	}
	
	public void setGameRunning(boolean aRunning){
		gameRunning = aRunning;
	}

}

