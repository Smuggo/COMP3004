package network.server;
import game.entity.Player;

import java.net.Socket;
import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import network.NetworkManager;
import network.packet.PlayerPacket;

public class Server implements Runnable{

	private Socket cSocket;
	private NetworkManager lNetworkManager;
	private boolean gameRunning;
	private ServerApp lServerApp;
	
	
	public Server(Socket s, NetworkManager aNetworkManager, ServerApp aServerApp)
	{
		cSocket = s;
		lNetworkManager = aNetworkManager;
		gameRunning = false;
		lServerApp = aServerApp;
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

					int playerNum = lServerApp.createNewPlayer(lPlayerPacket.getNickname());

					lOutputStream.writeObject(playerNum);
				}
				
				if(lRequestHeader.equals("GameStart")){
					lOutputStream.writeObject(gameRunning);
				}
				
				if(lRequestHeader.equals("GameState")){
					lOutputStream.writeObject(lServerApp.getGameState());
				}
				
				if(lRequestHeader.equals("PlayerPointClicked")){
					lServerApp.getGameState().updatePointClicked((Integer)lInputStream.readObject(),(Point)lInputStream.readObject());
					lOutputStream.writeObject(true);
				}
				if(lRequestHeader.equals("UpdateGameState")){
					lServerApp.getGameState().updatePlayer((Player)lInputStream.readObject(), (Integer)lInputStream.readObject()-1);
					//lServerApp.getGameState().setHeroes((Map<String,Hero>)lInputStream.readObject());
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
			e.printStackTrace();
		}	
		
	}
	
	public void setGameRunning(boolean aRunning){
		gameRunning = aRunning;
	}

}

