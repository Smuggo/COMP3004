package model;

import view.ViewManager;
import network.NetworkManager;

public class MagicRealm {
	
	ViewManager lViewManager;
	NetworkManager lNetworkManager;
	ViewModel lViewModel;
	
	public MagicRealm(){

		lNetworkManager = new NetworkManager();
		
		lViewModel = new ViewModel(lNetworkManager);
		
		lViewManager = new ViewManager(lViewModel);
		
		lViewModel.setNetworkManager(lNetworkManager);
		lViewModel.setViewManager(lViewManager);
		lNetworkManager.setViewModel(lViewModel);
		lViewManager.newMenu();
		
	}
	
	public void start(){
		while(true){
			
			
		}
	}
}
