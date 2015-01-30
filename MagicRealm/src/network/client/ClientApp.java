package network.client;

import java.io.IOException;
import java.net.Socket;

import config.Config;


public class ClientApp{


	public static Client connect(String aIpAddress, int aPort)
	{
		try 
		{
			
			Socket s = new Socket(aIpAddress, aPort);
			
			Client client = new Client(s);
			
			Thread t = new Thread(client);
			t.start();
			
			return client;
		} 
		catch (Exception noServer)
		{
			System.out.println("Error Connecting to Server.");
		}
		return null;
	}
}

