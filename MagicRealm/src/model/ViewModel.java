package model;

import view.ViewManager;

public class ViewModel {

	ViewManager lViewManager;
	
	public ViewModel(){
		
	}
	
	
	public void setViewManager(ViewManager aViewManager){
		lViewManager = aViewManager;
	}
	
	
	public void requestNewGame(){
		lViewManager.newGame();
	}
	
	public void requestMainMenu(){
		lViewManager.newMenu();
	}
	
}
