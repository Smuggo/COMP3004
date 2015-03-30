package network.server;
import game.chit.ActionChit;
import game.entity.Hero;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

import java.net.Socket;
import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import config.Config.CombatStage;
import config.Config.FightType;
import config.Config.MoveType;
import config.Config.TurnStage;
import action.ActionList;
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
				
				else if(lRequestHeader.equals("GameStart")){
					lServerApp.getGameState().setTurnStage(TurnStage.BIRDSONG);
					lOutputStream.writeObject(gameRunning);
				}
				
				else if(lRequestHeader.equals("GameState")){
					lOutputStream.writeObject(lServerApp.getGameState());
				}
				
				else if(lRequestHeader.equals("PlayerPointClicked")){
					lServerApp.getGameState().updatePointClicked((Integer)lInputStream.readObject(),(Point)lInputStream.readObject());
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("UpdatePlayerCharacter")){
					lServerApp.getGameState().updatePlayer((Hero)lInputStream.readObject(), (Integer)lInputStream.readObject()-1);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("CreateMap")){
					lServerApp.getGameState().setHexGrid((HexGrid)lInputStream.readObject());
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("SetStartingLocation")){
					Clearing currentClearing = lServerApp.getGameState().setStartingLocation((String)lInputStream.readObject(), (Integer)lInputStream.readObject());
					lOutputStream.writeObject(currentClearing);
				}
				else if(lRequestHeader.equals("ActionList")){
					ActionList lActionList =  (ActionList)lInputStream.readObject();
					int lPlayer = (Integer)lInputStream.readObject();
					lServerApp.getGameState().addActionList(lActionList, lPlayer);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("ContinueActions")){
					lServerApp.getGameState().continueExecutingPlayerActionSheets();
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("EnableCheating")){
					lServerApp.getGameState().setCheating(true);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("StartCombat")){ //Sets server to be in EVENING_IN_COMBAT
					lServerApp.getGameState().setTurnStage(TurnStage.EVENING_IN_COMBAT);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("AssignFight")){ //Sets a hero's attack type
					int lPlayer = (Integer)lInputStream.readObject();
					FightType lFightType = (FightType)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setCombatStage(CombatStage.FIGHT);
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setFightType(lFightType);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("AssignMove")){ //Set how a hero will move in combat
					int lPlayer = (Integer)lInputStream.readObject();
					MoveType lMoveType = (MoveType)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setCombatStage(CombatStage.MOVE);
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setMoveType(lMoveType);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("AssignFightChit")){ //Sets the hero's fight chit
					int lPlayer = (Integer)lInputStream.readObject();
					ActionChit lActionChit = (ActionChit)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setFightChoice(lActionChit);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("AssignMoveChit")){ //Set the hero's move chit
					int lPlayer = (Integer)lInputStream.readObject();
					ActionChit lActionChit = (ActionChit)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setMoveChoice(lActionChit);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("SetToWaiting")){ //Sets the player to wait for other players (Combat)
					int lPlayer = (Integer)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().setCombatStage(CombatStage.WAITING);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("SetBlockingDirection")){ //Sets the blocking direction of the shield
					int lPlayer = (Integer)lInputStream.readObject();
					FightType lBlockingDirection = (FightType)lInputStream.readObject();
					
					lServerApp.getGameState().getPlayer(lPlayer).getChosenHero().getShield().setProtectsFrom(lBlockingDirection);
					lOutputStream.writeObject(true);
				}
				else if(lRequestHeader.equals("SetTurnStage")){ //Set the turn stage: BIRDSONG, EVENING, ETC.
					TurnStage lTurnStage = (TurnStage)lInputStream.readObject();
					
					lServerApp.getGameState().setTurnStage(lTurnStage);
					lOutputStream.writeObject(true);
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

