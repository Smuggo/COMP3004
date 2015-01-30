package view;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import model.MagicRealm;
import model.ViewModel;
import view.externals.MagicRealmWindow;
import view.internals.MenuView;
import view.internals.NewGameView;

public class ViewManager {
	
	MagicRealmWindow lWindow;
	
	MenuView lMenu;
	NewGameView lNewGame;
	
	ViewModel lModel;
	
	public ViewManager(ViewModel aModel){
		lModel = aModel;
		
		lWindow = new MagicRealmWindow();
		
		newMenu();
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
	
	

	
	
}
