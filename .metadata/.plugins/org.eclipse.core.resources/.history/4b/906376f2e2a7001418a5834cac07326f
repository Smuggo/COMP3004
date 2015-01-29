package client;

import java.io.IOException;
import java.net.Socket;

import config.Config;


public class ClientApp{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7614529197166228473L;

	public static void main(String[] args) throws IOException
	{
		try 
		{
			
			Socket s = new Socket(Config.lIpAddress, Config.lPort);
			
			System.out.println("Connected to " + Config.lIpAddress + ":" + Config.lPort);
			
			Client client = new Client(s);
			
			
			Thread t = new Thread(client);
			t.start();
			
			//Wait for client to set up streams
			while(!client.isStreamsOpened()){
				Thread.sleep(10);	
			}
			
			//Game Loop
			while(true){
				
			}
		} 
		catch (Exception noServer)
		{
			System.out.println("Error Connecting to Server.");
		}
	}
}

