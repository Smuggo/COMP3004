package view;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import view.externals.MagicRealmWindow;
import view.internals.MenuView;
import view.internals.NewGameView;

public class ViewManager {
	
	MagicRealmWindow lWindow;
	
	MenuView lMenu;
	NewGameView lNewGame;
	
	public ViewManager(){
		lWindow = new MagicRealmWindow();
		
		lMenu = new MenuView();
		lMenu.setVisible(true);
		lWindow.addWindow(lMenu);
		
		lNewGame = new NewGameView();
		lNewGame.setVisible(true);
		lWindow.addWindow(lNewGame);
		
	}
	
	

	
	
}
