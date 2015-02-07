package network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import network.NetworkManager;
import config.Config;

public class ServerApp implements Runnable{

	int lPortNumber;
	NetworkManager lNetworkManager;
	List<Server> lServerConnections = new ArrayList<Server>();
	boolean lServerOpen;
	
	public ServerApp(int aPortNumber, NetworkManager aNetworkManager){
		lPortNumber = aPortNumber;
		lNetworkManager = aNetworkManager;
		lServerOpen = true;
	}
	
	public void run(){
	
		
		try{		
			int lOpenConnections = 0;
			ServerSocket lServer = new ServerSocket(lPortNumber);
			System.out.println("Waiting for connections on port " + lPortNumber);
		    
			while (!Thread.interrupted() && lServerOpen ){                                              
		    	Socket lNewSocket = lServer.accept();
		        
		    	System.out.println("Incoming connection from: " + lNewSocket.getLocalAddress().getHostName());
		        
		    	Server lNewServer = new Server(lNewSocket, lNetworkManager);
		    	Thread t = new Thread(lNewServer);
		    	t.start();
		    	System.out.println("Thread Created");
		    	lServerConnections.add(lNewServer);


		    	lOpenConnections++;
		    	if(lOpenConnections >= Config.lMaxPlayers){
		    		lNetworkManager.notifyServerFull();
		    		lNetworkManager.closeServer();
		    	}
		 	}
			while(true){
				Thread.sleep(100);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception caught in network.server.ServerApp.java");
		}

		System.out.println("Closing Server");
	}
	
	public void notifyClientsGameStarting(){
		for(int i = 0; i < lServerConnections.size(); i++){
			lServerConnections.get(i).setGameRunning(true);
		}
	}
	

}
