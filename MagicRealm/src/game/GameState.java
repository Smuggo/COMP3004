package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import game.entity.Player;

public class GameState implements Serializable{

	private static final long serialVersionUID = -1970010135110366533L;
	
	private int lVersion;
	private ArrayList<Player> lPlayers;
	
	public GameState(){
		lVersion = 1;
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
	
	public void getAvailableCharacters(){ }
}
