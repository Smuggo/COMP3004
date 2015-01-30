package network;

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
		lLocalClient = ClientApp.connect(aIpAddress, aPortNumber);
		
		if (lLocalClient == null){
			return false;
		}
		
		PlayerPacket lPlayerPacket = new PlayerPacket(aNickname);
		lLocalClient.sendPlayerPacket(lPlayerPacket);
		
		return true;
	}
	
	public void notifyServerFull(){
		//TODO: fill out notifyServerFull
	}
	
	

}
