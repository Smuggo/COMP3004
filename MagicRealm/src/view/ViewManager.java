package view;

import java.awt.Dimension;

import network.packet.PlayerPacket;
import model.ViewModel;
import view.externals.MagicRealmWindow;
import view.internals.game.GameBoardView;
import view.internals.menu.ClientLobbyView;
import view.internals.menu.JoinGameView;
import view.internals.menu.LoadGameView;
import view.internals.menu.MenuView;
import view.internals.menu.NewGameView;
import view.internals.menu.ServerMenuView;
import view.internals.game.CharacterView;
import view.internals.game.CharacterList;

public class ViewManager {
	
	MagicRealmWindow lWindow;
	
	MenuView lMenu;
	NewGameView lNewGame;
	JoinGameView lJoinGame;
	LoadGameView lLoadGame;
	ServerMenuView lServerMenu;
	ClientLobbyView lClientLobby;
	GameBoardView lGameBoard;
	CharacterView lCharacterView;
	CharacterList lCharacterList;
	
	ViewModel lModel;
	
	public ViewManager(ViewModel aModel){
		lModel = aModel;
		lWindow = new MagicRealmWindow();
	}
	
	public Dimension getScreenDimensions(){
		return lWindow.getSize();
	}
	
	
	public void newMenu(){		
		lMenu = new MenuView(lModel);
		lMenu.setVisible(true);
		lWindow.addWindow(lMenu);
	}
	
	public void newGame(){
	
		lNewGame = new NewGameView(lModel);
		lNewGame.setVisible(true);
		lWindow.addWindow(lNewGame);
	}
	
	public void newJoinGame(){
		
		lJoinGame = new JoinGameView(lModel);
		lJoinGame.setVisible(true);
		lWindow.addWindow(lJoinGame);
	}
	
	public void newLoadGame(){
		
		lLoadGame = new LoadGameView(lModel);
		lLoadGame.setVisible(true);
		lWindow.addWindow(lLoadGame);
	}
	
	public void newServerMenu(){
		
		lServerMenu = new ServerMenuView(lModel);
		lServerMenu.setVisible(true);
		lWindow.addWindow(lServerMenu);
	}
	
	public void newClientLobby(){
		
		lClientLobby = new ClientLobbyView(lModel);
		lClientLobby.setVisible(true);
		lWindow.addWindow(lClientLobby);
	}
	
	public void notifyMenuNewClient(PlayerPacket aClientPacket){
		if(lServerMenu != null && lServerMenu.isVisible()){
			lServerMenu.newClient(aClientPacket);
		}
	}
	
	public void closeServerMenu(){
		if(lServerMenu != null){
			lServerMenu.dispose();
		}
	}
	
	public void newGameBoard(Dimension aCanvasSize){
		lGameBoard = new GameBoardView(lModel, aCanvasSize);
		lGameBoard.setVisible(true);
		lWindow.addWindow(lGameBoard);
	}
	
	public void createCharacterView(){
		lCharacterView = new CharacterView(lModel);
		lCharacterView.setVisible(true);
		lWindow.addWindow(lCharacterView);
	}
	
	public void showCharacterList(){
		lCharacterList = new CharacterList(lModel);
		lCharacterList.setVisible(true);
	}
	
	public void clearMenu(){
		if(lServerMenu != null){
			lServerMenu.dispose();
		}
		if(lNewGame != null){
			lNewGame.dispose();
		}
	}
	
	

	
	
}
