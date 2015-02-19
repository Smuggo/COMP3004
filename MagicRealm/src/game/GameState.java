package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import config.Config.ActionType;
import action.Action;
import action.ActionList;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

public class GameState implements Serializable{

	private static final long serialVersionUID = -1970010135110366533L;
	
	private int lVersion;
	private ArrayList<Player> lPlayers;
	private HexGrid lHexGrid;
	private int lTurn;
	
	
	public GameState(){
		lVersion = 1;
		lTurn = 1;
		lPlayers = new ArrayList<Player>();
	}
	
	//Version
	public int getVersion(){return lVersion;}
	public void incrementVersion(){lVersion++;}
	
	//PlayerList
	public ArrayList<Player> getPlayers(){return lPlayers;}
	public int addPlayer(Player aPlayer){
		lPlayers.add(aPlayer);
		return lPlayers.size();
	}
	
	public Player getPlayer(int i){ return lPlayers.get(i-1);} 
	
	public void updatePointClicked(int aPlayer, Point aPoint){
		if(lPlayers == null)
			return;
		lPlayers.get(aPlayer-1).lastClick = aPoint;
	}
	
	public void updatePlayer(Player aPlayer, int aPlayerNum){
		lPlayers.get(aPlayerNum).setHero(aPlayer.getChosenHero());
	}
	
	public HexGrid getHexGrid(){
		return lHexGrid;
	}
	
	public void setHexGrid(HexGrid aHexGrid){
		lHexGrid = aHexGrid;
	}
	
	public Clearing setStartingLocation(String aDwelling, int aPlayer){
		Clearing lClearing = lHexGrid.getClearingByDwelling(aDwelling);
		getPlayer(aPlayer).getChosenHero().setClearing(lClearing);
		return lClearing;
	}
	
	public void executeActionList(ActionList aActionList, int aPlayer){
		for(int i = 0; i < aActionList.getActions().size(); i++){
			Action lAction = aActionList.getActions().get(i);
			getPlayer(aPlayer).getChosenHero().executeAction(lAction);
		}
	}
}
