package model;

import javax.swing.JFrame;

import view.ViewManager;
import view.externals.MagicRealmWindow;
import network.NetworkManager;
import network.client.ClientApp;
import network.server.ServerApp;

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
