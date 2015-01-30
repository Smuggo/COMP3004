package view;

import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import model.MagicRealm;
import model.ViewModel;
import view.externals.MagicRealmWindow;
import view.internals.menu.JoinGameView;
import view.internals.menu.LoadGameView;
import view.internals.menu.MenuView;
import view.internals.menu.NewGameView;
import view.internals.menu.ServerMenuView;

public class ViewManager {
	
	MagicRealmWindow lWindow;
	
	MenuView lMenu;
	NewGameView lNewGame;
	JoinGameView lJoinGame;
	LoadGameView lLoadGame;
	ServerMenuView lServerMenu;
	
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
	
	

	
	
}
