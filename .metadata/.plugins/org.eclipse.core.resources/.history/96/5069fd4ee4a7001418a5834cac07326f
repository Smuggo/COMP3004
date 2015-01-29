package server;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Server implements Runnable{

	private Socket cSocket;
	
	public Server(Socket s)
	{
		cSocket = s;
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
				
				String newRequest = (String)lInputStream.readObject();
			
				lOutputStream.writeObject(newRequest);
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

}

